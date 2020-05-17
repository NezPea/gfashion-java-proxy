package com.gfashion.data;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class TestDataFactory {

    private static final Random ID_GENERATOR;

    static {
        ID_GENERATOR = new Random();
    }

    Integer getNextId() {
        return ID_GENERATOR.nextInt();
    }

}
