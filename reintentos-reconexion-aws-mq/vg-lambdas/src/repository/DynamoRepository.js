module.exports = {
    obtenerCantidadReintentos: async function (payloadBase64, dynamoDB) {
        let queryParams = {
            TableName: process.env.DDB_REINTENTOS,
            KeyConditionExpression: "#k = :msg",
            ExpressionAttributeNames: {
                "#k": "msg"
            },
            ExpressionAttributeValues: {
                ":msg": payloadBase64
            },
            ConsistentRead: true
        };

        try{
            let data = await dynamoDB.query(queryParams).promise();
            return data.Items.length;
        } catch (err) {
            console.log(`Error al consultar en dynamoDB(${process.env.DDB_REINTENTOS}), payload(base64): ${payloadBase64}, error: ${JSON.stringify(err, null, 2)}`, err);
        }
    },

    registrarReintento: async function (payloadBase64, dynamoDB) {
        let ts = new Date().getTime(); // timestamp
        let ttl = (Math.floor(+new Date() / 1000) + (process.env.DDB_REINTENTOS_MINUTOS_TT*60*60)).toString() // time-to-live
        
        let putParams = {
            TableName: process.env.DDB_REINTENTOS,
            Item: {
                "msg": payloadBase64,
                "date": ts,
                "ttl": ttl
            }
        }
        let resultado;
        try{
            resultado = await dynamoDB.put(putParams).promise();
        } catch (err) {
            console.log(`Error al guardar en dynamoDB:${process.env.DDB_REINTENTOS}, params:${JSON.stringify(putParams)}, error: ${JSON.stringify(err, null, 2)}`, err);
        }
        return resultado;
    }
}