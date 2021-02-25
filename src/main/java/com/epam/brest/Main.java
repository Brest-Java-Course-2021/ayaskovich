package com.epam.brest;

import com.epam.brest.files.CSVFileReader;
import com.epam.brest.files.FileReader;
import com.epam.brest.selector.PriceSelector;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        FileReader distancePriceFileReader = new CSVFileReader();
        Map<Integer, BigDecimal> distancePriceMap = distancePriceFileReader.readData("price_distance.csv");
        Map<Integer, BigDecimal> weightPriceMap = distancePriceFileReader.readData("price_weight.csv");

        PriceSelector priceSelector = new PriceSelector();

        BigDecimal[] enteredValues = new BigDecimal[2];
        Scanner scanner = new Scanner(System.in);
        String inputValue;

        int i = 0;

        do {
            if(i == 0) {
                System.out.print("Please, enter distance: ");
            } else if(i == 1) {
                System.out.print("Please, enter weight: ");
            }

            inputValue = scanner.next();
            if(inputValue.equalsIgnoreCase("Q")) {
                break;
            } else if(isCorrectDoubleValue(inputValue)) {
                enteredValues[i] = new BigDecimal(inputValue);
                i++;
            } else {
                System.out.println("Incorrect value: " + inputValue);
            }

            if(i == 2) {
                BigDecimal distancePrice = priceSelector.selectPriceValue(distancePriceMap, enteredValues[0].toBigInteger());
                System.out.println("Distance price: " + distancePrice);

                BigDecimal weightPrice = priceSelector.selectPriceValue(distancePriceMap, enteredValues[1].toBigInteger());
                System.out.println("Weight price: " + weightPrice);

                BigDecimal resultPrice = enteredValues[0].multiply(distancePrice).add(enteredValues[1].multiply(weightPrice));
                System.out.println("Result price: " + resultPrice);

                i = 0;
            }


        } while(i < 2);

    }

    private static boolean isCorrectDoubleValue(String value) {
        boolean checkResult;
        try {
            double enteredValue = Double.parseDouble(value);
            checkResult = enteredValue >= 0;
        } catch(NumberFormatException ex) {
            checkResult = false;
        }
        return checkResult;
    }
}
