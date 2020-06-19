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
gf_id=$(PostCategory $root_id "Gfashion")
#echo $gf_id

#Add GFashion First Level Category
men_id=$(PostCategory $gf_id "Men")
women_id=$(PostCategory $gf_id "Women")
others_id=$(PostCategory $gf_id "Others")

# Add Second Level Categories
men_cloth_id=$(PostCategory $men_id "Clothing")
men_bags_id=$(PostCategory $men_id "Bags")
men_accessories_id=$(PostCategory $men_id "Accessories")
men_shoes_id=$(PostCategory $men_id "Shoes")

women_cloth_id=$(PostCategory $women_id "Clothing")
women_bags_id=$(PostCategory $women_id "Bags")
women_accessories_id=$(PostCategory $women_id "Accessories")
women_shoes_id=$(PostCategory $women_id "Shoes")

others_cloth_id=$(PostCategory $others_id "Cloth")
others_accessories_id=$(PostCategory $others_id "Accessories")


# Add third & fourth Level Categories for Men

$(PostCategory $men_bags_id "Tote Bags")
$(PostCategory $men_bags_id "backpacks")
$(PostCategory $men_bags_id "pouches & document holders")
$(PostCategory $men_bags_id "briefcases")
$(PostCategory $men_bags_id "duffle bags")
$(PostCategory $men_bags_id "travel bags")
$(PostCategory $men_bags_id "messenger bags")

men_tops_id=$(PostCategory $men_cloth_id "Tops")
$(PostCategory $men_tops_id "Polos")
$(PostCategory $men_tops_id "T-shirt")
$(PostCategory $men_tops_id "Henleys")
$(PostCategory $men_tops_id "Tank Tops")

men_underwear_loungewear_id=$(PostCategory $men_cloth_id "underwear & loungewear")
$(PostCategory $men_underwear_loungewear_id "briefs")
$(PostCategory $men_underwear_loungewear_id "boxers")
$(PostCategory $men_underwear_loungewear_id "robes")
$(PostCategory $men_underwear_loungewear_id "Pyjamas & loungewear")

men_sweaters_id=$(PostCategory $men_cloth_id "sweaters")
$(PostCategory $men_sweaters_id "V-necks")
$(PostCategory $men_sweaters_id "crewnecks")
$(PostCategory $men_sweaters_id "sweatshirts")
$(PostCategory $men_sweaters_id "cardigans")
$(PostCategory $men_sweaters_id "hoodies & zipups")
$(PostCategory $men_sweaters_id "turtlenecks")

men_jacket_coats_id=$(PostCategory $men_cloth_id "jacket & coats")
$(PostCategory $men_jacket_coats_id "peacoats")
$(PostCategory $men_jacket_coats_id "coats")
$(PostCategory $men_jacket_coats_id "jackets")
$(PostCategory $men_jacket_coats_id "denim jackets")
$(PostCategory $men_jacket_coats_id "leather jackets")
$(PostCategory $men_jacket_coats_id "fur & shearling")
$(PostCategory $men_jacket_coats_id "down")
$(PostCategory $men_jacket_coats_id "trench coats")
$(PostCategory $men_jacket_coats_id "bombers")
$(PostCategory $men_jacket_coats_id "vests")

men_swimwear_id=$(PostCategory $men_cloth_id "swimwear")
$(PostCategory $men_swimwear_id "swimsuits")

men_jeans_id=$(PostCategory $men_cloth_id "jeans")
men_shorts_id=$(PostCategory $men_cloth_id "shorts")
men_shirts_id=$(PostCategory $men_cloth_id "shirts")
men_pants_id=$(PostCategory $men_cloth_id "pants")
$(PostCategory $men_pants_id "cargo pants")
$(PostCategory $men_pants_id "leather pants")
$(PostCategory $men_pants_id "sweatpants")
$(PostCategory $men_pants_id "trousers")

men_suits_blazers_id=$(PostCategory $men_cloth_id "suits & blazers")
$(PostCategory $men_suits_blazers_id "blazers")
$(PostCategory $men_suits_blazers_id "suits")
$(PostCategory $men_suits_blazers_id "tuxedos")
$(PostCategory $men_suits_blazers_id "waistcoats")

