package br.com.barbosa.rodrigo.testgit.activity.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import br.com.barbosa.rodrigo.testgit.R;
import br.com.barbosa.rodrigo.testgit.activity.adapter.GistAdapter;
import br.com.barbosa.rodrigo.testgit.activity.model.Gist;

public class MainActivity extends AppCompatActivity  implements MainView {
    private MainPresenter mainPresenter;

    private int page = 0;
    private int per_page = 15;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView rvGits;
    private GistAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvGits = (RecyclerView)findViewById(R.id.rvGits);

        initRecyclerView();

        mainPresenter = new MainPresenter(this);

        mainPresenter.load(page, per_page);
    }

    private void initRecyclerView() {

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvGits.setLayoutManager(mLayoutManager);
        rvGits.setNestedScrollingEnabled(false);
        rvGits.setHasFixedSize(false);
        rvGits.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new GistAdapter(this, onclickView());
        rvGits.setAdapter(mAdapter);

    }

    private GistAdapter.GistOnClickListener onclickView() {
        return new GistAdapter.GistOnClickListener() {
            @Override
            public void OnClick(View view, int index) {

            }

            @Override
            public void OnClickFavorito(View view, int index) {

            }
        };

    }
    public void showData(List<Gist> gists) {
        Log.i("TAG", "SUCESSO");

        mAdapter.addAll(gists);
    }

    @Override
    public void showError(String erro) {
        Log.i("TAG", "ERRO");
        Toast.makeText(MainActivity.this, erro, Toast.LENGTH_LONG).show();
    }
}
