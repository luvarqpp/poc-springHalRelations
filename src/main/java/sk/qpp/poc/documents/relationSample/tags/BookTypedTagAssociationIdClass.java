package sk.qpp.poc.documents.relationSample.tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookTypedTagAssociationIdClass implements Serializable {
    private Integer book;
    private Integer typedTag;
}
