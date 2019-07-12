package sk.qpp.poc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import sk.qpp.poc.documents.PoCUser;
import sk.qpp.poc.documents.relationSample.Book;
import sk.qpp.poc.documents.relationSample.BookPublisher;
import sk.qpp.poc.documents.relationSample.Publisher;
import sk.qpp.poc.repository.BookRepository;
import sk.qpp.poc.repository.PublisherRepository;
import sk.qpp.poc.repository.PoCUserRepository;

import java.util.Date;

@Configuration
@Profile("fillTestData")
@Slf4j
@RequiredArgsConstructor
class LoadDatabase {
    private final PoCUserRepository poCUserRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    // TODO preload some sample data in dev profile to be able to get something for demo/showcase
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
            Book bookB = new Book("Book B");
            Publisher publisherA = new Publisher(poCUser, "Publisher A");

            BookPublisher bookPublisherAA = new BookPublisher();
            bookPublisherAA.setBook(bookA);
            bookPublisherAA.setPublisher(publisherA);
            bookPublisherAA.setPublishedDate(new Date());

            bookA.getBookPublishers().add(bookPublisherAA);

            publisherRepository.save(publisherA);
            var a = bookRepository.save(bookA);

            {
                BookPublisher bookPublisherBA = new BookPublisher();
                bookPublisherBA.setBook(bookB);
                bookPublisherBA.setPublisher(publisherA);
                bookPublisherBA.setPublishedDate(new Date());
                bookB.getBookPublishers().add(bookPublisherBA);
                bookRepository.save(bookB);
            }

            System.out.println(a);
        };
    }
}
