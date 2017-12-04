package br.com.barbosa.rodrigo.testgit.activity.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import br.com.barbosa.rodrigo.testgit.R;
import br.com.barbosa.rodrigo.testgit.activity.model.Gist;

public class MainActivity extends AppCompatActivity  implements MainView {
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this);

        mainPresenter.load();
    }

    @Override
    public void showData(List<Gist> gists) {
        Log.i("TAG", "SUCESSO");

        //((File)gists.get(0).getFiles().values().toArray()[0]).getFilename()
    }

    @Override
    public void showError(String erro) {
        Log.i("TAG", "ERRO");
    }
}
