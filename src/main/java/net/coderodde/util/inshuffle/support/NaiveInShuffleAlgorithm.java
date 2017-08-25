package net.coderodde.util.inshuffle.support;

import java.util.Arrays;
import net.coderodde.util.inshuffle.InShuffleAlgorithm;

/**
 * This class implements a naïve in-shuffle algorithm that relies on auxiliary
 * memory.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Aug 25, 2017)
 */
public final class NaiveInShuffleAlgorithm implements InShuffleAlgorithm {

    /**
     * Implements a naïve in-shuffle algorithm that utilizes memory. Runs in
     * linear time and uses linear amount of storage.
     * 
     * @param <E>   the array component type.
     * @param array the array to shuffle.
     */
    @Override
    public <E> void shuffle(E[] array) {
        int leftPartLength = (array.length >>> 1) + 
                            ((array.length & 1) != 0 ? 1 : 0);
        
        E[] leftPart  = Arrays.copyOfRange(array, 0, leftPartLength);
        E[] rightPart = Arrays.copyOfRange(array, leftPartLength, array.length);
        
        int targetIndex    = 0;
        int leftPartIndex  = 0;
        int rightPartIndex = 0;
        
        boolean loadFromLeftPart = true;
        
        while (targetIndex < array.length) {
            array[targetIndex++] = 
                    loadFromLeftPart ? 
                        leftPart[leftPartIndex++] :
                        rightPart[rightPartIndex++];
            
            loadFromLeftPart = !loadFromLeftPart;
        }
    }
}