men_pocket_squares_tie_bars_id=$(PostCategory $men_accessories_id "pocket squares & tie bars")
men_scarves_id=$(PostCategory $men_accessories_id "scarves")
men_dog accessories_id=$(PostCategory $men_accessories_id "dog accessories")
men_hats_id=$(PostCategory $men_accessories_id "hats")
$(PostCategory $men_hats_id "structured hats")
$(PostCategory $men_hats_id "beanies")
$(PostCategory $men_hats_id "caps & Flat Caps")

men_gloves_id=$(PostCategory $men_accessories_id "gloves")
men_watches_id=$(PostCategory $men_accessories_id "watches")
men_towels_id=$(PostCategory $men_accessories_id "towels")
men_jewelry_id=$(PostCategory $men_accessories_id "jewelry")
$(PostCategory $men_jewelry_id "rings")
$(PostCategory $men_jewelry_id "bracelets")
$(PostCategory $men_jewelry_id "earrings")
$(PostCategory $men_jewelry_id "pins")
$(PostCategory $men_jewelry_id "cufflinks")
$(PostCategory $men_jewelry_id "necklaces")

men_TECH_id=$(PostCategory $men_accessories_id "TECH")
$(PostCategory $men_TECH_id "iphone cases")
$(PostCategory $men_TECH_id "headphones")
$(PostCategory $men_TECH_id "laptop cases")

men_eyewear_id=$(PostCategory $men_accessories_id "eyewear")
$(PostCategory $men_eyewear_id "sunglasses")
$(PostCategory $men_eyewear_id "glasses")

men_souvenirs_id=$(PostCategory $men_accessories_id "souvenirs")
$(PostCategory $men_souvenirs_id "posters")
$(PostCategory $men_souvenirs_id "notes & sketches")

men_belts_suspenders_id=$(PostCategory $men_accessories_id "belts & suspenders")
men_socks_id=$(PostCategory $men_accessories_id "socks")
men_blankets_id=$(PostCategory $men_accessories_id "blankets")
men_keychains_id=$(PostCategory $men_accessories_id "keychains")
men_wallets_card_holders_id=$(PostCategory $men_accessories_id "wallets & card holders")
$(PostCategory $men_wallets_card_holders_id "card holders")
$(PostCategory $men_wallets_card_holders_id "passport holders")
$(PostCategory $men_wallets_card_holders_id "wallets")
$(PostCategory $men_wallets_card_holders_id "money clips")

men_umbrellas_id=$(PostCategory $men_accessories_id "umbrellas")
men_ties_id=$(PostCategory $men_accessories_id "ties")
$(PostCategory $men_ties_id "bow ties")
$(PostCategory $men_ties_id "neck ties")

men_loafers_id=$(PostCategory $men_shoes_id "loafers")
men_sandals_id=$(PostCategory $men_shoes_id "sandals")
$(PostCategory $men_sandals_id "flip flops")
$(PostCategory $men_sandals_id "sandals")

men_monkstraps_id=$(PostCategory $men_shoes_id "monkstraps")
men_boat_shoes_Moccasins_id=$(PostCategory $men_shoes_id "boat shoes & Moccasins")
men_lace_ups_id=$(PostCategory $men_shoes_id "lace ups")
men_Espardrilles_id=$(PostCategory $men_shoes_id "Espardrilles")
men_sneakers_id=$(PostCategory $men_shoes_id "sneakers")
$(PostCategory $men_sneakers_id "low top sneakers")
$(PostCategory $men_sneakers_id "high top sneakers")

men_boots_id=$(PostCategory $men_shoes_id "boots")
$(PostCategory $men_boots_id "wingtip boots")
$(PostCategory $men_boots_id "chelsea boots")
$(PostCategory $men_boots_id "zip up & buckled boots")
$(PostCategory $men_boots_id "biker & combat boots")
$(PostCategory $men_boots_id "desert boots")
$(PostCategory $men_boots_id "lace-up boots")



