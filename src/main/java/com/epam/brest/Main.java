package com.epam.brest;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        double[] enteredValues = new double[]{0.0, 0.0, 0.0, 0.0};
        String[] requests = new String[] {
                "Please, enter distance: ",
                "Please, enter price per km: ",
                "Please, enter weight: ",
                "Please, enter price per kg: "
        };
        int i = 0;

        do {
            System.out.println("If you want to exit, enter \"0\".");
            System.out.print(requests[i]);

            while(!scanner.hasNextDouble()) {
                    System.out.print("The value must be a number! "+requests[i]);
                    scanner.next();
            }
            double inputValue = scanner.nextDouble();
            if (inputValue > 0) {
                enteredValues[i] = inputValue;
                i++;
            } else if (inputValue < 0){
                System.out.println("The value must be greater than zero!");
            } else {
                break;
            }
        } while(i < 4);

        getResult(enteredValues);
    }

    private static void getResult(double[] enteredValues) {
        boolean checkValues = true;
        for(double enteredValue : enteredValues) {
            if(enteredValue == 0.0)
                checkValues = false;
        }
        if(checkValues) {
            System.out.println("Final price is " + (enteredValues[0]*enteredValues[1] + enteredValues[2]*enteredValues[3]) + "$");
        } else {
            System.out.println("You didn't enter all the required values. The final price can't be calculated.");
        }
    }
}
