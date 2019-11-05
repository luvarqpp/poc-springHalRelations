package sk.qpp.poc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import sk.qpp.poc.documents.PoCUser;
import sk.qpp.poc.documents.relationSample.Book;
import sk.qpp.poc.documents.relationSample.BookPublisher;
import sk.qpp.poc.documents.relationSample.BookPublisherIdEmbeddable;
import sk.qpp.poc.documents.relationSample.Publisher;
import sk.qpp.poc.documents.relationSample.tags.*;
import sk.qpp.poc.repository.BookPublisherRepository;
import sk.qpp.poc.repository.BookRepository;
import sk.qpp.poc.repository.PoCUserRepository;
import sk.qpp.poc.repository.PublisherRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
@Profile("fillTestData")
@Slf4j
@RequiredArgsConstructor
class LoadDatabase {
    private final PoCUserRepository poCUserRepository;
    private final BookRepository bookRepository;
    private final BookPublisherRepository bookPublisherRepository;
    private final PublisherRepository publisherRepository;
    private final BookTypedTagAssociationRepository bookTypedTagAssociationRepository;
    private final TypedTagRepository typedTagRepository;

    // TODO preload some sample data in dev profile to be able to get something for demo/showcase
    @Order(1)
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            PoCUser poCUser;
            log.info("Preloading " + (poCUser = poCUserRepository.save(
                    new PoCUser("Master123", "nbuMaster123@test.qpp.sk")
            )));

            /*
            final Book knizka1 = new Book("knizka1");
            this.bookRepository.save(knizka1);
            final Publisher autor1 = new Publisher("autor1");
            autor1.getBookPublishers().add(knizka1);
            this.publisherRepository.save(autor1);*/

            Book bookA = new Book("Book A");
            bookA = bookRepository.save(bookA);
            Book bookB = new Book("Book B");
            bookRepository.save(bookB);
            Book bookC = new Book("Book C");
            bookRepository.save(bookC);

            Publisher publisherA = new Publisher(poCUser, "Publisher A");
            publisherRepository.save(publisherA);

            BookPublisher bookPublisherAA = new BookPublisher();
            //bookPublisherAA.setBook(bookA);
            //bookPublisherAA.setPublisher(publisherA);
            bookPublisherAA.setPk(new BookPublisherIdEmbeddable(bookA, publisherA));
            bookPublisherAA.setPublishedDate(new Date());
//            bookA.getBookPublishers().add(bookPublisherAA);
            this.bookPublisherRepository.save(bookPublisherAA);

            BookPublisher bookPublisherBA = new BookPublisher();
            bookPublisherBA.setPk(new BookPublisherIdEmbeddable(bookB, publisherA));
            bookPublisherBA.setPublishedDate(new Date());
            this.bookPublisherRepository.save(bookPublisherBA);

            BookPublisher bookPublisherCA = new BookPublisher();
            bookPublisherCA.setPk(new BookPublisherIdEmbeddable(bookC, publisherA));
            bookPublisherCA.setPublishedDate(new Date());
            this.bookPublisherRepository.save(bookPublisherCA);
        };
    }

    @Order(10)
    @Bean
    CommandLineRunner initDatabase_addSomeTags() {
        return args -> {
            TypedTag ttFav = new TypedTag("my favourite", TagType.FREE_TAG);
            TypedTag ttRead = new TypedTag("should read later", TagType.FREE_TAG);
            TypedTag ttDoc = new TypedTag("Documentary", TagType.GENERE);
            TypedTag ttFan = new TypedTag("Fantasy", TagType.GENERE);
            TypedTag ttBind1 = new TypedTag("Saddle-stitching", TagType.BINDING_TYPE);
            TypedTag ttBind2 = new TypedTag("Perfect Binding", TagType.BINDING_TYPE);
            TypedTag ttBind3 = new TypedTag("Section Sewn", TagType.BINDING_TYPE);
            TypedTag ttBind4 = new TypedTag("Wire Binding", TagType.BINDING_TYPE);
            TypedTag ttBind5 = new TypedTag("Spiral Binding", TagType.BINDING_TYPE);
            TypedTag ttBind6 = new TypedTag("Cased-in Wiro Binding", TagType.BINDING_TYPE);
            TypedTag ttBind7 = new TypedTag("Pamphlet Binding", TagType.BINDING_TYPE);
            TypedTag ttBind8 = new TypedTag("Coptic Binding", TagType.BINDING_TYPE);
            TypedTag ttBind9 = new TypedTag("Japanese Binding", TagType.BINDING_TYPE);
            TypedTag ttBindA = new TypedTag("Screw-post Binding", TagType.BINDING_TYPE);
            this.typedTagRepository.saveAll(Arrays.asList(ttFav, ttRead, ttDoc, ttFan, ttBind1, ttBind2, ttBind3, ttBind4, ttBind5, ttBind6, ttBind7, ttBind8, ttBind9, ttBindA));

            List<Book> books = bookRepository.findAll();

            this.bookTypedTagAssociationRepository.save(new BookTypedTagAssociation(ttFav, books.get(0)));
            this.bookTypedTagAssociationRepository.save(new BookTypedTagAssociation(ttFan, books.get(0)));
            this.bookTypedTagAssociationRepository.save(new BookTypedTagAssociation(ttBind9, books.get(0)));

            this.bookTypedTagAssociationRepository.save(new BookTypedTagAssociation(ttDoc, books.get(1)));
            this.bookTypedTagAssociationRepository.save(new BookTypedTagAssociation(ttBind3, books.get(1)));

            this.bookTypedTagAssociationRepository.save(new BookTypedTagAssociation(ttDoc, books.get(2)));
            this.bookTypedTagAssociationRepository.save(new BookTypedTagAssociation(ttBind2, books.get(2)));
            this.bookTypedTagAssociationRepository.save(new BookTypedTagAssociation(ttBind3, books.get(2)));
            this.bookTypedTagAssociationRepository.save(new BookTypedTagAssociation(ttRead, books.get(2)));
        };
    }

    @Order(20)
    @Bean
    CommandLineRunner initDatabase_printTagsForBooks() {
        return args -> {

        };
    }

    @Bean
    CommandLineRunner asdf() {
        final BookRepository bookRepositoryLocal = this.bookRepository;
        return new CommandLineRunner() {
            @Transactional
            @Override
            public void run(String... args) {
                StringBuilder sb = new StringBuilder();
                final List<Book> allBooks = bookRepositoryLocal.findAll();
                for (Book b : allBooks) {
                    sb.append(b.getName() + ":\n");
                    for (BookTypedTagAssociation bta : b.getBookTypedTagAssociations()) {
                        sb.append("\t-> " + bta.getTypedTag() + "\n");
                    }
                } // end of for each b in allBooks
                System.out.println(sb.toString());
            }
        };
    }
}
