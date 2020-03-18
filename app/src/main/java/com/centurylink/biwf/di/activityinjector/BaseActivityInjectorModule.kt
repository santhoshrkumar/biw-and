package com.centurylink.biwf.di.activityinjector

import com.centurylink.biwf.ui.activity.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BaseActivityInjectorModule {
    @ContributesAndroidInjector
    abstract fun contributeBaseActivityInjector(): BaseActivity
}