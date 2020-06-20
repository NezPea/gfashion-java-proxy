#!/bin/bash

server=$1
username=$2
password=$3

adminToken=$(curl --location --request POST ${server}/index.php/rest/V1/integration/admin/token \
--header 'Content-Type: application/json' \
--header 'Cookie: PHPSESSID=f52b8b5466245cc1e0e333f8ae88617f' \
--data-raw '{
	"username": "'$username'",
	"password": "'$password'"
}' | jq -r .)



curl --location --request POST ${server}/index.php/rest/V1/customers \
--header 'Authorization: Bearer '$adminToken'' \
--header 'Content-Type: application/json' \
--header 'Cookie: PHPSESSID=f52b8b5466245cc1e0e333f8ae88617f' \
--data-raw '{
  "customer": {
    "email": "customer@test.com",
    "firstname": "Tester",
    "lastname": "Tester"
  },
  "password": "P@ssw0rd"
}'
