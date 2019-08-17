package tat.mukhutdinov.deliveries.infrastructure.di

import tat.mukhutdinov.deliveries.delivery.di.DeliveryInjectionModule

object InjectionModules {

    val modules = listOf(
        DeliveryInjectionModule.module
    )
}