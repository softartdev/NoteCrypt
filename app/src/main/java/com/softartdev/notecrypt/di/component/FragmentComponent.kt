package com.softartdev.notecrypt.di.component

import com.softartdev.notecrypt.di.PerFragment
import com.softartdev.notecrypt.di.module.FragmentModule
import dagger.Subcomponent

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent