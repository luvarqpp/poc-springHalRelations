package sk.qpp.poc.documents.relationSample;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "book_publisher")
@IdClass(BookPublisherIdClass.class)
@Data
@NoArgsConstructor
public class BookPublisher implements Serializable {
    @Id
    @OneToOne(optional = false)
    private Publisher publisher;

    @Id
    @OneToOne(optional = false)
    private Book book;

    @Column(name = "published_date")
    private Date publishedDate;
}
