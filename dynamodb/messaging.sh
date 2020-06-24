#!/bin/bash
#
# Setup the Dynamodb tables.
# gfMsgMessage: table for one to one conversation messages.
#

# The main message table for all the messages.
aws dynamodb create-table \
    --table-name gfMsgMessage \
    --attribute-definitions AttributeName=receiver,AttributeType=S AttributeName=timeSent,AttributeType=N AttributeName=id,AttributeType=S \
    --key-schema AttributeName=receiver,KeyType=HASH AttributeName=timeSent,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=5 \
    --local-secondary-indexes \
      "[{\"IndexName\": \"ReceiverMsgIdIndex\", \"KeySchema\":[{\"AttributeName\":\"receiver\",\"KeyType\":\"HASH\"},
      {\"AttributeName\":\"id\",\"KeyType\":\"RANGE\"}],
      \"Projection\":{\"ProjectionType\":\"ALL\"}}]" \
    --profile local

aws dynamodb create-table \
    --table-name gfMsgBroadcastStatus \
    --attribute-definitions AttributeName=receiver,AttributeType=S AttributeName=timeSent,AttributeType=N AttributeName=id,AttributeType=S \
    --key-schema AttributeName=receiver,KeyType=HASH AttributeName=timeSent,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=5 \
    --local-secondary-indexes \
      "[{\"IndexName\": \"ReceiverMsgIdIndex\", \"KeySchema\":[{\"AttributeName\":\"receiver\",\"KeyType\":\"HASH\"},
      {\"AttributeName\":\"id\",\"KeyType\":\"RANGE\"}],
      \"Projection\":{\"ProjectionType\":\"ALL\"}}]" \
    --profile local

# Enable Time To Live (TTL) for messages.
aws dynamodb update-time-to-live --table-name gfMsgMessage --time-to-live-specification "Enabled=true, AttributeName=ttl" --profile local
aws dynamodb update-time-to-live --table-name gfMsgBroadcastStatus --time-to-live-specification "Enabled=true, AttributeName=ttl" --profile local

aws dynamodb put-item \
    --table-name gfMsgMessage \
    --item '{"id": {"S": "testid"}, "name": {"S": "test"}, "desc": {"S": "This is a test"}, "option": {"N": "1"}, "create_time": {"N": "10"}, "update_time": {"S": "hhah"}}' \
    --profile local 

aws dynamodb scan --table-name gfMsgMessage --profile local
