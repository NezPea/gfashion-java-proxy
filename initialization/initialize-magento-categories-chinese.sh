#!/bin/bash

server=$1
username=$2
password=$3
root_id=$4

adminToken=$(curl --location --request POST ${server}/index.php/rest/V1/integration/admin/token \
--header 'Content-Type: application/json' \
--header 'Cookie: PHPSESSID=f52b8b5466245cc1e0e333f8ae88617f' \
--data-raw '{
	"username": "'$username'",
	"password": "'$password'"
}' | jq -r .)


PostCategory () {
  echo $(curl --location --request POST ${server}/index.php/rest/V1/categories \
  --header 'Authorization: Bearer '$adminToken'' \
  --header 'Content-Type: application/json;charset=utf-8' \
  --header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
  --data-raw '{
    "category": {
      "parent_id": "'$1'",
      "name": "'$2'",
      "is_active": true,
      "include_in_menu": true
    }
  }' | jq -r .id)
}


#Add GFashion Root Level Category
gf_id=$(PostCategory $root_id "G潮流")
#echo $gf_id

#Add GFashion First Level Category
men_id=$(PostCategory $gf_id "M男士")
women_id=$(PostCategory $gf_id "W女士")
others_id=$(PostCategory $gf_id "O其他")

# Add Second Level Categories
men_cloth_id=$(PostCategory $men_id "C服装")
men_accessories_id=$(PostCategory $men_id "A配饰")
women_cloth_id=$(PostCategory $women_id "C服装")
women_accessories_id=$(PostCategory $women_id "A配饰")
others_cloth_id=$(PostCategory $others_id "C服装")
others_accessories_id=$(PostCategory $others_id "A配饰")