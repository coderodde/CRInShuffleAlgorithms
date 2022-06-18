package net.coderodde.util.inshuffle.support;

import net.coderodde.util.inshuffle.InShuffleAlgorithm;

/**
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 ()
 * @since 1.6 ()
 */
public final class InPlaceInShuffleAlgorithmV2 implements InShuffleAlgorithm {

    @Override
    public <E> void shuffle(E[] array) {
        shuffle(array, 0, array.length);
    }
    
    public <E> void shuffle(E[] array, int fromIndex, int toIndex) {
        int rangeLength = toIndex - fromIndex;
        
        if (rangeLength < 2) {
            // Trivially shuffled.
            return;
        }
        
        if (rangeLength % 2 == 1) {
            // Once here, the range length is odd. Move the middle element to
            // the end of the array.
            rightRotate(array,
                        rangeLength / 2,
                        rangeLength / 2 + 1,
                        1);
            
            shuffle(array, fromIndex, toIndex - 1);
            return;
        }
        
        int twoTimesM;
        int threeToPowerK = 1;
        int k = 0;
        
        if (rangeLength == 2) {
            twoTimesM = 0;
        } else {
            while (true) {
                if (threeToPowerK <= rangeLength 
                        && rangeLength < threeToPowerK * 3) {
                    twoTimesM = threeToPowerK - 1;
                    break;
                }
                
                threeToPowerK *= 3;
                k++;
            }
        }
        
        do {
            rightRotate(array,
                        fromIndex + twoTimesM,
                        rangeLength / 2, 
                        twoTimesM / 2);

            for (int i = 0, tmp = 1; i < k; ++i, tmp *= 3) {
                int startIndex = fromIndex + tmp - 1;
                cycleLeader(array, startIndex, twoTimesM);
            }
            
            fromIndex += twoTimesM;
//            System.out.println("m = " + mTimesTwo);
        } while (twoTimesM != 0);
    } 
    
    private static int permutation(int i, int rangeLength) {
//        return 2 * i + 1 % (rangeLength + 1);
//        return 2 * (i + 1) % (rangeLength + 1);
//        return 2 * i  + 1 % (rangeLength);
//        return 2 * (i + 1) % (rangeLength);
//        return 2 * i % (rangeLength);
        return 2 * (i + 1) % (rangeLength + 1) - 1;
    }
    
    private static <E> void cycleLeader(E[] array, 
                                        int cycleLeaderIndex, 
                                        int rangeLength) {
        int i = permutation(cycleLeaderIndex, rangeLength);
        
        while (i != cycleLeaderIndex) {
            System.out.println("i = " + i);
            swap(array, cycleLeaderIndex, i);
            i = permutation(i, rangeLength);
        }
    }
    
    private static <E> void rightRotate(E[] array,
                                        int rangeStartIndex,
                                        int rangeLength,
                                        int rotations) {
        reverse(array, rangeStartIndex, rangeStartIndex + rangeLength);
        reverse(array, rangeStartIndex, rangeStartIndex + rotations);
        reverse(array, 
                rangeStartIndex + rotations, 
                rangeStartIndex + rangeLength);
    }
    
    private static <E> void reverse(E[] array, int fromIndex, int toIndex) {
        for (--toIndex; fromIndex < toIndex; fromIndex++, toIndex--) {
            swap(array, fromIndex, toIndex);
        }
    }
    
    private static <E> void swap(E[] array, int index1, int index2) {
        E tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
}
