package com.example.trace.grpc.usecase

import com.example.trace.grpc.domain.model.User
import com.example.trace.grpc.domain.model.UserId
import com.example.trace.grpc.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class GetUserUseCase(
    private val userRepository: UserRepository
) {
    suspend fun getUser(userId: Int): User {
        return userRepository.getUser(userId)
    }

    suspend fun getUserWithValueClass(userId: UserId): User {
        return userRepository.getUser(userId.id)
    }
}