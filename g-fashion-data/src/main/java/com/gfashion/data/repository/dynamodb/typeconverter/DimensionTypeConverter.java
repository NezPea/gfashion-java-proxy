package com.gfashion.data.repository.dynamodb.typeconverter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class DimensionTypeConverter implements DynamoDBTypeConverter<String, ProductDimensionType> {

    @Override
    public String convert(ProductDimensionType object) {
        ProductDimensionType itemDimensions = (ProductDimensionType) object;
        String dimension = null;
        try {
            if (itemDimensions != null) {
                dimension = String.format("%s x %s x %s", itemDimensions.getLength(), itemDimensions.getHeight(),
                        itemDimensions.getThickness());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dimension;
    }

    @Override
    public ProductDimensionType unconvert(String s) {

        ProductDimensionType itemDimension = new ProductDimensionType();
        try {
            if (s != null && s.length() != 0) {
                String[] data = s.split("x");
                itemDimension.setLength(data[0].trim());
                itemDimension.setHeight(data[1].trim());
                itemDimension.setThickness(data[2].trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemDimension;
    }
}
