const uuidv1 = require('uuid/v1');

module.exports = {
    reencolar: async (payload, destino, mqtt) => {
        let mqHost = `wss://${process.env.MQ_HOST}`
        const options = {
            username: process.env.MQ_USERNAME,
            password: process.env.MQ_PASSWORD,
            clientId: 'lambda_' + uuidv1(),
            port: process.env.MQ_PORT
        }
        try {
            let client = mqtt.connect(mqHost, options);
            //await client.publish(destino, payload, {AMQ_SCHEDULED_DELAY:30000})
            await client.publish(destino, payload);
            await client.end();
        }catch (err){
            console.log(`Error al enviar msj:${payload}, destino:${destino}, endpoint:${mqHost}, error: ${JSON.stringify(err, null, 2)}`, err);
            console.log(err.stack);
            process.exit();
        }
    }
}