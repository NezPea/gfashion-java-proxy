#!/bin/bash

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
gf_id=$(PostCategory $root_id "Gfashion")
#echo $gf_id

#Add GFashion First Level Category
men_id=$(PostCategory $gf_id "Men")
women_id=$(PostCategory $gf_id "Women")
others_id=$(PostCategory $gf_id "Others")

# Add Second Level Categories
men_cloth_id=$(PostCategory $men_id "Cloth")
men_accessories_id=$(PostCategory $men_id "Accessories")
women_cloth_id=$(PostCategory $women_id "Cloth")
women_accessories_id=$(PostCategory $women_id "Accessories")
others_cloth_id=$(PostCategory $others_id "Cloth")
others_accessories_id=$(PostCategory $others_id "Accessories")





