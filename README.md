# poc-springHalRelations
Simplest maven spring boot project to showcase one problem. This repo is a part of [stackoverflow question](https://stackoverflow.com/questions/57005713/make-collection-propertie-render-as-relation-instead-of-property-in-json-hal-rep).

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