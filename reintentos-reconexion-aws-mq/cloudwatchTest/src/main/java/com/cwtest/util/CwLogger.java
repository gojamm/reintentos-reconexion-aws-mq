package com.cwtest.util;

import com.cwtest.model.TrazaDesconexion;
import com.google.gson.Gson;
import software.amazon.awssdk.services.cloudwatchevents.CloudWatchEventsClient;
import software.amazon.awssdk.services.cloudwatchevents.model.PutEventsRequest;
import software.amazon.awssdk.services.cloudwatchevents.model.PutEventsRequestEntry;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;

public class CwLogger {
    private static CwLogger instance;
    private static String sequenceToken;
    private static CloudWatchLogsClient logsClient;
    private static CloudWatchEventsClient eventsClient;

    //private static final String LOG_GROUP_NAME = "Pruebas";
    //private static final String STREAM_NAME = "error-log";

    private CwLogger(){
        /*logsClient = CloudWatchLogsClient.builder()
                .build();*/

        eventsClient = CloudWatchEventsClient.builder().build();

        // logs
        /*DescribeLogStreamsRequest logStreamRequest = DescribeLogStreamsRequest.builder()
                .logGroupName(LOG_GROUP_NAME)
                .logStreamNamePrefix(STREAM_NAME)
                .build();
        DescribeLogStreamsResponse describeLogStreamsResponse = logsClient.describeLogStreams(logStreamRequest);

        sequenceToken = describeLogStreamsResponse.logStreams().get(0).uploadSequenceToken();*/
    }

    static{
        try{
            if(instance == null) {
                instance = new CwLogger();
            }
        }catch(Exception e){
            throw new RuntimeException("Exception!");
        }
    }

    public static CwLogger getInstance(){
        return instance;
    }

    /*public static void reportarTraza(com.cwtest.model.TrazaDesconexion tr){
        getInstance();

        InputLogEvent inputLogEvent = InputLogEvent.builder()
                .message(new Gson().toJson(tr))
                .timestamp(System.currentTimeMillis())
                .build();

        PutLogEventsRequest putLogEventsRequest = PutLogEventsRequest.builder()
                .logEvents(Arrays.asList(inputLogEvent))
                .logGroupName(LOG_GROUP_NAME)
                .logStreamName(STREAM_NAME)
                .sequenceToken(sequenceToken)
                .build();

        logsClient.putLogEvents(putLogEventsRequest);
    }*/

    public static void reportarEvento(TrazaDesconexion tr){
        PutEventsRequestEntry requestEntry = PutEventsRequestEntry.builder()
                .detail(new Gson().toJson(tr))
                .detailType("prueba ....")
                .resources("arn:aws:mq:us-west-2:609673039074:broker:serverlessrepo-lambda-publish-amazonmq-MqBroker:b-5b033529-dd29-4cd5-8950-031a97339ade")
                .source("errores-desconexion")
                .build();

        PutEventsRequest request = PutEventsRequest.builder()
                .entries(requestEntry)
                .build();

        eventsClient.putEvents(request);
    }
}
