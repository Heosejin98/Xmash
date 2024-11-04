package com.tmp.xmash.common;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class AppConstants {
    public final static int CURRENT_SEASON = 2; //TODO: DB에서 읽어오도록 바꿔야함
    public final static int DEFAULT_WINNER_LP = 10;

    public final static RandomGenerator RANDOM_GENERATOR = RandomGeneratorFactory.of("Random").create();

    private AppConstants() {
        throw new AssertionError();
    }
}
