package br.com.barbosa.rodrigo.testgit.activity;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by rodrigobarbosa on 05/12/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

    }
}

