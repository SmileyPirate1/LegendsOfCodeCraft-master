package Enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum HandType {
    oneHand,
    twoHand,
    offHand;

    // Vi ligger enum ind i en arrayliste og tag længden af listen som tal og finder et tilfældigt et af dem
    private static final List<HandType> values = Collections.unmodifiableList(Arrays.asList(HandType.values()));
    private static final int SIZE = values.size();
    private static final Random RANDOM = new Random();

    public static HandType random() {
        return values.get(RANDOM.nextInt(values.size()));
    }
}