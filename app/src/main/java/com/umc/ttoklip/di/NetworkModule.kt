package com.umc.ttoklip.di

import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.TtoklipApplication.Companion.getString
import com.umc.ttoklip.data.api.FCMApi
import com.umc.ttoklip.data.api.HomeApi
import com.umc.ttoklip.data.api.HoneyTipApi
import com.umc.ttoklip.data.api.KakaoApi
import com.umc.ttoklip.data.api.LoginApi
import com.umc.ttoklip.data.api.MyPage2Api
import com.umc.ttoklip.data.api.MyPageApi
import com.umc.ttoklip.data.api.MainCommsApi
import com.umc.ttoklip.data.api.MainTogethersApi
import com.umc.ttoklip.data.api.MyPage3Api
import com.umc.ttoklip.data.api.MyPostApi
import com.umc.ttoklip.data.api.NaverApi
import com.umc.ttoklip.data.api.NewsApi
import com.umc.ttoklip.data.api.OtherApi
import com.umc.ttoklip.data.api.ReadCommsApi
import com.umc.ttoklip.data.api.ReadTogetherApi
import com.umc.ttoklip.data.api.Search2Api
import com.umc.ttoklip.data.api.SearchApi
import com.umc.ttoklip.data.api.SignupApi
import com.umc.ttoklip.data.api.TermApi
import com.umc.ttoklip.data.api.TestApi
import com.umc.ttoklip.data.api.TownMainApi
import com.umc.ttoklip.data.api.WriteCommsApi
import com.umc.ttoklip.data.api.WriteTogetherApi
import com.umc.ttoklip.util.NaverInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val NETWORK_EXCEPTION_OFFLINE_CASE = "network status is offline"
    const val NETWORK_EXCEPTION_BODY_IS_NULL = "result body is null"

    @Provides
    @Singleton
    fun provideOKHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val authIntercepter = AuthIntercepter()

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authIntercepter)
            .retryOnConnectionFailure(false)
            .build()
    }

    @Provides
    @Singleton
    @Named("kakaoClient")
    fun providesKOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {

            addInterceptor (interceptor)
            connectTimeout(25, TimeUnit.SECONDS)
            readTimeout(25, TimeUnit.SECONDS)
            writeTimeout(25, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    @Named("NaverClient")
    fun provideNaverOKHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val closeInterceptor = Interceptor { chain ->
            val request: Request =
                chain.request().newBuilder().addHeader("Connection", "close").build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(NaverInterceptor())
            .addInterceptor(interceptor)
            .addNetworkInterceptor(closeInterceptor)
            .retryOnConnectionFailure(false)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(TtoklipApplication.getString(R.string.base_url))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    class AuthIntercepter : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
//            if(request().headers["Auth"]=="false"){
//                val newRequest = request().newBuilder()
//                    .removeHeader("Auth")
//                    .build()
//                return chain.proceed(newRequest)
//            }
            val token = ("Bearer " + TtoklipApplication.prefs.getString("jwt", ""))
            val newRequest = request().newBuilder()
                .addHeader("Authorization", token)
                .build()
            proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    @Named("kakao")
    fun providesKakaoRetrofit(
        @Named("kakaoClient") client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TtoklipApplication.getString(R.string.kakao))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("naver")
    fun providesNaverRetrofit(
        @Named("NaverClient") client: OkHttpClient,
    ): Retrofit{
        return Retrofit.Builder()
            .client(client)
            .baseUrl(getString(R.string. naver_url))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTestApi(retrofit: Retrofit): TestApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideHoneyTipApi(retrofit: Retrofit): HoneyTipApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideSearch2Api(retrofit: Retrofit): Search2Api {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideMyPageApi(retrofit: Retrofit): MyPageApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideTermApi(retrofit: Retrofit): TermApi{
        return retrofit.buildService()
    }
    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideTownMainApi(retrofit: Retrofit): TownMainApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi{
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideSignupApi(retrofit: Retrofit): SignupApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun providesKakaoService(@Named("kakao") retrofit: Retrofit):KakaoApi{
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideMyPage2Api(retrofit: Retrofit): MyPage2Api{
        return retrofit.buildService()
    }
    @Provides
    @Singleton
    fun provideMyPage3Api(retrofit: Retrofit): MyPage3Api {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun providesMyPostApi(retrofit: Retrofit): MyPostApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideReadCommsApi(retrofit: Retrofit): ReadCommsApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideReadTogetherApi(retrofit: Retrofit): ReadTogetherApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideWriteCommsApi(retrofit: Retrofit): WriteCommsApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideWriteTogetherApi(retrofit: Retrofit): WriteTogetherApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideMainCommsApi(retrofit: Retrofit): MainCommsApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideMainTogethersApi(retrofit: Retrofit): MainTogethersApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideStrangerApi(retrofit: Retrofit): OtherApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideFCMApi(retrofit: Retrofit): FCMApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideNaverApi(@Named("naver") retrofit: Retrofit): NaverApi{
        return retrofit.buildService()
    }


    private inline fun <reified T> Retrofit.buildService(): T {
        return this.create(T::class.java)
    }
}