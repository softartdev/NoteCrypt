package com.softartdev.notecrypt.db;

import android.content.Context;

import java.io.File;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

abstract class RealmDbRepository implements DbStore {
    private Realm mRealm;

    RealmDbRepository(Context context) {
        Realm.init(context);
    }

    Realm getRealm() {
        return mRealm;
    }

    @Override
    public boolean isEncryption() {
        return !checkPass(null);
    }

    @Override
    public boolean checkPass(String password) {
        try {
            RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
            if (password != null) {
                builder.encryptionKey(getSecureKey(password));
            }
            RealmConfiguration realmConfiguration = builder.build();
            mRealm = Realm.getInstance(realmConfiguration);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void changePass(String oldPass, String newPass) {
        byte[] oldKey = getSecureKey(oldPass);
        byte[] newKey = getSecureKey(newPass);

        RealmConfiguration defaultConfig = mRealm.getConfiguration();
        byte[] key = defaultConfig.getEncryptionKey();
        if (!Arrays.equals(oldKey, key)) {
            Timber.d("Original: %s", Arrays.toString(key));
            Timber.d("Typed:    %s", Arrays.toString(oldKey));
            throw new RuntimeException("Wrong old password");
        }

        String defaultFileName = RealmConfiguration.DEFAULT_REALM_NAME; // default.realm
        String tempFileName = "temp.realm";

        File dir = defaultConfig.getRealmDirectory();
        File tempFile = new File(dir, tempFileName);
//        mRealm.writeEncryptedCopyTo(tempFile, newKey);
        mRealm.executeTransaction(realm -> {
            realm.writeEncryptedCopyTo(tempFile, newKey);
            realm.close();
        });
        mRealm.close();
//        mRealm = null;

        int count = Realm.getGlobalInstanceCount(defaultConfig);
        if (count != 0) {
            Timber.d("Open %s instances of Realm", count);
        }

        if (Realm.deleteRealm(defaultConfig)) {
            Timber.d("%s deleted", defaultFileName);
        }
        Realm.removeDefaultConfiguration();

        File defaultFile = new File(dir, defaultFileName);
        if (tempFile.renameTo(defaultFile)) {
            Timber.d("%s renamed to %s", tempFileName, defaultFileName);
        }

        RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
        builder.name(defaultFileName);
        if (newKey != null) {
            builder.encryptionKey(newKey);
        }
        defaultConfig = builder.build();
        Realm.setDefaultConfiguration(defaultConfig);
        mRealm = Realm.getDefaultInstance();
    }

    private byte[] getSecureKey(String pass){
        if (pass == null) {
            return null;
        }
        byte[] source = pass.getBytes();
        byte[] encryptionKey = new byte[64];
        System.arraycopy(source, 0, encryptionKey, 0, source.length);
        return encryptionKey;
    }
}
