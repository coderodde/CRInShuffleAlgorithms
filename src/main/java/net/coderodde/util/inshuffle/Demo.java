package net.coderodde.util.inshuffle;

import java.util.Arrays;
import java.util.Random;
import net.coderodde.util.inshuffle.support.InPlaceInShuffleAlgorithmV2;
import net.coderodde.util.inshuffle.support.NaiveInShuffleAlgorithm;

public class Demo {

    public static void main(String[] args) {
        demo();
    }
    
    private static final void demo() {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        System.out.println("--- Seed = " + seed + " ---");
        
        for (int length = 2; length < 100; length++) {
            Integer[] array1 = getRandomIntegerArray(length, random);
            Integer[] array2 = array1.clone();
            
            new InPlaceInShuffleAlgorithmV2().shuffle(array1);
            new NaiveInShuffleAlgorithm().shuffle(array2);
            
            if (!Arrays.equals(array1, array2)) {
                System.out.println("faild on " + length);
            }
        }
    }
    
    private static final Integer[] getRandomIntegerArray(int length,
                                                         Random random) {
        Integer[] array = new Integer[length];
        
        for (int i = 1; i <= length; ++i) {
            array[i - 1] = i;
        }
        
        return array;
    }
}
