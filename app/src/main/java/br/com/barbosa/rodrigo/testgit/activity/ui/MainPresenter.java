package br.com.barbosa.rodrigo.testgit.activity.ui;


import android.view.View;

import java.util.List;

import br.com.barbosa.rodrigo.testgit.activity.api.GistAPI;
import br.com.barbosa.rodrigo.testgit.activity.model.Gist;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainPresenter {

    private MainView mainView;


    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public void load(int page, int per_page) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GistAPI api = retrofit.create(GistAPI.class);

        api.getAllPublic(page, per_page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        String erro = "";
                    }

                })
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Gist>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mainView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Gist> gists) {
                        mainView.showData(gists);

                    }
                });


    }
}