'use strict';

const mqtt = require('async-mqtt');
const redis = require('redis');
const service = require("../service/ReenvioService");

const redis_client = redis.createClient({
    host: process.env.REDIS_HOST,
    port: process.env.REDIS_PORT
});

module.exports.handler = async (event, context) => {
  return await service.procesarDesconexion(event, mqtt, redis_client);
};