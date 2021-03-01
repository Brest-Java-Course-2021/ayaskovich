package com.epam.brest.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public interface Status {

    List<String> messages = new ArrayList<>(Arrays.asList("Please, enter distance: ", "Please, enter weight: "));

    List<BigDecimal> userData = new ArrayList<>();

    public Status handle(Scanner scanner);

    StatusType getType();

}
