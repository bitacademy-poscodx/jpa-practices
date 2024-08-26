package bookmall.domain.type;

import lombok.Getter;

@Getter
public enum GoodsType {
    BOOK ("BOOK"),
    EBOOK ("EBOOK"),
    DVD ("DVD"),
    CD ("CD");

    private final String type;

    GoodsType(String type) {
        this.type = type;
    }

    public String type() {
        return this.type;
    }
}