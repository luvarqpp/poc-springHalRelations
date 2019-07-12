package sk.qpp.poc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.ForwardedHeaderFilter;

@EnableJpaAuditing
@SpringBootApplication
public class PoCBackendMain {
    public static void main(String[] args) {
        SpringApplication.run(PoCBackendMain.class, args);
    }

    /**
     * TODO only include this bean if reverse proxy is in path.
     * <p>
     * This makes spring accept x-forwarded-* magic to work. Have a look at documentation:
     * https://docs.spring.io/spring-hateoas/docs/current-SNAPSHOT/reference/html/#server.link-builder.forwarded-headers
     * or have a look at issue corresponding to resulting problems if not used:
     * https://github.com/spring-projects/spring-hateoas/issues/862
     *
     * @return
     */
    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }
}