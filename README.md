# poc-springHalRelations
Simplest maven spring boot project to showcase one problem. This repo is a part of [stackoverflow question](https://stackoverflow.com/questions/57005713/make-collection-propertie-render-as-relation-instead-of-property-in-json-hal-rep).

This branch does hold applied "partial" solution from Alan Hay and than (inspired by danny.lesnik from https://stackoverflow.com/questions/6405746/mapping-manytomany-with-composite-primary-key-and-annotation question/answer) applied another approach to fix IdClass problem with HAL. IdClass is now changed to embeddable. Current "problem" is one TODO in FixConfig.java file (hackish approach) and probably not ideal result in HAL format. Resource BookPublisher does now "embedd" Book and Publisher instances, but does have missing links to these resources (because they are loaded in hackish way).

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
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/publishers/1"
        },
        "publisher" : {
          "href" : "http://localhost:8080/api/publishers/1"
        },
        "friends" : {
          "href" : "http://localhost:8080/api/publishers/1/friends"
        },
        "createdBy" : {
          "href" : "http://localhost:8080/api/publishers/1/contact"
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

More important is, that there is now associative endpoint for managing associations between Book and Publisher instances with some additional data (publishedDate, as implemented in BookPublisher class).
You can see here single BookPublisher instance.
There is now new problem, as you are not able to associate multiple books with same publisher, due to `@OneToOne` annotation, which does load entities in `BookPublisher` class.
**Mapping is wrong**, but hal representation is nearly as expected.
```bash
curl http://localhost:8080/api/bookPublishers/
```
```json
{
  "_embedded" : {
    "bookPublishers" : [ {
      "publishedDate" : "2019-09-05T12:44:42.905+0000",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/bookPublishers/1_1"
        },
        "bookPublisher" : {
          "href" : "http://localhost:8080/api/bookPublishers/1_1"
        },
        "publisher" : {
          "href" : "http://localhost:8080/api/bookPublishers/1_1/publisher"
        },
        "book" : {
          "href" : "http://localhost:8080/api/bookPublishers/1_1/book"
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

After applying approach from danny.lesnik question/answer (see beginning of readme for link), result has changed like this:
```bash
curl http://localhost:8080/api/bookPublishers/
```
```json
{
  "_embedded" : {
    "bookPublishers" : [ {
      "publishedDate" : "2019-09-06T09:39:45.989+0000",
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
    }, {
      "publishedDate" : "2019-09-06T09:39:46.005+0000",
      "book" : {
        "name" : "Book B"
      },
      "publisher" : {
        "name" : "Publisher A"
      },
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/bookPublishers/2_1"
        },
        "bookPublisher" : {
          "href" : "http://localhost:8080/api/bookPublishers/2_1"
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
    "totalElements" : 2,
    "totalPages" : 1,
    "number" : 0
  }
}
```
