package tat.mukhutdinov.deliveries.delivery.di

import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tat.mukhutdinov.deliveries.delivery.domain.DeliveryInteractor
import tat.mukhutdinov.deliveries.delivery.domain.boundary.DeliveryGateway
import tat.mukhutdinov.deliveries.delivery.gateway.DeliveryBoundGateway
import tat.mukhutdinov.deliveries.delivery.gateway.api.DeliveriesApi
import tat.mukhutdinov.deliveries.delivery.gateway.converter.DeliveryConverter
import tat.mukhutdinov.deliveries.delivery.ui.boundary.DeliveryDomain
import tat.mukhutdinov.deliveries.infrastructure.db.DataBase

object DeliveryInjectionModule {
    private const val DATABASE_NAME = "deliveries_db"
    private const val BASE_SERVER_URL = "https://mock-api-mobile.dev.lalamove.com/"

    val module = module {

        single {
            Room.databaseBuilder(get(), DataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        single {
            get<DataBase>().deliveryDao()
        }

        single<DeliveriesApi> {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_SERVER_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(DeliveriesApi::class.java)
        }

        factory<DeliveryDomain> { (coroutineScope: CoroutineScope) ->
            DeliveryInteractor(get { parametersOf(coroutineScope) })
        }

        factory<DeliveryGateway> { (coroutineScope: CoroutineScope) ->
            DeliveryBoundGateway(get(), get(), get(), get(), coroutineScope)
        }

        factory {
            DeliveryConverter()
        }

    }
}