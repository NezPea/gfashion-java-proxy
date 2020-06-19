aws dynamodb create-table \
    --table-name gfDesigner \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
   --provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=5
        
     
aws dynamodb create-table \
    --table-name gfBrand \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
   --provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=5
               
                  
aws dynamodb create-table \
    --table-name gfVendor \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=5



aws dynamodb create-table \
    --table-name gfProduct \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=5

aws dynamodb update-table \
    --table-name gfProduct \
    --stream-specification StreamEnabled=true,StreamViewType=NEW_AND_OLD_IMAGES
        


aws dynamodb create-table \
    --table-name gfSku \
    --attribute-definitions \
        AttributeName=productId,AttributeType=S \
        AttributeName=skuId,AttributeType=S \
    --key-schema \
        AttributeName=productId,KeyType=HASH \
        AttributeName=skuId,KeyType=RANGE \
    --provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=5
        
aws dynamodb delete-table --table-name gfProduct
        
        
        
        
        
        
        
         
        
        
        aws dynamodb put-item \
    --table-name Music \
    --item \
    '{"Artist": {"S": "HI"}, "SongTitle": {"S": "Happy Day"}, "AlbumTitle": {"S": "Songs About Life"}, 
    "Awards": {"N": "10"}, "Attr1": {"S": "hhah"},"Arr":{"SS":["what","happend","lalaal"]},"Attr2": {"S":"nihaoma hahah"}}'
 
 
 aws dynamodb scan --table-name Music > export.json