#!/bin/bash

server="https://magento-dev6.devenv.vogfw.xyz"
root_id="1"

#Add GFashion Root Level Category
gf_id=$(curl --location --request POST ${server}/index.php/rest/V1/categories \
--header 'Authorization: Bearer ucuctlqts4t56sz5ro570zfpqz7lfqyv' \
--header 'Content-Type: application/json;charset=utf-8' \
--header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
--data-raw '{
  "category": {
    "parent_id": '$root_id',
    "name": "GFashion",
    "is_active": true,
    "include_in_menu": true
  }
}' | jq -r .id)

#Add GFashion First Level Category

men_id=$(curl --location --request POST ${server}/index.php/rest/V1/categories \
--header 'Authorization: Bearer ucuctlqts4t56sz5ro570zfpqz7lfqyv' \
--header 'Content-Type: application/json;charset=utf-8' \
--header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
--data-raw '{
  "category": {
    "parent_id": '$gf_id',
    "name": "Men",
    "is_active": true,
    "include_in_menu": true
  }
}' | jq -r .id)

women_id=$(curl --location --request POST ${server}/index.php/rest/V1/categories \
--header 'Authorization: Bearer ucuctlqts4t56sz5ro570zfpqz7lfqyv' \
--header 'Content-Type: application/json;charset=utf-8' \
--header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
--data-raw '{
  "category": {
    "parent_id": '$gf_id',
    "name": "Women",
    "is_active": true,
    "include_in_menu": true
  }
}' | jq -r .id)


others_id=$(curl --location --request POST ${server}/index.php/rest/V1/categories \
--header 'Authorization: Bearer ucuctlqts4t56sz5ro570zfpqz7lfqyv' \
--header 'Content-Type: application/json;charset=utf-8' \
--header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
--data-raw '{
  "category": {
    "parent_id": '$gf_id',
    "name": "Others",
    "is_active": true,
    "include_in_menu": true
  }
}' | jq -r .id)



# Add Second Level Categories
men_cloth_id=$(curl --location --request POST ${server}/index.php/rest/V1/categories \
--header 'Authorization: Bearer ucuctlqts4t56sz5ro570zfpqz7lfqyv' \
--header 'Content-Type: application/json;charset=utf-8' \
--header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
--data-raw '{
  "category": {
    "parent_id": '$men_id',
    "name": "Cloth",
    "is_active": true,
    "include_in_menu": true
  }
}' | jq -r .id)

men_accessories_id=$(curl --location --request POST ${server}/index.php/rest/V1/categories \
--header 'Authorization: Bearer ucuctlqts4t56sz5ro570zfpqz7lfqyv' \
--header 'Content-Type: application/json;charset=utf-8' \
--header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
--data-raw '{
  "category": {
    "parent_id": '$men_id',
    "name": "Accessories",
    "is_active": true,
    "include_in_menu": true
  }
}' | jq -r .id)

women_cloth_id=$(curl --location --request POST ${server}/index.php/rest/V1/categories \
--header 'Authorization: Bearer ucuctlqts4t56sz5ro570zfpqz7lfqyv' \
--header 'Content-Type: application/json;charset=utf-8' \
--header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
--data-raw '{
  "category": {
    "parent_id": '$women_id',
    "name": "Cloth",
    "is_active": true,
    "include_in_menu": true
  }
}' | jq -r .id)

women_accessories_id=$(curl --location --request POST ${server}/index.php/rest/V1/categories \
--header 'Authorization: Bearer ucuctlqts4t56sz5ro570zfpqz7lfqyv' \
--header 'Content-Type: application/json;charset=utf-8' \
--header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
--data-raw '{
  "category": {
    "parent_id": '$women_id',
    "name": "Accessories",
    "is_active": true,
    "include_in_menu": true
  }
}' | jq -r .id)

others_cloth_id=$(curl --location --request POST ${server}/index.php/rest/V1/categories \
--header 'Authorization: Bearer ucuctlqts4t56sz5ro570zfpqz7lfqyv' \
--header 'Content-Type: application/json;charset=utf-8' \
--header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
--data-raw '{
  "category": {
    "parent_id": '$others_id',
    "name": "Cloth",
    "is_active": true,
    "include_in_menu": true
  }
}' | jq -r .id)

others_accessories_id=$(curl --location --request POST ${server}/index.php/rest/V1/categories \
--header 'Authorization: Bearer ucuctlqts4t56sz5ro570zfpqz7lfqyv' \
--header 'Content-Type: application/json;charset=utf-8' \
--header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
--data-raw '{
  "category": {
    "parent_id": '$others_id',
    "name": "Accessories",
    "is_active": true,
    "include_in_menu": true
  }
}' | jq -r .id)





