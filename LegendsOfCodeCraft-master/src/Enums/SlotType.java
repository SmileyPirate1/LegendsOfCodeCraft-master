package Enums;

import java.util.*;

public enum SlotType {
        head,
        chest,
        hands,
        legs,
        feet,
        shoulders,
        cloak,
        shield;

        // Vi ligger enum ind i en arrayliste og tag længden af listen som tal og finder et tilfældigt et af dem
        private static final List<SlotType> values = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = values.size();
        private static final Random RANDOM = new Random();

        public static SlotType random() {
            return values.get(RANDOM.nextInt(values.size()));
        }
}


