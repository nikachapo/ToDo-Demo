package com.chapo.todo.common.domain

import java.io.IOException

class NetworkUnavailableException(message: String = "No network available") : IOException(message)

class UnauthorizedException(message: String = "Session expired"): IOException(message)

class UserAlreadyRegisteredException(message: String = "User already registered"): IOException(message)

class NetworkException(message: String): Exception(message)