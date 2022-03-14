# Library Management

## To run on local

Tech stack used:

- Java >= 8
- SpringBoot 2.5.4
- REST
- Maven
- Swagger

## Build and Run Application

Go to the project home directory and run below command
```bash
$ mvn clean install
$ java -jar target/library-management-0.0.1-SNAPSHOT.jar
```

Now hit this link http://localhost:8080/swagger-ui.html in browser.

## Using docker

Required software:

-   docker
-   docker-compose

Go to the project home directory and run below command

```bash
$ docker-compose up
```

Now hit this link http://localhost:8080/swagger-ui.html in browser.

## APIs

View all books from Library

```
GET "http://localhost:8080/library/v1/bookstorage/catalog"
```

Add book to Library

```
POST "http://localhost:8080/library/v1/bookstorage" 
[
  {
    "bookId": "111",
    "quantity": 1
  }
]
```

Barrow book from Library

```
GET "http://localhost:8080/library/v1/bookstorage/barrow" 
```

View borrowed books by user id

```
GET "http://localhost:8080/library/v1/bookstorage/{userId}"
```

Return books to Library

```
POST "http://localhost:8080/library/v1/bookstorage/return"
{
    "bookId" : "111",
    "userID" : "123"
}
```


View book stock by book id

```
GET "http://localhost:8080/library/v1/bookstorage/stock/123"
```

 Reset library

```
DELETE "http://localhost:8080/library/v1/bookstorage"

```
