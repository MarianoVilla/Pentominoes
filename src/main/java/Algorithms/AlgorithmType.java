package Algorithms;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

public enum AlgorithmType {
	EB_AFIT(1);
    private final int value;
    private AlgorithmType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static AlgorithmType find(int algNo, Supplier<? extends AlgorithmType> byDef) {
        return Arrays.asList(AlgorithmType.values()).stream()
                .filter(e -> e.value == algNo).findFirst().orElseGet(byDef);
    }
}
