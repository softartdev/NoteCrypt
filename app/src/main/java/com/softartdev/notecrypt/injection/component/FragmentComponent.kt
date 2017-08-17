package com.softartdev.notecrypt.injection.component

import com.softartdev.notecrypt.injection.PerFragment
import com.softartdev.notecrypt.injection.module.FragmentModule
import dagger.Subcomponent

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent