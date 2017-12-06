package br.com.barbosa.rodrigo.testgit.activity.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import br.com.barbosa.rodrigo.testgit.R;
import br.com.barbosa.rodrigo.testgit.activity.adapter.GistAdapter;
import br.com.barbosa.rodrigo.testgit.activity.data.DBHelper;
import br.com.barbosa.rodrigo.testgit.activity.model.Constants;
import br.com.barbosa.rodrigo.testgit.activity.model.Favorito;
import br.com.barbosa.rodrigo.testgit.activity.model.File;
import br.com.barbosa.rodrigo.testgit.activity.model.Gist;
import br.com.barbosa.rodrigo.testgit.activity.ui.activity.DetailGistActivity;
import br.com.barbosa.rodrigo.testgit.activity.ui.activity.MainPresenter;
import br.com.barbosa.rodrigo.testgit.activity.ui.activity.MainView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GistFragment extends Fragment implements MainView {





    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 2; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private boolean infiniteScrollingEnabled = true;

    private boolean controlsVisible = true;


    private int page = 0;
    private int per_page = 15;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView rvGits;
    private GistAdapter mAdapter;
    private MainPresenter mainPresenter;

    private LinearLayout containerLoading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvGits = (RecyclerView) view.findViewById(R.id.rvGits);

        rvGits.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

                visibleItemCount = recyclerView.getChildCount();
                if (manager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager)manager;
                    firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
                    totalItemCount = gridLayoutManager.getItemCount();
                } else if (manager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager)manager;
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                    totalItemCount = linearLayoutManager.getItemCount();
                }


                if (infiniteScrollingEnabled) {
                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }


                    if (!loading && (totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // do something
                        onLoadMore();
                        loading = true;
                    }
                }

            }
        });
        containerLoading = (LinearLayout) view.findViewById(R.id.containerLoading);

        containerLoading.setVisibility(View.VISIBLE);
        mainPresenter = new MainPresenter(this);

        initRecyclerView();
        setToolbar();
    }

    private void onLoadMore(){
        mainPresenter.load(page, per_page);
    }

    private void setToolbar() {
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.git_title);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(getString(R.string.subtittle_home));
        }
    }

    private void initRecyclerView() {

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvGits.setLayoutManager(mLayoutManager);
        rvGits.setNestedScrollingEnabled(false);
        rvGits.setHasFixedSize(false);
        rvGits.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new GistAdapter(getContext(), onclickView());
        rvGits.setAdapter(mAdapter);

       onLoadMore();

    }

    private GistAdapter.GistOnClickListener onclickView() {
        return new GistAdapter.GistOnClickListener() {
            DBHelper db = new DBHelper(getContext());

            @Override
            public void OnClick(View view, int index) {
                Intent i = new Intent(getActivity(), DetailGistActivity.class);
                i.putExtra(Constants.FAVORITOS, returnFavorito(index));

                startActivity(i);
            }

            @Override
            public void OnClickFavorito(View view, int index) {
                final ImageView imvFavorito = view.findViewById(R.id.imvFavorito);

                if (Boolean.valueOf(imvFavorito.getTag().toString())) {
                    Gist g = mAdapter.getWithIndex(index);
                    db.deleteFavorito(g.getId());

                    imvFavorito.setImageResource(R.drawable.ic_nao_favoritado);
                    Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
                    imvFavorito.startAnimation(pulse);
                    imvFavorito.setTag(false);
                } else {
                    db.insertFavorito(returnFavorito(index));
                    imvFavorito.setImageResource(R.drawable.ic_favorito);
                    Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
                    imvFavorito.startAnimation(pulse);
                    imvFavorito.setTag(true);

                }
            }
        };

    }

    private Favorito returnFavorito(int index){
        Gist g = mAdapter.getWithIndex(index);
        File file = (File) g.getFiles().values().toArray()[0];
        Favorito f = new Favorito();
        f.setId(g.getId());
        f.setCaminhoArquivo(file.getRaw_url());
        f.setIdioma(f.getIdioma() == null ? "" : getString(R.string.idioma, f.getIdioma()));
        f.setNome(file.getFilename() == null ? "" : getString(R.string.nome, file.getFilename()));
        if (g.getOwner() != null)
            f.setTitulo(g.getOwner().getLogin() == null ? "" : g.getOwner().getLogin());

        if (g.getOwner() != null)
            if (g.getOwner().getAvatarUrl() != null)
                f.setImagem(g.getOwner().getAvatarUrl());


        return f;
    }

    public void showData(List<Gist> gists) {
        Log.i("TAG", "SUCESSO");

        mAdapter.addAll(gists);
        containerLoading.setVisibility(View.GONE);
        page += 1;
    }

    @Override
    public void showError(String erro) {
        Log.i("TAG", "ERRO");
        Toast.makeText(getContext(), erro, Toast.LENGTH_LONG).show();
        containerLoading.setVisibility(View.GONE);
    }

}
