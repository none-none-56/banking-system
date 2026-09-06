# Banking System

Spring Boot API for transferring money between accounts. In memory, no database.

## Running

    ./mvnw spring-boot:run

Runs on port 8080. Seeds acc1 with 1000 and acc2 with 5000 on startup.

## Tests

    ./mvnw test

requests.sh hits every case against a running app if you want to see the
responses. It doesn't assert anything.

## Endpoints

POST /api/transfer

    {"from": "acc1", "to": "acc2", "amount": 100.00}

200 on success. 404 unknown account, 422 insufficient funds, 400 for a
self transfer or a bad amount.

GET /api/accounts lists balances. Only there so I could check state while
testing.

## TODO

- No idempotency. A client that times out and retries will transfer twice.
- No database, no transfer history, no auth.
- No web layer tests. Went with the service tests and the concurrency one
  instead.