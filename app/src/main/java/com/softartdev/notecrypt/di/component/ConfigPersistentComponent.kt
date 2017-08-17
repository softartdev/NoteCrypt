package com.softartdev.notecrypt.di.component

import com.softartdev.notecrypt.di.ConfigPersistent
import com.softartdev.notecrypt.di.module.ActivityModule
import com.softartdev.notecrypt.di.module.FragmentModule
import com.softartdev.notecrypt.ui.base.BaseActivity
import com.softartdev.notecrypt.ui.base.BaseFragment
import dagger.Component

/**
 * A dagger component that will live during the lifecycle of an Activity or Fragment but it won't
 * be destroy during configuration changes. Check [BaseActivity] and [BaseFragment] to
 * see how this components survives configuration changes.
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface ConfigPersistentComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent

    fun fragmentComponent(fragmentModule: FragmentModule): FragmentComponent

}
