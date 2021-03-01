package com.epam.brest.selector;

import org.junit.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class PriceSelectorTest {

    PriceSelector priceSelector = new PriceSelector();
    Map<Integer, BigDecimal> values = Map.of(
        1, new BigDecimal(100),
        2, new BigDecimal(200),
        3, new BigDecimal(300)
    );

    @org.junit.Test
    public void selectPriceMediumValue() {
        BigDecimal result = priceSelector.selectPriceValue(values, new BigDecimal(2));
        Assert.assertTrue("Result value is incorrect", result.equals(new BigDecimal(200)));
    }

    @org.junit.Test
    public void selectPriceZeroValue() {
        BigDecimal result = priceSelector.selectPriceValue(values, new BigDecimal(0));
        Assert.assertTrue("Result value is incorrect", result.equals(new BigDecimal(100)));
    }

    @org.junit.Test
    public void selectPriceMaxValue() {
        BigDecimal result = priceSelector.selectPriceValue(values, new BigDecimal(4));
        Assert.assertTrue("Result value is incorrect", result.equals(new BigDecimal(300)));
    }

    @org.junit.Test
    public void selectPriceIncorrectValue() {
        BigDecimal result = priceSelector.selectPriceValue(values, new BigDecimal(-1));
        Assert.assertTrue("Result value is incorrect", result.equals(new BigDecimal(100)));
    }
}