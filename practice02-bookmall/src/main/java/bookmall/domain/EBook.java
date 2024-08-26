package bookmall.domain;

import bookmall.domain.type.GoodsType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ebook")
@DiscriminatorValue(GoodsType.BOOK)
public class EBook extends Goods {
    @NonNull
    @Column(name = "author", nullable = false, length = 20)
    private String author;

    @NonNull
    @Column(name = "isbn", nullable = false, length = 30)
    private String isbn;

    public EBook(int index, String title, int price, @NonNull String author, @NonNull String isbn) {
        super(index, title, price);

        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "EBook{" +
                "author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", super=" + super.toString() +
                "}";
    }
}
