package sk.qpp.poc.documents.relationSample;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.qpp.poc.documents.relationSample.tags.BookTypedTagAssociation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
// Do not serialize "lazy loaded" fields.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Id @GeneratedValue(generator="system-uuid")
    //@GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "book_id", nullable = false)
    // TODO have a look at type. Why it is not alloved to use "int" here? It is a bit more natural for "nullable = false" field, but detecting "new entity" is probably easier with id==null...
    private Integer id;

    private String name;

    //@OneToMany(mappedBy = "id.book", cascade = CascadeType.ALL, orphanRemoval = true)
    //private Set<BookPublisher> bookPublishers;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BookTypedTagAssociation> bookTypedTagAssociations = new HashSet<>();

    public Book(String name) {
        this.name = name;
        //bookPublishers = new HashSet<>();
    }
}
