package com.blackyaga.streaming_grpc_service.controller;
import com.blackyaga.streaming.grpc.interfaces.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@GRpcService
public class StreamingGrpcService extends StreamingServiceGrpc.StreamingServiceImplBase{
    @Override
    public void serverSideStreaming(Empty request, StreamObserver<Data> responseObserver) {
        for (int i = 0; i <10; i++) {
            Data data=Data.newBuilder()
                    .setTimestamp(Timestamp.valueOf(LocalDateTime.now()).toString())
                    .setMessage("message-"+(i+1))
                    .build();
            responseObserver.onNext(data);
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                return;
            }
        }
        Data data=Data.newBuilder()
                .setTimestamp(Timestamp.valueOf(LocalDateTime.now()).toString())
                .setMessage("message-ok")
                .build();
        responseObserver.onNext(data);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Data> clientSideStreaming(StreamObserver<Empty> responseObserver) {
        return new StreamObserver<Data>() {
            @Override
            public void onNext(Data data) {
                System.out.println("--data");
                System.out.println(data.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                    System.out.println("ERROR:"+throwable.getMessage());
            }
            @Override
            public void onCompleted() {
                System.out.println("cancel");
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Data> bidirectional(StreamObserver<Data> responseObserver) {
        return new StreamObserver<Data>() {
            String allMessage="";
            @Override
            public void onNext(Data data) {
                allMessage=allMessage+data.getMessage();
                Data newDate=Data.newBuilder()
                        .setTimestamp(Timestamp.valueOf(LocalDateTime.now()).toString())
                        .setMessage(allMessage)
                        .build();
                responseObserver.onNext(newDate);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("ERROR:"+throwable.getMessage());
            }
            @Override
            public void onCompleted() {
                    responseObserver.onCompleted();
            }
        };
    }
}
