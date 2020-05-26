package <%= appPackage %>.di

import android.app.Application
import android.content.Context
import <%= appPackage %>.BuildConfig
import <%= appPackage %>.api.ApiManager
import <%= appPackage %>.api.ApiManagerImpl
import <%= appPackage %>.api.RestfulApiService
import <%= appPackage %>.env.ApiEnv
import <%= appPackage %>.util.DeviceInfoUtil
import <%= appPackage %>.util.Validator
import dagger.Module
import dagger.Provides
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    @Singleton
    @Named("UserAgentInterceptor")
    fun provideUserAgentInterceptor(app: Application): Interceptor {
        return Interceptor {
            try {
                val userAgent =
                    "${BuildConfig.APPLICATION_ID}/${BuildConfig.VERSION_NAME} (${DeviceInfoUtil.OS} ${DeviceInfoUtil.getSystemVersion()}; ${DeviceInfoUtil.getApiVersion()})"
                val request = it.request().newBuilder()
                    .header("User-Agent", userAgent)
                    .build()
                it.proceed(request)
            } catch (e: Exception) {
                throw IOException(e)
            }
        }
    }

    @Provides
    @Singleton
    fun provideConnectionSpec(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(
                TlsVersion.TLS_1_3,
                TlsVersion.TLS_1_2,
                TlsVersion.TLS_1_1,
                TlsVersion.TLS_1_0,
                TlsVersion.SSL_3_0
            )
            .build()
    }

    @Provides
    @Singleton
    @Named("SharedOkHttpClient")
    fun provideSharedOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .build()

    @Provides
    @Singleton
    @Named("RestfulApiOkHttpClient")
    fun provideRestfulApiOkHttpClient(
        @Named("SharedOkHttpClient") sharedClient: OkHttpClient,
        @Named("UserAgentInterceptor") userAgentInterceptor: Interceptor
    ): OkHttpClient = sharedClient.newBuilder()
        .addInterceptor(userAgentInterceptor)
        .apply {
            if (BuildConfig.DEBUG)
                addInterceptor(HttpLoggingInterceptor())
        }
        .readTimeout(25, TimeUnit.SECONDS) // enrollment is very slow
        .build()


    @Provides
    @Singleton
    fun provideRestfulApiService(
        @Named("RestfulApiOkHttpClient") client: OkHttpClient
    ): RestfulApiService {
        return Retrofit.Builder()
            .baseUrl(ApiEnv.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestfulApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiManager(rest: RestfulApiService): ApiManager {
        return ApiManagerImpl(rest)
    }

    @Provides
    @Named("ApplicationContext")
    fun provideApplicationContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideValidator(util: PhoneNumberUtil): Validator = Validator(util)
}
