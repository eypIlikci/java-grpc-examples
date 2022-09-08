package com.blackyaga.security.service.controller.grpc;

import com.blackyaga.security.grpc.interfaces.AuthTestServiceGrpc;
import com.blackyaga.security.grpc.interfaces.Credentials;
import com.blackyaga.security.grpc.interfaces.MessageResponse;
import com.blackyaga.security.grpc.interfaces.TokenResponse;
import com.blackyaga.security.service.auth.AuthService;
import com.blackyaga.security.service.auth.authorization.AdminAuthorization;
import com.blackyaga.security.service.auth.authorization.UserAuth;
import com.blackyaga.security.service.auth.authorization.UserAuthorization;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@GRpcService
public class AuthTestGrpcServiceImpl extends AuthTestServiceGrpc.AuthTestServiceImplBase {
    @Autowired
    private AuthService authService;

    @Override
    public void adminLogin(Credentials request, StreamObserver<TokenResponse> responseObserver) {
        com.blackyaga.security.service.auth.Credentials credentials=new com.blackyaga.security.service.auth.Credentials();
        credentials.setPassword(request.getPassword());
        credentials.setUsername(request.getUsername());
        var token=TokenResponse.newBuilder().setToken(authService.adminAuthenticate(credentials)).build();
        responseObserver.onNext(token);
        responseObserver.onCompleted();
    }

    @Override
    public void userLogin(Credentials request, StreamObserver<TokenResponse> responseObserver) {
        com.blackyaga.security.service.auth.Credentials credentials=new com.blackyaga.security.service.auth.Credentials();
        credentials.setPassword(request.getPassword());
        credentials.setUsername(request.getUsername());
        var token=TokenResponse.newBuilder().setToken(authService.userAuthenticate(credentials)).build();
        responseObserver.onNext(token);
        responseObserver.onCompleted();
    }

    @Override
    @UserAuthorization
    public void userReq(Empty request, StreamObserver<MessageResponse> responseObserver) {
        UserAuth userDetails=(UserAuth) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        responseObserver.onNext(MessageResponse.newBuilder().setMessage("OK USER, username:"+authService.getUserAuth().getUser().getUsername()).build());
        responseObserver.onCompleted();
    }

    @Override
    @AdminAuthorization
    public void adminReq(Empty request, StreamObserver<MessageResponse> responseObserver) {
        responseObserver.onNext(MessageResponse.newBuilder().setMessage("OK ADMIN, username:"+authService.getAdminAuth().getAdmin().getUsername()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void publicReq(Empty request, StreamObserver<MessageResponse> responseObserver) {
        responseObserver.onNext(MessageResponse.newBuilder().setMessage("OK PUBLIC").build());
        responseObserver.onCompleted();
    }
}
