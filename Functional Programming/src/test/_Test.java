package test;

import org.testng.annotations.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class _Test {
    @Test
    public void givenTwoCollections_whenStreamedSequentially_thenCheckOutputDifferent() {
        List<String> list = Arrays.asList("B", "A", "C", "D", "F");
        Set<String> treeSet = new TreeSet<>(list);


        Object[] listOutput = list.stream().toArray();
        Object[] setOutput = treeSet.stream().toArray();

        assertEquals("[B, A, C, D, F]", Arrays.toString(listOutput));
        assertEquals("[A, B, C, D, F]", Arrays.toString(setOutput));

    }

    @Test
    public void testSetOrdering(){
        List<String> list = Arrays.asList("A", "BB", "CCC");

        Map<String, Integer> hashMap = list.stream().collect(Collectors
                .toMap(Function.identity(), String::length));
        Map<String, Integer> hashMap1 = list.stream().collect(Collectors.toMap(
            Function.identity(),
            String::length,
            (u,v)->u,
            LinkedHashMap::new
        ));

        Object[] keySet = hashMap.keySet().toArray();

        assertEquals("[BB, A, CCC]", Arrays.toString(keySet));
    }
    @Test
    public void givenSameCollection_whenStreamCollected_checkOutput() {

    }
}
