package net.coderodde.util.inshuffle.support;

import java.util.Arrays;
import net.coderodde.util.inshuffle.InShuffleAlgorithm;
import org.junit.Test;
import static org.junit.Assert.*;

public class InPlaceInShuffleAlgorithmTest {
    
    private final InShuffleAlgorithm algorithm = 
            new InPlaceInShuffleAlgorithm();
    
    @Test
    public void testShuffleEvenLength1() {
        String[] input = {"A", "B", "C", "1", "2", "3"};
        String[] expectedOutput = {"A", "1", "B", "2", "C", "3"};
        algorithm.shuffle(input);
        assertTrue(Arrays.equals(expectedOutput, input));
    }

    @Test
    public void testShuffleEvenLength2() {
        String[] input = {"A", "B", "C", "D", "E", "1", "2", "3", "4", "5"};
        String[] expectedOutput = 
                         {"A", "1", "B", "2", "C", "3", "D", "4", "E", "5"};
        
        algorithm.shuffle(input);
        assertTrue(Arrays.equals(expectedOutput, input));
    }    
    
    @Test
    public void testShuffleOddLength1() {
        String[] input = {"A", "B", "C", "D", "1", "2", "3"};
        String[] expectedOutput = {"A", "1", "B", "2", "C", "3", "D"};
        algorithm.shuffle(input);
        assertTrue(Arrays.equals(expectedOutput, input));
    }
    
    @Test
    public void testShuffleOddLength2() {
        String[] input = {"A", "B", "C", "D", "E", "1", "2", "3", "4"};
        String[] expectedOutput = {"A", "1", "B", "2", "C", "3", "D", "4", "E"};
        algorithm.shuffle(input);
        assertTrue(Arrays.equals(expectedOutput, input));
    }
    
    @Test(expected = NullPointerException.class) 
    public void testThrowsNullPointerExceptionOnNullArray() {
        algorithm.shuffle(null);
    }
}
