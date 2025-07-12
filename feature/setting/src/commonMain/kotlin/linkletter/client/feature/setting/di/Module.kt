package linkletter.client.feature.setting.di

import linkletter.client.feature.setting.SettingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureSettingModule =
    module {
        viewModelOf(::SettingViewModel)
    }
