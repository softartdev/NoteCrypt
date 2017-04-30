package com.softartdev.notecrypt.di.module;

import android.content.Context;
import android.support.annotation.Nullable;

import com.softartdev.notecrypt.di.scope.DbScope;

import java.util.Arrays;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class DbModule {
    @Nullable
    private String mPassword;

    public DbModule(@Nullable String pass) {
        mPassword = pass;
    }

    @Provides
    @DbScope
    Realm provideRealm(Context context) {
        Realm.init(context);
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
        if (mPassword != null) {
            builder.encryptionKey(getSecureKey());
        }
        RealmConfiguration realmConfiguration = builder.build();
        Realm.setDefaultConfiguration(realmConfiguration);
        return Realm.getDefaultInstance();
    }

    private byte[] getSecureKey(){
        byte[] source = mPassword != null ? mPassword.getBytes() : new byte[0];
        byte[] encryptionKey = new byte[64];
        System.arraycopy(source, 0, encryptionKey, 0, source.length);
        return encryptionKey;
    }
}
