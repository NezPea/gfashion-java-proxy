// dependencies
const AWS = require('aws-sdk');
const util = require('util');
const sharp = require('sharp');

// get reference to S3 client
const s3 = new AWS.S3();
const {TARGET_BUCKET_NAME} = process.env;

const LARGE_PATH = 'large';
const SMALL_PATH = 'small';
const THUMBNAIL_PATH = 'thumbnail';

const SMALL_WIDTH = 480;
const THUMBNAIL_WIDTH = 200;

exports.handler = async (event) => {

    // Read options from the event parameter.
    console.log("Reading options from event:\n", util.inspect(event, {depth: 5}));
    const srcBucket = event.Records[0].s3.bucket.name;
    // Object key may have spaces or unicode non-ASCII characters.
    const srcKey = decodeURIComponent(event.Records[0].s3.object.key.replace(/\+/g, " "));

    const largeKey = getScaleKey(srcKey, LARGE_PATH);
    const smallKey = getScaleKey(srcKey, SMALL_PATH);
    const thumbnailKey = getScaleKey(srcKey, THUMBNAIL_PATH);

    // Infer the image type from the file suffix.
    const typeMatch = srcKey.match(/\.([^.]*)$/);
    if (!typeMatch) {
        console.log("Could not determine the image type.");
        return;
    }

    // Check that the image type is supported  
    const imageType = typeMatch[1].toLowerCase();
    if (imageType !== "jpg" && imageType !== "png") {
        console.log(`Unsupported image type: ${imageType}`);
        return;
    }

    // Download the image from the S3 source bucket. 

    let largeImage;
    try {
        const params = {
            Bucket: srcBucket,
            Key: srcKey
        };
        largeImage = await s3.getObject(params).promise();
    } catch (error) {
        console.log(error);
        return;
    }  

    // Use the Sharp module to resize the image and save in a buffer.
    let smallBuffer, thumbnailBuffer;
    try { 
        smallBuffer = await sharp(largeImage.Body).resize(SMALL_WIDTH).toBuffer();
        thumbnailBuffer = await sharp(largeImage.Body).resize(THUMBNAIL_WIDTH).toBuffer();
    } catch (error) {
        console.log(error);
        return;
    } 

    // Upload the thumbnail image to the destination bucket
    try {
        let destParams = {
            Bucket: TARGET_BUCKET_NAME,
            Key: smallKey,
            Body: smallBuffer,
            ContentType: "image"
        };
        let putResult = await s3.putObject(destParams).promise();
        console.log(`The result of upload ${smallKey} to ${TARGET_BUCKET_NAME} is: ${JSON.stringify(putResult)}`); 

        destParams = {
            ...destParams,
            Key: thumbnailKey,
            Body: thumbnailBuffer,
        };
        putResult = await s3.putObject(destParams).promise();
        console.log(`The result of upload ${thumbnailKey} to ${TARGET_BUCKET_NAME} is: ${JSON.stringify(putResult)}`); 

        const copyParams = {
            Bucket: TARGET_BUCKET_NAME,
            CopySource: `${srcBucket}/${srcKey}`,
            Key: largeKey,
        };
        putResult = await s3.copyObject(copyParams).promise();
        console.log(`The result of copy ${largeKey} to ${TARGET_BUCKET_NAME} is: ${JSON.stringify(putResult)}`);

        const params = {
            Bucket: srcBucket,
            Key: srcKey
        };
        const deleteResult = await s3.deleteObject(params).promise();
        console.log(`The result of delete ${srcBucket}/${srcKey} is: ${deleteResult}`); 
    } catch (error) {
        console.log(error);
        return;
    } 

    console.log(`Successfully resized ${srcBucket}/${srcKey} and uploaded to ${TARGET_BUCKET_NAME}`); 
};

function getScaleKey(key, scalePath) {
    const arr = key.split('/');
    const fileName = arr.pop();
    arr.push(scalePath);
    arr.push(fileName);
    return  arr.join('/');
}
