package com.epam.brest.selector;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class PriceSelector {

    public BigDecimal selectPriceValue(Map<Integer, BigDecimal> valuesMap, BigInteger targetValue) {
        SortedSet<Integer> sortedKeys = new TreeSet<>(valuesMap.keySet());
        Integer resultKey = sortedKeys.first();
        for(Integer key : sortedKeys) {
            if(resultKey >= targetValue.doubleValue()) {
                break;
            } else {
                resultKey = key;
            }
        }
        BigDecimal resultValue = valuesMap.get(resultKey);
        return resultValue;
    }

}
