package sk.qpp.poc.documents.relationSample.tags.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import sk.qpp.poc.documents.relationSample.tags.TagType;
import sk.qpp.poc.documents.relationSample.tags.TypedTag;

@Projection(name = "countRelations", types = TypedTag.class)
public interface TypedTagWithBooksCount {
    String getValue();

    TagType getType();

    @Value("#{target.getBookTypedTagAssociation().size()}")
    int getBookTypedTagAssociationCount();
}
