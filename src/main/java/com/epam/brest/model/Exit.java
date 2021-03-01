package com.epam.brest.model;

import java.util.Scanner;

public class Exit implements Status{

    @Override
    public Status handle(Scanner scanner) {
        return null;
    }

    @Override
    public StatusType getType() {
        return StatusType.EXIT;
    }
}