# Add third & fourth Level Categories for Women


$(PostCategory $women_bags_id "Tote Bags")
$(PostCategory $women_bags_id "backpacks")
$(PostCategory $women_bags_id "sholder bags")
women_clutched_pouches_id=$(PostCategory $women_bags_id "clutched & pouches")
$(PostCategory $women_clutched_pouches_id "clutches")
$(PostCategory $women_clutched_pouches_id "pouches")
$(PostCategory $women_bags_id "Duffle & top handle bags")
$(PostCategory $women_bags_id "messenger bags & satchels")
$(PostCategory $women_bags_id "travel bags")

women_tops_id=$(PostCategory $women_cloth_id "Tops")
$(PostCategory $women_tops_id "Polos")
$(PostCategory $women_tops_id "T-shirt")
$(PostCategory $women_tops_id "Blouses")
$(PostCategory $women_tops_id "Tank Tops & Camisoles")
$(PostCategory $women_tops_id "Shirts")
$(PostCategory $women_tops_id "Bodysuits")

women_lingerie_id=$(PostCategory $women_cloth_id "Lingerie")
$(PostCategory $women_lingerie_id "thongs")
$(PostCategory $women_lingerie_id "briefs")
$(PostCategory $women_lingerie_id "boy shorts")
$(PostCategory $women_lingerie_id "bra")
$(PostCategory $women_lingerie_id "robes")
$(PostCategory $women_lingerie_id "sleepwear")
$(PostCategory $women_lingerie_id "Corsets & Bodysuits")
$(PostCategory $women_lingerie_id "tanks")

women_skirts_id=$(PostCategory $women_cloth_id "Skirts")
$(PostCategory $women_skirts_id "mid length skirts")
$(PostCategory $women_skirts_id "short skirts")
$(PostCategory $women_skirts_id "long skirts")

women_sweaters_id=$(PostCategory $women_cloth_id "sweaters")
$(PostCategory $women_sweaters_id "V-necks")
$(PostCategory $women_sweaters_id "crewnecks")
$(PostCategory $women_sweaters_id "sweatshirts")
$(PostCategory $women_sweaters_id "cardigans")
$(PostCategory $women_sweaters_id "hoodies & zipups")
$(PostCategory $women_sweaters_id "turtlenecks")

women_jacket_coats_id=$(PostCategory $women_cloth_id "jacket & coats")
$(PostCategory $women_jacket_coats_id "coats")
$(PostCategory $women_jacket_coats_id "jackets")
$(PostCategory $women_jacket_coats_id "denim jackets")
$(PostCategory $women_jacket_coats_id "leather jackets")
$(PostCategory $women_jacket_coats_id "fur & shearling")
$(PostCategory $women_jacket_coats_id "down")
$(PostCategory $women_jacket_coats_id "blazers")
$(PostCategory $women_jacket_coats_id "trench coats")
$(PostCategory $women_jacket_coats_id "bombers")
$(PostCategory $women_jacket_coats_id "vests")

women_swimwear_id=$(PostCategory $women_cloth_id "swimwear")
$(PostCategory $women_swimwear_id "bikinis")
$(PostCategory $women_swimwear_id "One-Piece")

women_jeans_id=$(PostCategory $women_cloth_id "jeans")
women_shorts_id=$(PostCategory $women_cloth_id "shorts")
women_pants_id=$(PostCategory $women_cloth_id "pants")
$(PostCategory $women_pants_id "leggings")
$(PostCategory $women_pants_id "leather pants")
$(PostCategory $women_pants_id "sweatpants")
$(PostCategory $women_pants_id "trousers")

women_activewear_id=$(PostCategory $women_cloth_id "Activewear")
$(PostCategory $women_activewear_id "Tops")
$(PostCategory $women_activewear_id "hoodies & zipups")
$(PostCategory $women_activewear_id "outerwear")
$(PostCategory $women_activewear_id "sport shorts & skirts")
$(PostCategory $women_activewear_id "leggings")
$(PostCategory $women_activewear_id "sports Bras")
$(PostCategory $women_activewear_id "pants")
$(PostCategory $women_activewear_id "Dresses")

