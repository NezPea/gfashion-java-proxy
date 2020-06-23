#!/bin/bash

server=$1
username=$2
password=$3
root_id=$4
storeViewCN=$5

adminToken=$(curl --location --request POST ${server}/index.php/rest/V1/integration/admin/token \
--header 'Content-Type: application/json' \
--header 'Cookie: PHPSESSID=f52b8b5466245cc1e0e333f8ae88617f' \
--data-raw '{
	"username": "'"$username"'",
	"password": "'"$password"'"
}' | jq -r .)

# post to default store view
PostCategory () {
  echo $(curl --location --request POST ${server}/index.php/rest/V1/categories \
  --header 'Authorization: Bearer '$adminToken'' \
  --header 'Content-Type: application/json;charset=utf-8' \
  --header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
  --data-raw '{
    "category": {
      "parent_id": "'"$1"'",
      "name": "'"$2"'",
      "is_active": true,
      "include_in_menu": true,
      "custom_attributes": [
        {
            "attribute_code": "description",
            "value": "'"${3:-Please add Description}"'"
        }
      ]
    }
  }' | jq -r .id)
}

# post to other store view like cn, en
PostCategoryWithId () {
  echo $(curl --location --request POST ${server}/index.php/rest/${1}/V1/categories \
  --header 'Authorization: Bearer '$adminToken'' \
  --header 'Content-Type: application/json;charset=utf-8' \
  --header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
  --data-raw '{
    "category": {
      "id": "'"$2"'",
      "name": "'"$3"'",
      "is_active": true,
      "include_in_menu": true,
      "custom_attributes": [
        {
            "attribute_code": "description",
            "value": "'"${4:-Please add Description}"'"
        }
      ]
    }
  }' | jq -r .id)
}

#Add GFashion Root Level Category
gf_id_en=$(PostCategory $root_id "Test")
gf_id_cn=$(PostCategoryWithId $storeViewCN $gf_id_en "测试")
echo $gf_id_cn
#echo $gf_id
