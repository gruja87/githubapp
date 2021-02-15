package com.githubapp.di

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.githubapp.BuildConfig
import com.githubapp.data.GithubApi
import com.githubapp.repositories.commitlist.CommitListRepository
import com.githubapp.repositories.commitlist.CommitListRepositoryImpl
import com.githubapp.repositories.profile.ProfileRepository
import com.githubapp.repositories.profile.ProfileRepositoryImpl
import com.githubapp.repositories.repolist.RepoListRepository
import com.githubapp.repositories.repolist.RepoListRepositoryImpl
import com.githubapp.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message: String ->
            Timber.tag(Constants.OKHTTP_LOGGING_TAG).d(message)
        }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .readTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS);
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson =
        GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    @Singleton
    @Provides
    fun provideGithubApi(gsonBuilder: Gson, client: OkHttpClient): GithubApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
            .create(GithubApi::class.java)

    @Singleton
    @Provides
    fun provideRepoListRepository(
        githubApi: GithubApi
    ): RepoListRepository = RepoListRepositoryImpl(
        githubApi
    )

    @Singleton
    @Provides
    fun provideProfileRepository(
        githubApi: GithubApi
    ): ProfileRepository = ProfileRepositoryImpl(
        githubApi
    )

    @Singleton
    @Provides
    fun provideCommitListRepository(
        githubApi: GithubApi
    ): CommitListRepository = CommitListRepositoryImpl(
        githubApi
    )

    @Singleton
    @Provides
    fun provideGlideInstance(
        application: Application
    ): RequestManager = Glide.with(application)
}