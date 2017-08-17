package com.softartdev.notecrypt.di.component

import com.softartdev.notecrypt.di.PerActivity
import com.softartdev.notecrypt.di.module.ActivityModule
import com.softartdev.notecrypt.ui.base.BaseActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)
}
