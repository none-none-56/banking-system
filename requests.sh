curl -X GET localhost:8080/api/accounts

curl -X POST localhost:8080/api/transfer \
-H "Content-Type: application/json" \
-d '{"from":"acc1","to":"acc2","amount":500}'