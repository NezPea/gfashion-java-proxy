#!/bin/bash

server=$1
username=$2
password=$3
root_id=$4
storeViewCN=$5

./initialize-magento-categories-english.sh $server $username $password $root_id $storeViewCN
#./initialize-magento-categories-chinese.sh $server $username $password $root_id
./initialize-ITData.sh $server $username $password

