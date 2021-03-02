const util = require('util');

module.exports = {
    obtenerCantidadReintentos: async function (payloadBase64, redis) {
        redis.get = util.promisify(redis.get);
        let reintentosActuales = await redis.get(payloadBase64)
        return reintentosActuales!=null?parseInt(reintentosActuales):0;
    },

    registrarReintento: async function (payloadBase64, redis) {
        let reintentosActuales = await module.exports.obtenerCantidadReintentos(payloadBase64,redis);
        redis.set(payloadBase64,reintentosActuales+1);
    }
}