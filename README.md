# poc-springHalRelations
Simplest maven spring boot project to showcase one problem. This repo is a part of stackoverflow question.

After app startup from PoCBackendMain class, you can visit http://localhost:8080/api in your browser to see hal browser, or you can cURL all publisher resources/entities loaded in LoadDatabase class:

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