package sk.qpp.poc;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import sk.qpp.poc.documents.relationSample.Book;
import sk.qpp.poc.documents.relationSample.BookPublisher;
import sk.qpp.poc.documents.relationSample.BookPublisherIdEmbeddable;
import sk.qpp.poc.documents.relationSample.Publisher;
import sk.qpp.poc.documents.relationSample.tags.BookTypedTagAssociation;
import sk.qpp.poc.documents.relationSample.tags.BookTypedTagAssociationIdClass;
import sk.qpp.poc.documents.relationSample.tags.BookTypedTagAssociationRepository;
import sk.qpp.poc.repository.BookPublisherRepository;
import sk.qpp.poc.repository.BookRepository;
import sk.qpp.poc.repository.PublisherRepository;

import java.io.Serializable;

// see https://jira.spring.io/browse/DATAJPA-770
// source "copied" from https://github.com/dariotortola/jira-datajpa-770/blob/master/datajpa-770/datajpa-770/src/main/java/jira/FixConfig.java
@Configuration
@AllArgsConstructor
public class FixConfig implements RepositoryRestConfigurer {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.withEntityLookup()
                .forRepository(BookPublisherRepository.class)
                .withIdMapping((BookPublisher ed) -> {
                    BookPublisherIdEmbeddable pk = new BookPublisherIdEmbeddable();
                    pk.setBook(ed.getBook());
                    pk.setPublisher(ed.getPublisher());
                    return pk;
                })
                .withLookup((r, id) -> r.findById(id))

                .forRepository(BookTypedTagAssociationRepository.class)
                .withIdMapping((BookTypedTagAssociation ed) -> {
                    BookTypedTagAssociationIdClass pk = new BookTypedTagAssociationIdClass();
                    // if Book has id field of type "int", it will throw here!
                    pk.setBook(ed.getBook().getId());
                    pk.setTypedTag(ed.getTypedTag().getId());
                    return pk;
                })
                .withLookup((r, id) -> r.findById(id));
    }

    @Bean
    public BackendIdConverter bookPublisherIdConverter() {
        return new BackendIdConverter() {

            @Override
            public boolean supports(Class<?> delimiter) {
                return BookPublisher.class.equals(delimiter);
            }

            @Override
            public String toRequestId(Serializable id, Class<?> entityType) {
                BookPublisherIdEmbeddable pk = (BookPublisherIdEmbeddable) id;
                return String.format("%s_%s", pk.getBook().getId(), pk.getPublisher().getId());
            }

            @Override
            public Serializable fromRequestId(String id, Class<?> entityType) {
                if (id == null) {
                    return null;
                }
                String[] parts = id.split("_");
                BookPublisherIdEmbeddable pk = new BookPublisherIdEmbeddable();
                // TODO consult this "hackish" approach to load entities and do copy them into pojo with someone!
                final Book book = bookRepository.getOne(Integer.valueOf(parts[0]));
                pk.setBook(book.toBuilder().build());
                final Publisher publisher = publisherRepository.getOne(Integer.valueOf(parts[1]));
                pk.setPublisher(publisher.toBuilder().build());
                return pk;
            }
        };
    }

    @Bean
    public BackendIdConverter bookTypedTagIdConverter() {
        return new BackendIdConverter() {

            @Override
            public boolean supports(Class<?> delimiter) {
                return BookTypedTagAssociation.class.equals(delimiter);
            }

            @Override
            public String toRequestId(Serializable id, Class<?> entityType) {
                BookTypedTagAssociationIdClass pk = (BookTypedTagAssociationIdClass) id;
                return String.format("%s_%s", pk.getBook(), pk.getTypedTag());
            }

            @Override
            public Serializable fromRequestId(String id, Class<?> entityType) {
                if (id == null) {
                    return null;
                }
                String[] parts = id.split("_");
                BookTypedTagAssociationIdClass pk = new BookTypedTagAssociationIdClass();
                pk.setBook(Integer.valueOf(parts[0]));
                pk.setTypedTag(Integer.valueOf(parts[1]));
                return pk;
            }
        };
    }
}

