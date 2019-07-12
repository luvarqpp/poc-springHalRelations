package sk.qpp.poc.documents.commonparts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.rest.core.annotation.RestResource;
import sk.qpp.poc.documents.PoCUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Common entity class with common fields for many of our entities. At least each entity which can be created/updated
 * by {@link PoCUser} should extend this class to have transparently filled/updated common
 * fields defined here.
 * <p>
 * Class is highly inspired by tutorial available here:
 * https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
@Getter
@NoArgsConstructor
public abstract class AuditModel implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false) //TODO , updatable = false)
    @CreatedDate
    // hibernate way: @CreationTimestamp
    private Date createdAt;

    /**
     * Creator of given entity
     */
    @ManyToOne(optional = false)
    @RestResource(rel = "createdBy")
    private PoCUser author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    /**
     * Only consctructor. Fields {@link #updatedAt} and {@link #createdAt} are populated by spring magic.
     *
     * @param author
     */
    public AuditModel(PoCUser author) {
        this.author = author;
    }
}
