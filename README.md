# poc-springHalRelations
Mostly the simplest maven spring boot project to showcase one problem.
This repo was a part of [stackoverflow question](https://stackoverflow.com/questions/57005713/make-collection-propertie-render-as-relation-instead-of-property-in-json-hal-rep).
It is also part of answer to my [another self-question](https://stackoverflow.com/questions/56934294/what-are-differences-in-many-to-many-mapping-approach).

This branch does hold applied "partial" solution from Alan Hay and than (inspired by danny.lesnik from https://stackoverflow.com/questions/6405746/mapping-manytomany-with-composite-primary-key-and-annotation question/answer) applied another approach to fix IdClass problem with HAL.
IdClass is now changed to embeddable.
There is also another approach with IdClass (also working same way).
Current "problem" is one TODO in FixConfig.java file (hackish approach) and probably not ideal result in HAL format.
Resource BookPublisher does now "embedd" Book and Publisher instances, but does have missing links to these resources (because they are loaded in hackish way).

After app startup from PoCBackendMain class (or using `mvn spring-boot:run` command), you can visit http://localhost:8080/api in your browser to see hal browser, or you can cURL all publisher resources/entities loaded in LoadDatabase class:

```bash
curl http://localhost:8080/api/publishers
```
With result like this:
```json
{
  "_embedded" : {
    "publishers" : [ {
      "name" : "Publisher A",
      "_embedded" : {
        "author" : {
          "loginName" : "Master123",
          "email" : "nbuMaster123@test.qpp.sk"
        }
      },
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/publishers/1"
        },
        "publisher" : {
          "href" : "http://localhost:8080/api/publishers/1"
        },
        "author" : {
          "href" : "http://localhost:8080/api/publishers/1/contact"
        },
        "friends" : {
          "href" : "http://localhost:8080/api/publishers/1/friends"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/api/publishers{?page,size,sort}",
      "templated" : true
    },
    "profile" : {
      "href" : "http://localhost:8080/api/profile/publishers"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 1,
    "totalPages" : 1,
    "number" : 0
  }
}
```

## Approach with @EmbeddableId

Associating Book entity with Publisher entity is done using EmbeddableId.

More important is, that there is now associative endpoint for managing associations between Book and Publisher instances with some additional data (publishedDate, as implemented in BookPublisher class).
You can see here single BookPublisher instance.
```bash
curl http://localhost:8080/api/bookPublishers/
```
```json
{
  "_embedded" : {
    "bookPublishers" : [ {
       "publishedDate" : "2019-11-05T12:51:39.609+0000",
       "book" : {
         "name" : "Book A"
       },
       "publisher" : {
         "name" : "Publisher A"
       },
       "_links" : {
         "self" : {
           "href" : "http://localhost:8080/api/bookPublishers/1_1"
         },
         "bookPublisher" : {
           "href" : "http://localhost:8080/api/bookPublishers/1_1"
         }
       }
     } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/api/bookPublishers{?page,size,sort}",
      "templated" : true
    },
    "profile" : {
      "href" : "http://localhost:8080/api/profile/bookPublishers"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 1,
    "totalPages" : 1,
    "number" : 0
  }
}
```

## Approach with @IdClass

Associating Book entity with TypedTag entity is done using IdClass. This is imho working nearly as I expect.

```bash
curl http://localhost:8080/api/bookTypedTagAssociations?size=1
```
Booth entities included in this association does have embedded its values (can be alerted using projections presented in code and comments) and also associations link.
```json
{
  "_embedded" : {
    "bookTypedTagAssociations" : [ {
      "additionalInfoForTagAssociation" : "Skúška",
      "_embedded" : {
        "book" : {
          "name" : "Book A"
        },
        "typedTag" : {
          "value" : "my favourite",
          "type" : "FREE_TAG"
        }
      },
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/bookTypedTagAssociations/1_1"
        },
        "bookTypedTagAssociation" : {
          "href" : "http://localhost:8080/api/bookTypedTagAssociations/1_1{?projection}",
          "templated" : true
        },
        "book" : {
          "href" : "http://localhost:8080/api/bookTypedTagAssociations/1_1/book"
        },
        "typedTag" : {
          "href" : "http://localhost:8080/api/bookTypedTagAssociations/1_1/typedTag{?projection}",
          "templated" : true
        }
      }
    } ]
  },
  "_links" : {
    "first" : {
      "href" : "http://localhost:8080/api/bookTypedTagAssociations?page=0&size=1"
    },
    "self" : {
      "href" : "http://localhost:8080/api/bookTypedTagAssociations{&sort,projection}",
      "templated" : true
    },
    "next" : {
      "href" : "http://localhost:8080/api/bookTypedTagAssociations?page=1&size=1"
    },
    "last" : {
      "href" : "http://localhost:8080/api/bookTypedTagAssociations?page=8&size=1"
    },
    "profile" : {
      "href" : "http://localhost:8080/api/profile/bookTypedTagAssociations"
    },
    "search" : {
      "href" : "http://localhost:8080/api/bookTypedTagAssociations/search"
    }
  },
  "page" : {
    "size" : 1,
    "totalElements" : 9,
    "totalPages" : 9,
    "number" : 0
  }
}
```