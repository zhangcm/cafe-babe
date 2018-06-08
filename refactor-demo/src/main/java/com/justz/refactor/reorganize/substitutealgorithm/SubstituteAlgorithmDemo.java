package com.justz.refactor.reorganize.substitutealgorithm;

import java.util.Arrays;
import java.util.List;

/**
 * Substitute Algorithm 替换算法
 */
public class SubstituteAlgorithmDemo {

    String foundPerson(String[] people) {
        List<String> candidates = Arrays.asList(new String[] {"Don", "John", "Kent"});
        for (int i = 0; i < people.length; i++) {
            if (candidates.contains(people[i])) {
                return people[i];
            }
        }
        return "";
    }

}
