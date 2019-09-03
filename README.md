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
      "bookPublishers" : [ {
        "publishedDate" : "2019-07-12T10:53:15.227+0000",
        "_links" : {
          "book" : {
            "href" : "http://localhost:8080/api/books/2"
          },
          "publisher" : {
            "href" : "http://localhost:8080/api/publishers/1"
          }
        }
      }, {
        "publishedDate" : "2019-07-12T10:53:15.197+0000",
        "_links" : {
          "book" : {
            "href" : "http://localhost:8080/api/books/1"
          },
          "publisher" : {
            "href" : "http://localhost:8080/api/publishers/1"
          }
        }
      } ],
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/publishers/1"
        },
        "publisher" : {
          "href" : "http://localhost:8080/api/publishers/1"
        },
        "createdBy" : {
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
}%
```

This is solution branch according AlanHay. Given cURL command now returns this:
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
        },
        "bookPublishers" : {
          "href" : "http://localhost:8080/api/publishers/1/bookPublishers"
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
}%
```
