package bookmall.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book")
@DiscriminatorValue("BOOK")
public class Book extends Goods {
    @NonNull
    @Column(name = "author", nullable = false, length = 20)
    private String author;

    @NonNull
    @Column(name = "isbn", nullable = false, length = 30)
    private String isbn;

    public Book(int index, String title, int price, @NonNull String author, @NonNull String isbn) {
        super(index, title, price);

        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", " + super.toString() +
                "}";
    }
}