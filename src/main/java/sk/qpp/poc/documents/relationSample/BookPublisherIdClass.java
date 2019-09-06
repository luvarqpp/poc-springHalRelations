package sk.qpp.poc.documents.relationSample;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BookPublisherIdClass implements Serializable {
    @ManyToOne
    private Book book;

    @ManyToOne
    private Publisher publisher;
}
