package com.epam.brest.model;

import java.math.BigDecimal;
import java.util.Scanner;

public class ReadData implements Status{

    @Override
    public Status handle(Scanner scanner) {

        if(userData.size() < 2) {
            System.out.print(messages.get(userData.size()));
            String inputValue = scanner.next();
            if(inputValue.equalsIgnoreCase("Q")) {
                return new Exit();
            } else if(isCorrectValue(inputValue)) {
                userData.add(new BigDecimal(inputValue));
            }
        } else {
            return new Calc();
        }

        return this;
    }

    @Override
    public StatusType getType() {
        return StatusType.READ_DATA;
    }

    private boolean isCorrectValue(String value) {
        try {
            BigDecimal enteredValue = new BigDecimal(value);
            return enteredValue.doubleValue() > 0;
        } catch(NumberFormatException ex) {
            System.out.println("Incorrect value: " + value);
        }
        return false;
    }
}