women_dresses_id=$(PostCategory $women_cloth_id "Dresses")
$(PostCategory $women_dresses_id "mid length dresses")
$(PostCategory $women_dresses_id "short dresses")
$(PostCategory $women_dresses_id "long dressses")

$(PostCategory $women_cloth_id "Jumpsuits")

women_pocket_squares_tie_bars_id=$(PostCategory $women_accessories_id "pocket squares & tie bars")
women_scarves_id=$(PostCategory $women_accessories_id "scarves")
women_dog accessories_id=$(PostCategory $women_accessories_id "dog accessories")
women_hats_id=$(PostCategory $women_accessories_id "hats")
$(PostCategory $women_hats_id "Headbands & Hair Accessories")
$(PostCategory $women_hats_id "beanies")
$(PostCategory $women_hats_id "Caps")
$(PostCategory $women_hats_id "Fedoras & Panama Hats")
$(PostCategory $women_hats_id "Beach Hats")

women_gloves_id=$(PostCategory $women_accessories_id "gloves")
women_watches_id=$(PostCategory $women_accessories_id "watches")
women_towels_id=$(PostCategory $women_accessories_id "towels")
women_jewelry_id=$(PostCategory $women_accessories_id "jewelry")
$(PostCategory $women_jewelry_id "rings")
$(PostCategory $women_jewelry_id "bracelets")
$(PostCategory $women_jewelry_id "earrings")
$(PostCategory $women_jewelry_id "Brooches")
$(PostCategory $women_jewelry_id "cufflinks")
$(PostCategory $women_jewelry_id "necklaces")

women_TECH_id=$(PostCategory $women_accessories_id "TECH")
$(PostCategory $women_TECH_id "iphone cases")

women_eyewear_id=$(PostCategory $women_accessories_id "eyewear")
$(PostCategory $women_eyewear_id "sunglasses")
$(PostCategory $women_eyewear_id "glasses")

women_belts_suspenders_id=$(PostCategory $women_accessories_id "belts & suspenders")
women_socks_id=$(PostCategory $women_accessories_id "socks")
women_blankets_id=$(PostCategory $women_accessories_id "blankets")
women_keychains_id=$(PostCategory $women_accessories_id "keychains")
women_wallets_card_holders_id=$(PostCategory $women_accessories_id "wallets & card holders")
$(PostCategory $women_wallets_card_holders_id "card holders")
$(PostCategory $women_wallets_card_holders_id "passport holders")
$(PostCategory $women_wallets_card_holders_id "wallets")
$(PostCategory $women_wallets_card_holders_id "coin Pouches")

women_umbrellas_id=$(PostCategory $women_accessories_id "umbrellas")
women_fine_jewelry_id=$(PostCategory $women_accessories_id "Fine Jewelry")
$(PostCategory $women_fine_jewelry_id "rings")
$(PostCategory $women_fine_jewelry_id "earrings")

women_sandals_id=$(PostCategory $women_shoes_id "sandals")
$(PostCategory $women_sandals_id "flat sandals")
$(PostCategory $women_sandals_id "Heeled sandals")

women_flats_id=$(PostCategory $women_shoes_id "flats")
$(PostCategory $women_flats_id "slippers & loafers")
$(PostCategory $women_flats_id "lace up & oxfords")
$(PostCategory $women_flats_id "ballerina flats")
$(PostCategory $women_flats_id "espadrilles")

women_sneakers_id=$(PostCategory $women_shoes_id "sneakers")
$(PostCategory $women_sneakers_id "low top sneakers")
$(PostCategory $women_sneakers_id "high top sneakers")

women_boots_id=$(PostCategory $women_shoes_id "boots")
$(PostCategory $women_boots_id "mid-calf boots")
$(PostCategory $women_boots_id "ankle boots")
$(PostCategory $women_boots_id "tall boots")

$(PostCategory $women_shoes_id "heels")
