'use strict';

const mqtt = require('async-mqtt');
//const AWS = require("aws-sdk");
const redis = require('redis');

const redis_client = redis.createClient({
    host: process.env.REDIS_HOST,
    port: process.env.REDIS_PORT
});

/*const dynamoDB = new AWS.DynamoDB.DocumentClient();*/
const service = require("../service/ReenvioService");

/*AWS.config.update({
  region: process.env.APP_REGION
});*/

module.exports.handler = async (event, context) => {
  return await service.procesarDesconexion(event, mqtt, redis_client);
};