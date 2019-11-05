package sk.qpp.poc.documents.relationSample.tags.projections;

import org.springframework.data.rest.core.config.Projection;
import sk.qpp.poc.documents.relationSample.tags.BookTypedTagAssociation;
import sk.qpp.poc.documents.relationSample.tags.TypedTag;

@Projection(types = BookTypedTagAssociation.class, name = "eagerLoadedTypedTag")
public interface BookTypedTagAssociationExtendedProjection {
    // Declaration of this method does embedd TypedTag instance directly to BookTypedTagAssociation projection (or excerpt). Link to TypedTag association is left still in _links attribute.
    TypedTag getTypedTag();

    //Book getBook();

    String getAdditionalInfoForTagAssociation();

    // Also some spel constructed values can be provided here. For example you can have a look at "user based" values, see https://stackoverflow.com/questions/28794145/spring-data-rest-security-based-projection
    //@Value("#{target.getTypedTag().toString()}")
    //String getTypedTagEager();
}
