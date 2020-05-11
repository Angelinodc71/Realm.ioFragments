package com.example.realmiofragments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class Config extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("carefeed.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
