package sk.qpp.poc.documents.relationSample;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
public class BookPublisherIdClass implements Serializable {
    private int book;
    private int publisher;
}
