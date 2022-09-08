package com.blackyaga.grpc_service.controller.grpc;


import com.blackyaga.exceptionhandler.grpc.interfaces.LoginRequest;
import com.blackyaga.exceptionhandler.grpc.interfaces.LoginResponse;
import com.blackyaga.exceptionhandler.grpc.interfaces.LoginServiceGrpc;
import com.blackyaga.grpc_service.error.ErrorConstants;
import com.blackyaga.grpc_service.error.ServiceException;
import com.blackyaga.grpc_service.model.User;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import javax.sql.rowset.serial.SerialException;

@GRpcService
public class LoginGrpcService extends LoginServiceGrpc.LoginServiceImplBase {
    private User user=new User();

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        if (!user.check(request.getUsername(),request.getPassword())){
            throw new ServiceException(Status.Code.UNAUTHENTICATED, ErrorConstants.LOGIN);
        }
        responseObserver.onNext(LoginResponse.newBuilder().setMessage("SUCCESSFUL").build());
        responseObserver.onCompleted();
    }
}
