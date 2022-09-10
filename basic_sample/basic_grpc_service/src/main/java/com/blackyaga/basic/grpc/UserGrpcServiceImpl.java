package com.blackyaga.basic.grpc;
import com.blackyaga.basic.grpc.interfaces.UserRequest;
import com.blackyaga.basic.grpc.interfaces.UserResponse;
import com.blackyaga.basic.grpc.interfaces.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class UserGrpcServiceImpl extends UserServiceGrpc.UserServiceImplBase{

    @Override
    public void register(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        System.out.println("username:"+request.getUsername());
        responseObserver.onNext(UserResponse.newBuilder().setUsername("server-username").build());
        responseObserver.onCompleted();
    }

    @Override
    public void login(UserRequest request, StreamObserver<UserResponse> responseObserver) {

    }
}
