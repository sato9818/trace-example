package com.example.trace.grpc.repository

import com.example.trace.grpc.domain.model.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository {
    suspend fun getUser(userId: Int): User {
        return User(userId, "Test Name", "test.example.com")
    }
}