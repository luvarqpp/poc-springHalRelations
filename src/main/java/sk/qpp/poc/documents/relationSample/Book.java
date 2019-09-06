package sk.qpp.poc.documents.relationSample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Id @GeneratedValue(generator="system-uuid")
    //@GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "book_id", nullable = false)
    private int id;

    private String name;

    //@OneToMany(mappedBy = "id.book", cascade = CascadeType.ALL, orphanRemoval = true)
    //private Set<BookPublisher> bookPublishers;

    public Book(String name) {
        this.name = name;
        //bookPublishers = new HashSet<>();
    }
}
