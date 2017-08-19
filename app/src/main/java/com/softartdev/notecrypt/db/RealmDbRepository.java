package com.softartdev.notecrypt.db;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmFileException;
import io.realm.rx.RealmObservableFactory;
import io.realm.rx.RxObservableFactory;
import timber.log.Timber;

abstract class RealmDbRepository implements DbStore {
    private Realm mRealm;

    RealmDbRepository(Context context) {
        Realm.init(context);
    }

    public Realm getRealm() {
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
        if (mRealm != null) {
            mRealm.close();
            mRealm = null;
        }
        String newName = getNextName(oldName);

        RealmConfiguration.Builder oldBuilder = new RealmConfiguration.Builder();
        oldBuilder.name(oldName);
        if (oldKey != null) {
            oldBuilder.encryptionKey(oldKey);
        }
        RealmConfiguration oldConfig = oldBuilder.build();

        Realm oldRealm = Realm.getInstance(oldConfig);

        File dir = oldConfig.getRealmDirectory();
        oldRealm.writeEncryptedCopyTo(new File(dir, newName), newKey);
        oldRealm.close();
        if (Realm.deleteRealm(oldConfig)) {
            Timber.d("%s deleted", oldName);
        }

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
        String prefix = "default";
        String extension = ".realm";
        String oldVerString = oldName.replace(prefix,"").replace(extension, ""); // default[ver].realm -> [ver]
        int newVer, oldVer;
        if (TextUtils.isEmpty(oldVerString)) {
            oldVer = 0;
        } else {
            oldVer = Integer.parseInt(oldVerString);
        }
        newVer = oldVer + 1;
        String newVerString = String.valueOf(newVer);
        String newName = prefix.concat(newVerString).concat(extension);
        Timber.d("New file name: %s", newName);
        return newName;
    }
}
