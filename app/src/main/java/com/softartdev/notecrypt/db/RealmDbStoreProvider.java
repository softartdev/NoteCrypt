package com.softartdev.notecrypt.db;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmFileException;

abstract class RealmDbStoreProvider implements DbStore {
    private Realm mRealm;

    RealmDbStoreProvider(Context context) {
        Realm.init(context);
    }

    public Realm getRealm() {
        return mRealm;
    }

    @Override
    public boolean isEncryption() {
        try {
            mRealm = Realm.getDefaultInstance();
            return mRealm == null;
        } catch (RealmFileException e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public boolean checkPass(String password) {
        try {
            RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
            if (password != null) {
                builder.encryptionKey(getSecureKey(password));
            }
            RealmConfiguration realmConfiguration = builder.build();
            Realm.setDefaultConfiguration(realmConfiguration);
            mRealm = Realm.getDefaultInstance();
            return true;
        } catch (RealmFileException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void changePass(String oldPass, String newPass) {
        byte[] oldKey = getSecureKey(oldPass);
        byte[] newKey = getSecureKey(newPass);

        String oldName = mRealm.getConfiguration().getRealmFileName();
        String newName = getNextName(oldName);

        RealmConfiguration.Builder oldBuilder = new RealmConfiguration.Builder();
        oldBuilder.name(oldName);
        if (oldKey != null) {
            oldBuilder.encryptionKey(oldKey);
        }
        RealmConfiguration oldConfig = oldBuilder.build();

        Realm oldRealm = Realm.getInstance(oldConfig);
        File file = new File(oldConfig.getRealmDirectory(), newName);
        oldRealm.writeEncryptedCopyTo(file, newKey);
        oldRealm.close();

        RealmConfiguration newConfig = new RealmConfiguration.Builder()
                .name(newName)
                .encryptionKey(newKey)
                .build();
        Realm.setDefaultConfiguration(newConfig);
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

    private String getNextName(String oldName) {
        String oldVerString = oldName.replaceAll("\\D+","");
        int newVer, oldVer;
        if (TextUtils.isEmpty(oldVerString)) {
            oldVer = 0;
        } else {
            oldVer = Integer.parseInt(oldVerString);
        }
        newVer = oldVer + 1;
        String newVerString = String.valueOf(newVer);
        return oldVerString.replace(oldVerString, newVerString);
    }
}
