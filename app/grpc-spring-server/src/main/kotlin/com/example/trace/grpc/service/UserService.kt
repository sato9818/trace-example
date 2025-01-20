package com.example.trace.grpc.service

import com.example.trace.grpc.domain.model.UserId
import com.example.trace.grpc.usecase.GetUserUseCase
import com.example.trace.v1.UserServiceGrpcKt
import com.example.trace.v1.UserServiceOuterClass
import com.example.trace.v1.getUserResponse
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class UserService(
    private val getUserUseCase: GetUserUseCase
) : UserServiceGrpcKt.UserServiceCoroutineImplBase() {
    override suspend fun getUser(request: UserServiceOuterClass.GetUserRequest): UserServiceOuterClass.GetUserResponse {
        getUserUseCase.getUser(request.userId).let {
            getUserResponse {
                userId = it.userId
                name = it.name
                email = it.email
            }
        }
        return getUserUseCase.getUserWithValueClass(UserId(request.userId)).let {
            getUserResponse {
                userId = it.userId
                name = it.name
                email = it.email
            }
        }
    }
}