package net.coderodde.util.inshuffle.support;

import java.util.Objects;
import net.coderodde.util.inshuffle.InShuffleAlgorithm;

/**
 * This class implements an in-place, linear time algorithm for in-shuffle. See 
 * <a href="https://arxiv.org/pdf/0805.1598.pdf">
 * A Simple In-Place Algorithm for In-Shuffle</a> for details.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Aug 25, 2017)
 */
public final class InPlaceInShuffleAlgorithm implements InShuffleAlgorithm {

    /**
     * In-shuffles the input array in linear time using constant amount of 
     * space.
     * 
     * @param <E>
     * @param array 
     */
    @Override
    public <E> void shuffle(E[] array) {
        new AlgorithmState<E>(array).run();
    }

    /**
     * Holds the state of the algorithm.
     * 
     * @param <E> the array component type.
     */
    private static final class AlgorithmState<E> {
        
        /**
         * The array being shuffled.
         */
        private final E[] array;
        
        AlgorithmState(E[] array) {
            this.array = Objects.requireNonNull(array, 
                                                "The input array is null.");
        }
        
        private void run() {
            if (array.length < 2) {
                return; // Trivially in-shuffled.
            }
            
            if ((array.length & 1) == 1) {
                // Array has an odd number of components. Put the median 
                // component to the end of the array:
                rightRotate(array.length / 2,
                            array.length / 2 + 1,
                            array.length / 2);
                
                // Shuffle omitting the last array component:
                shuffle(0, array.length - 2);
            } else {
                // Shuffle the entire array:
                shuffle(0, array.length - 1);
            }
        }
        
        /**
         * This class holds two algorithm parameters.
         */
        private static final class Parameters {
            
            int m;
            int k;
            
            Parameters(int m, int k) {
                this.m = m;
                this.k = k;
            }
        }
        
        /**
         * Finds the values for the algorithm parameters.
         * 
         * @param n the length of the half of the array to shuffle.
         * @return the object holding the parameters.
         */
        private Parameters findParameters(int n) {
            int t = 3;
            int k = 0;
            
            while (t <= 2 * n) {
                t *= 3;
                k++;
            }
            
            return new Parameters(((t / 3) - 1) >>> 1, k);
        }
        
        /**
         * This method implements the actual algorithm.
         * 
         * @param array      the array holding the target range.
         * @param startIndex the index of the first array component belonging to 
         *                   the range to shuffle.
         * @param endIndex   the index of the last array component belonging to 
         *                   the range to shuffle.
         */
        private void shuffle(int startIndex, int endIndex) {
            while (true) {
                int n = (endIndex - startIndex + 1) >>> 1;
                
                Parameters parameters = findParameters(n);
                
                int m = parameters.m;
                int k = parameters.k;
                
                rightRotate(startIndex + m, n, m);
                
                for (int i = 0, cycleStartIndex = 1;
                        i < k;
                        i++, cycleStartIndex *= 3) {
                    cycleLeader(startIndex,
                                endIndex,
                                cycleStartIndex,
                                m);
                }
                
                if (m == 0) {
                    return;
                }
                
                startIndex += 2 * m; // Tail recursion.
            }
        }
        
        /**
         * Runs the cycle leader algorithm in order to rotate a permutation
         * cycle by one position.
         * 
         * @param startIndex      the index of the first array component holding
         *                        the cycle.
         * @param endIndex        the index of the last array component holding
         *                        the cycle.
         * @param cycleStartIndex the starting index within the range.
         * @param order           the order.
         */
        private void cycleLeader(int startIndex, 
                                 int endIndex,
                                 int cycleStartIndex,
                                 int order) {
            int currentIndex = cycleStartIndex;
            int nextIndex = permute(currentIndex, order);
            E token = array[startIndex + currentIndex];
            E nextToken;
            
            while (nextIndex != cycleStartIndex) {
                nextToken = array[startIndex + nextIndex];
                array[startIndex + nextIndex] = token;
                token = nextToken;
                currentIndex = nextIndex;
                nextIndex = permute(nextIndex, order);
            }
            
            array[startIndex + cycleStartIndex] = token;
        }
        
        /**
         * Defines the permutation leading to the in-shuffle order.
         * 
         * @param currentIndex the starting index.
         * @param order        the order.
         * @return the next index.
         */
        private int permute(int currentIndex, int order) {
            if (currentIndex < order) {
                return currentIndex << 1;
            } else {
                return ((currentIndex - order) << 1) + 1;
            }
        }
        
        /**
         * Rotates the range {@code array[rangeStartIndex], 
         * array[rangeStartIndex + 1], ..., array[rangeStartIndex + rangeLength 
         * - 1] {@code rotationLength} positions to the right.
         * 
         * @param rangeStartIndex the index of the first array component 
         *                        belonging to the range to rotate.
         * @param rangeLength     the length of the range to rotate.
         * @param rotationLength  the number of positions to rotate to the 
         *                        right.
         */
        private void rightRotate(int rangeStartIndex,
                                 int rangeLength,
                                 int rotationLength) {
            reverse(rangeStartIndex, rangeStartIndex + rangeLength - 1);
            reverse(rangeStartIndex, rangeStartIndex + rotationLength - 1);
            reverse(rangeStartIndex + rotationLength, 
                    rangeStartIndex + rangeLength - 1);
        }
        
        /**
         * Reverses the range {@code array[startIndex], array[startIndex + 1],
         * ..., array[endIndex]}.
         * 
         * @param startIndex the index of the leftmost array component of the 
         *                   range to reverse.
         * @param endIndex   the index of the rightmost array component of the
         *                   range to reverse.
         */
        private void reverse(int startIndex, int endIndex) {
            for (; startIndex < endIndex; ++startIndex, --endIndex) {
                swap(startIndex, endIndex);
            }
        }
        
        /**
         * Swaps the array components {@code array[index1]} and 
         * {@code array[index2]}.
         * 
         * @param index1 the index of the first component.
         * @param index2 the index of the second component.
         */
        private void swap(int index1, int index2) {
            E tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
        }
    }
}
