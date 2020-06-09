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
        
        
        
        
        
        
        
        
        
        
        
        
         
        
        
        aws dynamodb put-item \
    --table-name Music \
    --item \
    '{"Artist": {"S": "HI"}, "SongTitle": {"S": "Happy Day"}, "AlbumTitle": {"S": "Songs About Life"}, 
    "Awards": {"N": "10"}, "Attr1": {"S": "hhah"},"Arr":{"SS":["what","happend","lalaal"]},"Attr2": {"S":"nihaoma hahah"}}'
 
 
 aws dynamodb scan --table-name Music > export.json