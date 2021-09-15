package com.chapo.todo.common.di

import com.chapo.todo.common.data.UserDataRepository
import com.chapo.todo.common.domain.repositories.UserRepository
import com.chapo.todo.common.utils.CoroutineDispatchersProvider
import com.chapo.todo.common.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

  @Binds
  @ViewModelScoped
  abstract fun bindUserRepository(repository: UserDataRepository): UserRepository

  @Binds
  abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider):
          DispatchersProvider

}