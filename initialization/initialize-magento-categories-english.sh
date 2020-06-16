#!/bin/bash

server="https://magento-dev6.devenv.vogfw.xyz"
root_id="1"
adminToken="1ukdombne8ptu4afm7m7spgygxpere1q"

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
gf_id=$(PostCategory $root_id "Gfashion1")
#echo $gf_id

#Add GFashion First Level Category
men_id=$(PostCategory $gf_id "Men1")
women_id=$(PostCategory $gf_id "Women1")
others_id=$(PostCategory $gf_id "Others1")

# Add Second Level Categories
men_cloth_id=$(PostCategory $men_id "Cloth1")
men_accessories_id=$(PostCategory $men_id "Accessories1")
women_cloth_id=$(PostCategory $women_id "Cloth1")
women_accessories_id=$(PostCategory $women_id "Accessories1")
others_cloth_id=$(PostCategory $others_id "Cloth1")
others_accessories_id=$(PostCategory $others_id "Accessories1")





