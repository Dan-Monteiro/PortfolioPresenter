package brcom.dan.example.portfoliopresenterapp.data.di


import android.util.Log
import brcom.dan.example.portfoliopresenterapp.data.services.GitHubService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {


    const val BASE_URL = "https://api.github.com"
    const val OK_HTTP = "OkHttp"

    fun load() {
        loadKoinModules(networkModules())
    }

    private fun networkModules(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor{
                    Log.e(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }
            single{
                GsonConverterFactory.create(GsonBuilder().create())
            }
            single {
                createService<GitHubService>(get(), get())
            }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T{
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(factory)
            .baseUrl(BASE_URL)
            .build().create(T::class.java)
    }
}