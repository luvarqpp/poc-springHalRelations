package sk.qpp.poc.documents.relationSample.tags;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qpp.poc.documents.relationSample.Book;

import javax.persistence.*;

/**
 * @see sk.qpp.poc.documents.relationSample.BookPublisher another example of many to many association
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(BookTypedTagAssociationIdClass.class)
public class BookTypedTagAssociation {
    @Id @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typedTag_id", nullable = false, insertable = false, updatable = false)
    private TypedTag typedTag;

    @Id @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false, insertable = false, updatable = false)
    private Book book;

    private String additionalInfoForTagAssociation = "Skúška";

    public BookTypedTagAssociation(TypedTag typedTag, Book book) {
        this.typedTag = typedTag;
        this.book = book;
    }
}
