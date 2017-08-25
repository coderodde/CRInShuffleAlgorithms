package net.coderodde.util.inshuffle;

/**
 * This interface defines the API for in-shuffle algorithms.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Aug 25, 2017)
 */
public interface InShuffleAlgorithm {
    
    /**
     * Performs an in-shuffle for the input array.
     * 
     * @param <E>   the array component type.
     * @param array the array to shuffle.
     */
    public <E> void shuffle(E[] array);
}
