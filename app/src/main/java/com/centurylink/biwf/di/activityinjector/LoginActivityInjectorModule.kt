package com.centurylink.biwf.di.activityinjector

import com.centurylink.biwf.screens.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginActivityInjectorModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginActivityInjector(): LoginActivity
}
