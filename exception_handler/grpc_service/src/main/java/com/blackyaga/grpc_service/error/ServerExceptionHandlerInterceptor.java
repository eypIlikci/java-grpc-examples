package com.blackyaga.grpc_service.error;

import io.grpc.*;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;

@GRpcGlobalInterceptor
public class ServerExceptionHandlerInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        ServerCall.Listener<ReqT> delegate = serverCallHandler.startCall(serverCall, metadata);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(delegate) {
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (ServiceException e) {
                    Metadata trailers = new Metadata();
                    trailers.put(Metadata.Key.of("error", Metadata.ASCII_STRING_MARSHALLER), e.getMessage());
                    serverCall.close(Status.fromCodeValue(e.getCode().value())
                            .withCause (e)
                            .withDescription("error message"),trailers);
                }
            }
        };
    }
}
