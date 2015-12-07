# Vote App
To start application run the following command (java 8 is required)
> mvn spring-boot:run

The following users are available:
- admin/password (role ADMIN)
- user1/password (role USER)
- user2/password (role USER)

The following Restorants are available:
- Metropol (id=1)
- Ugly Coytote (id=2)

### Restorant API 
#### /restorants

GET - retrieves a list of restorants with a menu

    curl -u admin:password http://127.0.0.1:8080/api/restorants

### Menu API 
#### /restorants/{restorant_id}/menu

GET - get a restorant menu

    curl -u admin:password http://127.0.0.1:8080/api/restorants/1/menu

PUT/POST - updates restorant menu

    curl -u admin:password -H "Content-Type: application/json" -X POST -d '[{"name":"fish","price":21.1}, {"name":"chips","price":1.82}]' 

### Votes API 
#### /votes/restorants
GET - get votes results

    curl -u admin:password http://127.0.0.1:8080/api/votes/restorants

PUT/POST - make a vote

    curl -u admin:password -H "Content-Type: application/json" -X POST -d '1' 
