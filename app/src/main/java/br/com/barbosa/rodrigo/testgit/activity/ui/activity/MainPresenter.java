package br.com.barbosa.rodrigo.testgit.activity.ui.activity;


import java.util.List;
import br.com.barbosa.rodrigo.testgit.activity.api.GistAPI;
import br.com.barbosa.rodrigo.testgit.activity.data.ApiService;
import br.com.barbosa.rodrigo.testgit.activity.model.Gist;
import retrofit2.Retrofit;
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

        ApiService api = new ApiService();
        Retrofit retrofit = api.getInstance();
        GistAPI iApiService = retrofit.create(GistAPI.class);

        iApiService.getAllPublic(page, per_page)
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