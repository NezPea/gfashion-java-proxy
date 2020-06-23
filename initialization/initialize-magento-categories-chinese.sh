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
            "value": "'"$3"'"
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
            "value": "'"$4"'"
        }
      ]
    }
  }' | jq -r .id)
}


#Add GFashion Root Level Category
gf_id=$(PostCategory $root_id "潮流")
#echo $gf_id

#Add GFashion First Level Category
men_id=$(PostCategory $gf_id "男士")
women_id=$(PostCategory $gf_id "女士")
others_id=$(PostCategory $gf_id "其他")

# Add Second Level Categories
men_cloth_id=$(PostCategory $men_id "服装")
men_accessories_id=$(PostCategory $men_id "配饰")
women_cloth_id=$(PostCategory $women_id "服装")
women_accessories_id=$(PostCategory $women_id "配饰")
others_cloth_id=$(PostCategory $others_id "服装")
others_accessories_id=$(PostCategory $others_id "配饰")