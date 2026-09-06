#!/usr/bin/env bash
# Fires one request per case at a running app so you can eyeball the responses.
# Doesn't assert anything - the real tests are in src/test.
# Usage: start the app, then ./requests.sh

BASE="localhost:8080/api"

call() {
  echo
  echo "-- $1"
  shift
  curl -s -w "\nHTTP %{http_code}\n" "$@"
}

echo "=== BEFORE ==="
curl -s $BASE/accounts; echo

call "valid transfer, 100 -> 200" \
  -X POST $BASE/transfer -H "Content-Type: application/json" \
  -d '{"from":"acc1","to":"acc2","amount":100}'

call "reverse direction, 50 -> 200" \
  -X POST $BASE/transfer -H "Content-Type: application/json" \
  -d '{"from":"acc2","to":"acc1","amount":50}'

call "unknown account -> 404" \
  -X POST $BASE/transfer -H "Content-Type: application/json" \
  -d '{"from":"acc9","to":"acc2","amount":10}'

call "insufficient funds -> 422" \
  -X POST $BASE/transfer -H "Content-Type: application/json" \
  -d '{"from":"acc1","to":"acc2","amount":9999999}'

call "same account -> 400" \
  -X POST $BASE/transfer -H "Content-Type: application/json" \
  -d '{"from":"acc1","to":"acc1","amount":10}'

call "negative amount -> 400" \
  -X POST $BASE/transfer -H "Content-Type: application/json" \
  -d '{"from":"acc1","to":"acc2","amount":-100}'

call "too many decimal places -> 400" \
  -X POST $BASE/transfer -H "Content-Type: application/json" \
  -d '{"from":"acc1","to":"acc2","amount":10.999}'

echo
echo "=== AFTER ==="
curl -s $BASE/accounts; echo
echo
echo "Net movement should be acc1 -50, acc2 +50. Total unchanged."