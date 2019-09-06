package sk.qpp.poc.documents.relationSample;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "book_publisher")
@AssociationOverrides({
        @AssociationOverride(name = "pk.book", joinColumns = @JoinColumn(name = "book_id")),
        @AssociationOverride(name = "pk.publisher", joinColumns = @JoinColumn(name = "publisher_id"))
})
@Data
@NoArgsConstructor
public class BookPublisher implements Serializable {

    @EmbeddedId
    private BookPublisherIdClass pk = new BookPublisherIdClass();

    @Transient
    public Publisher getPublisher() {
        return this.pk.getPublisher();
    }

    @Transient
    public Book getBook() {
        return this.pk.getBook();
    }

    @Column(name = "published_date")
    private Date publishedDate;
}
