package sk.qpp.poc.documents.relationSample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;
import sk.qpp.poc.documents.PoCUser;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @ManyToOne(optional = false)
    @RestResource(rel = "createdBy")
    private PoCUser contact;

    @ManyToMany
    @RestResource(rel = "friends")
    private Set<PoCUser> friends;

    @OneToMany(mappedBy = "publisher")
    private Set<BookPublisher> bookPublishers;

    public Publisher(PoCUser loginName, String publisher) {
        this.contact = loginName;
        this.name = publisher;
    }
}
