package com.chapo.todo.common.data.api.interceptors

import com.chapo.todo.common.data.ApiConstants.AUTH_ENDPOINT
import com.chapo.todo.common.data.ApiParameters.AUTH_HEADER
import com.chapo.todo.common.data.ApiParameters.CONTENT_TYPE
import com.chapo.todo.common.data.ApiParameters.CONTENT_TYPE_HEADER
import com.chapo.todo.common.data.ApiParameters.EMAIL_KEY
import com.chapo.todo.common.data.ApiParameters.PASSWORD_KEY
import com.chapo.todo.common.data.ApiParameters.TOKEN_TYPE
import com.chapo.todo.common.data.api.model.ApiAuthenticatedUser
import com.chapo.todo.common.data.di.Token
import com.chapo.todo.common.data.di.UserData
import com.chapo.todo.common.data.preferences.Preferences
import com.chapo.todo.common.domain.UnauthorizedException
import com.chapo.todo.common.domain.user.User
import com.squareup.moshi.Moshi
import okhttp3.*
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(
    @Token private val tokenPreferences: Preferences<String>,
    @UserData private val userPreferences: Preferences<User>
) : Interceptor {

    companion object {
        const val UNAUTHORIZED = 401
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenPreferences.getValue()
        val request = chain.request()

        val interceptedRequest: Request = if (token != null) {
            chain.createAuthenticatedRequest(token)
        } else {
            val tokenRefreshResponse = chain.refreshToken()
            if (tokenRefreshResponse?.isSuccessful != null) {
                val newToken = mapToken(tokenRefreshResponse)
                storeNewToken(newToken)
                chain.createAuthenticatedRequest(newToken)
            } else {
                request
            }
        }

        return chain.proceedDeletingTokenIfUnauthorized(interceptedRequest)
    }

    private fun Interceptor.Chain.refreshToken(): Response? {
        val user = userPreferences.getValue() ?: return null

        val url = request()
            .url
            .newBuilder(AUTH_ENDPOINT)!!
            .build()

        val body = FormBody.Builder()
            .add(EMAIL_KEY, user.email)
            .add(PASSWORD_KEY, user.password!!) // for simplicity I don't use encryption :]
            .build()

        val tokenRefresh = request()
            .newBuilder()
            .post(body)
            .url(url)
            .build()

        return proceedDeletingTokenIfUnauthorized(tokenRefresh)
    }

    private fun Interceptor.Chain.createAuthenticatedRequest(token: String): Request {
        return request()
            .newBuilder()
            .addHeader(AUTH_HEADER, TOKEN_TYPE + token)
            .addHeader(CONTENT_TYPE_HEADER, CONTENT_TYPE)
            .build()
    }

    private fun Interceptor.Chain.proceedDeletingTokenIfUnauthorized(request: Request): Response {
        val response = proceed(request)

        if (response.code == UNAUTHORIZED) {
            tokenPreferences.clearValue()
            throw UnauthorizedException()
        }

        return response
    }

    private fun mapToken(tokenRefreshResponse: Response): String {
        val moshi = Moshi.Builder().build()
        val userAdapter = moshi.adapter(ApiAuthenticatedUser::class.java)
        val responseBody = tokenRefreshResponse.body!! // if successful, this should be good :]

        return userAdapter.fromJson(responseBody.string())?.token
            ?: ApiAuthenticatedUser.INVALID_TOKEN
    }

    private fun storeNewToken(apiToken: String) {
        with(tokenPreferences) {
            putValue(apiToken)
        }
    }
}
