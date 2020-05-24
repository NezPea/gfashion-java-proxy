package com.gfashion;

import com.gfashion.domain.product.GfChannelProduct;
import com.gfashion.domain.product.GfMediaGalleryEntry;
import com.gfashion.domain.product.GfProduct;
import com.gfashion.domain.product.GfProductSearchResponse;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class testFilter {

    public static void main(String[] args) {

        String response = "{\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"id\": 1233,\n" +
                "            \"sku\": \"WJ02-M-Black\",\n" +
                "            \"name\": \"Josie Yoga Jacket-M-Black\",\n" +
                "            \"attribute_set_id\": 9,\n" +
                "            \"price\": 56.25,\n" +
                "            \"status\": 1,\n" +
                "            \"visibility\": 1,\n" +
                "            \"type_id\": \"simple\",\n" +
                "            \"created_at\": \"2020-05-09 12:02:18\",\n" +
                "            \"updated_at\": \"2020-05-09 12:02:18\",\n" +
                "            \"weight\": 1,\n" +
                "            \"extension_attributes\": {\n" +
                "                \"website_ids\": [\n" +
                "                    1\n" +
                "                ],\n" +
                "                \"category_links\": [\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"23\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"30\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"2\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"product_links\": [],\n" +
                "            \"options\": [],\n" +
                "            \"media_gallery_entries\": [\n" +
                "                {\n" +
                "                    \"id\": 2083,\n" +
                "                    \"media_type\": \"image\",\n" +
                "                    \"label\": \"\",\n" +
                "                    \"position\": 1,\n" +
                "                    \"disabled\": false,\n" +
                "                    \"types\": [\n" +
                "                        \"image\",\n" +
                "                        \"small_image\",\n" +
                "                        \"thumbnail\"\n" +
                "                    ],\n" +
                "                    \"file\": \"/w/j/wj02-black_main_1.jpg\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"tier_prices\": [],\n" +
                "            \"custom_attributes\": [\n" +
                "                {\n" +
                "                    \"attribute_code\": \"image\",\n" +
                "                    \"value\": \"/w/j/wj02-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"small_image\",\n" +
                "                    \"value\": \"/w/j/wj02-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"thumbnail\",\n" +
                "                    \"value\": \"/w/j/wj02-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"options_container\",\n" +
                "                    \"value\": \"container2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"msrp_display_actual_price_type\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"url_key\",\n" +
                "                    \"value\": \"josie-yoga-jacket-m-black\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"required_options\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"has_options\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"tax_class_id\",\n" +
                "                    \"value\": \"2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"category_ids\",\n" +
                "                    \"value\": [\n" +
                "                        \"23\",\n" +
                "                        \"30\",\n" +
                "                        \"2\"\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"description\",\n" +
                "                    \"value\": \"<p>When your near future includes yoga, the cozy comfort of the Josie Yoga Jacket gets your mind and body ready. Stretchy CoolTech&trade; fabric with zipper pockets makes this jacket the right gear for studio time or teatime after.</p>\\n<p>&bull; Slate rouched neck pullover.<br />&bull; Moisture-wicking fabric.<br />&bull; Hidden zipper.<br />&bull; Mesh armpit venting.<br />&bull; Dropped rear hem.</p>\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"size\",\n" +
                "                    \"value\": \"5595\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"color\",\n" +
                "                    \"value\": \"5476\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 1345,\n" +
                "            \"sku\": \"WJ10-M-Black\",\n" +
                "            \"name\": \"Nadia Elements Shell-M-Black\",\n" +
                "            \"attribute_set_id\": 9,\n" +
                "            \"price\": 69,\n" +
                "            \"status\": 1,\n" +
                "            \"visibility\": 1,\n" +
                "            \"type_id\": \"simple\",\n" +
                "            \"created_at\": \"2020-05-09 12:02:20\",\n" +
                "            \"updated_at\": \"2020-05-09 12:02:20\",\n" +
                "            \"weight\": 1,\n" +
                "            \"extension_attributes\": {\n" +
                "                \"website_ids\": [\n" +
                "                    1\n" +
                "                ],\n" +
                "                \"category_links\": [\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"23\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"8\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"35\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"2\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"product_links\": [],\n" +
                "            \"options\": [],\n" +
                "            \"media_gallery_entries\": [\n" +
                "                {\n" +
                "                    \"id\": 2291,\n" +
                "                    \"media_type\": \"image\",\n" +
                "                    \"label\": \"\",\n" +
                "                    \"position\": 1,\n" +
                "                    \"disabled\": false,\n" +
                "                    \"types\": [\n" +
                "                        \"image\",\n" +
                "                        \"small_image\",\n" +
                "                        \"thumbnail\"\n" +
                "                    ],\n" +
                "                    \"file\": \"/w/j/wj10-black_main_1.jpg\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"tier_prices\": [],\n" +
                "            \"custom_attributes\": [\n" +
                "                {\n" +
                "                    \"attribute_code\": \"image\",\n" +
                "                    \"value\": \"/w/j/wj10-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"small_image\",\n" +
                "                    \"value\": \"/w/j/wj10-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"thumbnail\",\n" +
                "                    \"value\": \"/w/j/wj10-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"options_container\",\n" +
                "                    \"value\": \"container2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"msrp_display_actual_price_type\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"url_key\",\n" +
                "                    \"value\": \"nadia-elements-shell-m-black\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"required_options\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"has_options\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"tax_class_id\",\n" +
                "                    \"value\": \"2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"category_ids\",\n" +
                "                    \"value\": [\n" +
                "                        \"23\",\n" +
                "                        \"8\",\n" +
                "                        \"35\",\n" +
                "                        \"2\"\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"description\",\n" +
                "                    \"value\": \"<p>Protect yourself from wind and rain in the stylish Nadia Elements Shell. It repels water using hydro-resistant fabric, with fleece lining that adds a touch of warmth. It's finished with bold contrast zippers, adjustable cuffs and a hood.</p>\\n<p>&bull; Zippered front. <br />&bull; Zippered side pockets. <br />&bull; Drawstring-adjustable waist and hood. <br />&bull; Machine wash/line dry.<br />&bull; Light blue 1/4 zip pullover.</p>\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"size\",\n" +
                "                    \"value\": \"5595\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"color\",\n" +
                "                    \"value\": \"5476\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 1361,\n" +
                "            \"sku\": \"WJ11-M-Black\",\n" +
                "            \"name\": \"Neve Studio Dance Jacket-M-Black\",\n" +
                "            \"attribute_set_id\": 9,\n" +
                "            \"price\": 69,\n" +
                "            \"status\": 1,\n" +
                "            \"visibility\": 1,\n" +
                "            \"type_id\": \"simple\",\n" +
                "            \"created_at\": \"2020-05-09 12:02:21\",\n" +
                "            \"updated_at\": \"2020-05-09 12:02:21\",\n" +
                "            \"weight\": 1,\n" +
                "            \"extension_attributes\": {\n" +
                "                \"website_ids\": [\n" +
                "                    1\n" +
                "                ],\n" +
                "                \"category_links\": [\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"23\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"8\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"2\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"product_links\": [],\n" +
                "            \"options\": [],\n" +
                "            \"media_gallery_entries\": [\n" +
                "                {\n" +
                "                    \"id\": 2319,\n" +
                "                    \"media_type\": \"image\",\n" +
                "                    \"label\": \"\",\n" +
                "                    \"position\": 1,\n" +
                "                    \"disabled\": false,\n" +
                "                    \"types\": [\n" +
                "                        \"image\",\n" +
                "                        \"small_image\",\n" +
                "                        \"thumbnail\"\n" +
                "                    ],\n" +
                "                    \"file\": \"/w/j/wj11-black_main_1.jpg\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"tier_prices\": [],\n" +
                "            \"custom_attributes\": [\n" +
                "                {\n" +
                "                    \"attribute_code\": \"image\",\n" +
                "                    \"value\": \"/w/j/wj11-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"small_image\",\n" +
                "                    \"value\": \"/w/j/wj11-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"thumbnail\",\n" +
                "                    \"value\": \"/w/j/wj11-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"options_container\",\n" +
                "                    \"value\": \"container2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"msrp_display_actual_price_type\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"url_key\",\n" +
                "                    \"value\": \"neve-studio-dance-jacket-m-black\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"required_options\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"has_options\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"tax_class_id\",\n" +
                "                    \"value\": \"2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"category_ids\",\n" +
                "                    \"value\": [\n" +
                "                        \"23\",\n" +
                "                        \"8\",\n" +
                "                        \"2\"\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"description\",\n" +
                "                    \"value\": \"<p>If you're constantly on the move, the Neve Studio Dance Jacket is for you. It's not just for dance, either, with a tight fit that works as a mid-layer. The reversible design makes it even more versatile.</p>\\n<p>&bull; Bright blue 1/4 zip pullover.<br />&bull; CoolTech&trade; liner is sweat-wicking.<br />&bull; Sleeve thumbholes.<br />&bull; Zipper garage to protect your chin.<br />&bull; Stretchy collar drawcords.</p>\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"size\",\n" +
                "                    \"value\": \"5595\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"color\",\n" +
                "                    \"value\": \"5476\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 1393,\n" +
                "            \"sku\": \"WJ12-M-Black\",\n" +
                "            \"name\": \"Olivia 1/4 Zip Light Jacket-M-Black\",\n" +
                "            \"attribute_set_id\": 9,\n" +
                "            \"price\": 77,\n" +
                "            \"status\": 1,\n" +
                "            \"visibility\": 1,\n" +
                "            \"type_id\": \"simple\",\n" +
                "            \"created_at\": \"2020-05-09 12:02:21\",\n" +
                "            \"updated_at\": \"2020-05-09 12:02:21\",\n" +
                "            \"weight\": 1,\n" +
                "            \"extension_attributes\": {\n" +
                "                \"website_ids\": [\n" +
                "                    1\n" +
                "                ],\n" +
                "                \"category_links\": [\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"23\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"30\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"35\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"position\": 0,\n" +
                "                        \"category_id\": \"2\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            \"product_links\": [],\n" +
                "            \"options\": [],\n" +
                "            \"media_gallery_entries\": [\n" +
                "                {\n" +
                "                    \"id\": 2375,\n" +
                "                    \"media_type\": \"image\",\n" +
                "                    \"label\": \"\",\n" +
                "                    \"position\": 1,\n" +
                "                    \"disabled\": false,\n" +
                "                    \"types\": [\n" +
                "                        \"image\",\n" +
                "                        \"small_image\",\n" +
                "                        \"thumbnail\"\n" +
                "                    ],\n" +
                "                    \"file\": \"/w/j/wj12-black_main_1.jpg\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"tier_prices\": [],\n" +
                "            \"custom_attributes\": [\n" +
                "                {\n" +
                "                    \"attribute_code\": \"image\",\n" +
                "                    \"value\": \"/w/j/wj12-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"small_image\",\n" +
                "                    \"value\": \"/w/j/wj12-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"thumbnail\",\n" +
                "                    \"value\": \"/w/j/wj12-black_main_1.jpg\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"options_container\",\n" +
                "                    \"value\": \"container2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"msrp_display_actual_price_type\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"url_key\",\n" +
                "                    \"value\": \"olivia-1-4-zip-light-jacket-m-black\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"required_options\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"has_options\",\n" +
                "                    \"value\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"tax_class_id\",\n" +
                "                    \"value\": \"2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"category_ids\",\n" +
                "                    \"value\": [\n" +
                "                        \"23\",\n" +
                "                        \"30\",\n" +
                "                        \"35\",\n" +
                "                        \"2\"\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"description\",\n" +
                "                    \"value\": \"<p>Running errands or headed to the gym, you just want to be comfortable. The Olivia Light Jacket promises that, plus a laid-back look. This zip-up is designed with shoulder stripes for an athletic touch, and banded waist and contoured seams for a flattering silhouette.</p>\\n<p>&bull; Loose fit.</br>&bull; Reflectivity.</br>&bull; Flat seams.</br>&bull; Machine wash/dry.</p>\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"size\",\n" +
                "                    \"value\": \"5595\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attribute_code\": \"color\",\n" +
                "                    \"value\": \"5476\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"search_criteria\": {\n" +
                "        \"filter_groups\": [\n" +
                "            {\n" +
                "                \"filters\": [\n" +
                "                    {\n" +
                "                        \"field\": \"category_id\",\n" +
                "                        \"value\": \"23\",\n" +
                "                        \"condition_type\": \"eq\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"filters\": [\n" +
                "                    {\n" +
                "                        \"field\": \"size\",\n" +
                "                        \"value\": \"5595\",\n" +
                "                        \"condition_type\": \"eq\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"filters\": [\n" +
                "                    {\n" +
                "                        \"field\": \"color\",\n" +
                "                        \"value\": \"5476\",\n" +
                "                        \"condition_type\": \"eq\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"filters\": [\n" +
                "                    {\n" +
                "                        \"field\": \"price\",\n" +
                "                        \"value\": \"150\",\n" +
                "                        \"condition_type\": \"lt\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"total_count\": 4\n" +
                "}";
        System.out.println(response);
        Set<String> fieldSet = new HashSet<>();
        fieldSet.add("image");
        fieldSet.add("thumbnail");
        fieldSet.add("description");
        fieldSet.add("small_image");
        fieldSet.add("msrp_display_actual_price_type");
        fieldSet.add("url_key");
        fieldSet.add("required_options");
        fieldSet.add("tax_class_id");
        fieldSet.add("has_options");
        fieldSet.add("category_ids");
        fieldSet.add("options_container");
        Map<String,Map<String,Object>> filters = new HashMap<>();

        //从产品返回列表中取可用的size，color
        List<GfChannelProduct> gfChannelProductList= new ArrayList<>();
        GfProductSearchResponse gfProductSearchResponse = new Gson().fromJson(response, GfProductSearchResponse.class);
        gfProductSearchResponse.getItems().forEach(gfProduct->{
            GfChannelProduct gfChannelProduct= new GfChannelProduct();
            gfChannelProduct.setId(gfProduct.getId());
            gfChannelProduct.setSku(gfProduct.getSku());
            gfChannelProduct.setName(gfProduct.getName());
            gfChannelProduct.setBrand_link(gfProduct.getBrand_link());
            gfChannelProduct.setBrand_name(gfProduct.getBrand_name());
            gfChannelProduct.setDesigner_link(gfProduct.getDesigner_link());
            gfChannelProduct.setPrice(gfProduct.getPrice());
            List<GfMediaGalleryEntry> gfMediaGalleryEntry=gfProduct.getMedia_gallery_entries();
            gfMediaGalleryEntry.get(0).getFile();
            gfChannelProduct.setFile(gfMediaGalleryEntry.get(0).getFile());
            gfChannelProductList.add(gfChannelProduct);
            gfProduct.getCustom_attributes().forEach(gfProductCustomAttribute -> {
                String customAttribute = gfProductCustomAttribute.getAttribute_code();
                Object customValue = gfProductCustomAttribute.getValue();
                if(!fieldSet.contains(customAttribute))
                    if(filters.containsKey(customAttribute)){
                        Map<String,Object> filterValueTemp = filters.get(customAttribute);
                        if(!filterValueTemp.containsKey(customValue.toString())){
                            filters.get(customAttribute).put(customValue.toString(),0);
                        }
                    }else{
                        Map<String,Object> filterValue = new  HashMap<>();
                        filterValue.put(customValue.toString(),0);
                        filters.put(customAttribute,filterValue);
                    }
            });
        });
        gfProductSearchResponse.getSearch_criteria().getFilter_groups().forEach(Filter_group->{
            Filter_group.getFilters().forEach(searchFilter->{
                if(filters.containsKey(searchFilter.getField())){
                    if(filters.get(searchFilter.getField()).containsKey(searchFilter.getValue())){
                        filters.get(searchFilter.getField()).put(searchFilter.getValue(),"1");
                    }
                }
            });
        });


        filters.forEach((key,value)->{
            System.out.println(key+" : "+value.toString());
        });


        System.out.println("getTotal_count:" + gfProductSearchResponse.getTotal_count());
    }
}