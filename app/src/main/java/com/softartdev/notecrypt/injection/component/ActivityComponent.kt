package com.softartdev.notecrypt.injection.component

import com.softartdev.notecrypt.injection.PerActivity
import com.softartdev.notecrypt.injection.module.ActivityModule
import com.softartdev.notecrypt.ui.base.BaseActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)
}
