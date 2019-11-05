package sk.qpp.poc.documents.relationSample.tags;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tag with some associated type.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class TypedTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String value;

    private TagType type;

    @OneToMany(mappedBy = "typedTag", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<BookTypedTagAssociation> bookTypedTagAssociation;

    /**
     * @param value
     * @param type
     * @param bookTypedTagAssociations set only {@link sk.qpp.poc.documents.relationSample.Book} reference.
     *                                 {@link TypedTag} reference will be filled in this constructor.
     */
    public TypedTag(String value, TagType type, BookTypedTagAssociation... bookTypedTagAssociations) {
        this.value = value;
        this.type = type;
        for (BookTypedTagAssociation bookTypedTagAssociation : bookTypedTagAssociations)
            bookTypedTagAssociation.setTypedTag(this);
        this.bookTypedTagAssociation = Stream.of(bookTypedTagAssociations).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "TypedTag{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
