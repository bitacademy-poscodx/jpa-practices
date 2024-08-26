package inquiry.domain.type;

import lombok.Getter;

@Getter
public enum ProductType {
    COLD_ROLLED(NAMES.COLD_ROLLED),
    HOT_ROLLED(NAMES.HOT_ROLLED),
    WIRE_ROD(NAMES.WIRE_ROD),
    THICK_PLATE(NAMES.THICK_PLATE),
    CAR(NAMES.CAR);

    private final String type;

    ProductType(String type) {
        this.type = type;
    }

    public static final class NAMES {
        public static final String COLD_ROLLED = "Cold Rolled";
        public static final String HOT_ROLLED = "Hot Rolled";
        public static final String WIRE_ROD = "Wire Rod";
        public static final String THICK_PLATE = "Thick Plate";
        public static final String CAR = "Car";
    }
}
