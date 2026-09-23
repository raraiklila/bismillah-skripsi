package com.cairosquad.evolvefit.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    includes(
        viewModelModule,
        repositoryModule,
        useCaseModule,
        localModule,
        // Gunakan mockRemoteModule untuk mode offline (tanpa backend)
        // Ganti ke remoteModule jika backend sudah aktif kembali
        mockRemoteModule
        // remoteModule
    )
}