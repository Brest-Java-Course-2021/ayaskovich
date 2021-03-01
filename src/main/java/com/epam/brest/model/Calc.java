package com.epam.brest.model;

import com.epam.brest.files.CSVFileReader;
import com.epam.brest.files.FileReader;
import com.epam.brest.selector.PriceSelector;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class Calc implements Status{

    PriceSelector priceSelector = new PriceSelector();

    @Override
    public Status handle(Scanner scanner) {

        FileReader distancePriceFileReader = new CSVFileReader();
        try {
            Map<Integer, BigDecimal> distancePriceMap = distancePriceFileReader.readData("price_distance.csv");
            Map<Integer, BigDecimal> weightPriceMap = distancePriceFileReader.readData("price_weight.csv");

            System.out.println("Result: " + userData.get(0).multiply(priceSelector.selectPriceValue(distancePriceMap, userData.get(0))).add(
                                            userData.get(1).multiply(priceSelector.selectPriceValue(weightPriceMap, userData.get(1)))
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            userData.clear();
        }

        return new ReadData();
    }

    @Override
    public StatusType getType() {
        return StatusType.CALC;
    }
}
