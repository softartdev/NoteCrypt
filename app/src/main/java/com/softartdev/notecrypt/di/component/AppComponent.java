package com.softartdev.notecrypt.di.component;

import com.softartdev.notecrypt.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

}
