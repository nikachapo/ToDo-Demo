package com.chapo.todo.common.data.di

import com.chapo.todo.common.data.ApiConstants
import com.chapo.todo.common.data.api.ToDoApi
import com.chapo.todo.common.data.api.interceptors.AuthenticationInterceptor
import com.chapo.todo.common.data.api.interceptors.LoggingInterceptor
import com.chapo.todo.common.data.api.interceptors.NetworkStatusInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

  @Provides
  @Singleton
  fun provideApi(builder: Retrofit.Builder): ToDoApi {
    return builder
        .build()
        .create(ToDoApi::class.java)
  }

  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
    return Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_ENDPOINT)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
  }

  @Provides
  fun provideOkHttpClient(
      httpLoggingInterceptor: HttpLoggingInterceptor,
      networkStatusInterceptor: NetworkStatusInterceptor,
      authenticationInterceptor: AuthenticationInterceptor
  ): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(networkStatusInterceptor)
        .addInterceptor(authenticationInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()
  }

  @Provides
  fun provideHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(loggingInterceptor)

    interceptor.level = HttpLoggingInterceptor.Level.BODY

    return interceptor
  }
}