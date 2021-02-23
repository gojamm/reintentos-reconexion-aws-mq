const brokerService = require("./BrokerService");
//const reintentoRepository = require("../repository/DynamoRepository");
const reintentoRepository = require("../repository/RedisRepository");

module.exports = {
    procesarDesconexion: async function (event, mqtt, repository) {

        let detalle = event.detail;
        let payload = detalle.mensajeOriginal;
        let payloadBase64 = Buffer.from(payload).toString('base64');
        let destino = detalle.destino;

        let response = {
            'statusCode': 200,
            'body': `msg:${payload} publicado en ${destino}`
        }

        let reintentosActuales = await reintentoRepository.obtenerCantidadReintentos(payloadBase64, repository);
        if (reintentosActuales > process.env.DDB_REINTENTOS_TOPE) {
            response.body = `No se reencola mensaje: ${payload}, limite alcanzado: ${process.env.DDB_REINTENTOS_TOPE}`;
            repository.del(payloadBase64);

        } else {
            await brokerService.reencolar(payload, destino, mqtt)
            await reintentoRepository.registrarReintento(payloadBase64, repository);

        }

        console.log(response.body)
        //repository.quit();
        return response;
       
    }
}