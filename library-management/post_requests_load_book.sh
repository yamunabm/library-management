curl -X POST "http://localhost:8080/library/v1/book?author=Yamuna&id=111&isbn=1234&title=DS" -H "accept: */*"
curl -X POST "http://localhost:8080/library/v1/bookstorage" -H "accept: */*" -H "Content-Type: application/json" -d "[ { \"bookId\": \"111\", \"quantity\": 1 }]"

