package br.com.barbosa.rodrigo.testgit.activity.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.barbosa.rodrigo.testgit.R;
import br.com.barbosa.rodrigo.testgit.activity.adapter.FavoritosAdapter;
import br.com.barbosa.rodrigo.testgit.activity.data.DBHelper;
import br.com.barbosa.rodrigo.testgit.activity.model.Constants;
import br.com.barbosa.rodrigo.testgit.activity.model.Favorito;
import br.com.barbosa.rodrigo.testgit.activity.ui.activity.DetailGistActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends Fragment {

    private DBHelper db;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView rvFavoritos;
    private FavoritosAdapter mAdapter;
    private TextView tvTexto;


    private LinearLayout containerLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DBHelper(getContext());
        rvFavoritos = (RecyclerView) view.findViewById(R.id.rvFavoritos);
        containerLoading = (LinearLayout) view.findViewById(R.id.containerLoading);
        tvTexto = (TextView) view.findViewById(R.id.tvTexto);

        initRecyclerView();
        setToolbar();
    }

    private void setToolbar() {
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.git_title);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(getString(R.string.subtittle_favoritos));
        }
    }

    private void getFavoritos() {
        mAdapter.clean();

        List<Favorito> list = db.getAllFavoritos();
        if(list.size() == 0){
           tvTexto.setText(R.string.nenhuma_informacao_localizada);
            containerLoading.setVisibility(View.VISIBLE);
        }
        else {
            mAdapter.addAll(list);
            containerLoading.setVisibility(View.GONE);
        }
    }


    private void initRecyclerView() {

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvFavoritos.setLayoutManager(mLayoutManager);
        rvFavoritos.setNestedScrollingEnabled(false);
        rvFavoritos.setHasFixedSize(false);
        rvFavoritos.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new FavoritosAdapter(getContext(), onclickView());
        rvFavoritos.setAdapter(mAdapter);

        getFavoritos();

    }

    private FavoritosAdapter.FavoritosOnClickListener onclickView() {
        return new FavoritosAdapter.FavoritosOnClickListener() {
            DBHelper db = new DBHelper(getContext());

            @Override
            public void OnClick(View view, int index) {
                Intent i = new Intent(getActivity(), DetailGistActivity.class);
                i.putExtra(Constants.FAVORITOS, mAdapter.getWithIndex(index));

                startActivity(i);
            }

            @Override
            public void OnClickFavorito(View view, int index) {
                final ImageView imvFavorito = view.findViewById(R.id.imvFavorito);

                if (Boolean.valueOf(imvFavorito.getTag().toString())) {
                    Favorito g = mAdapter.getWithIndex(index);
                    db.deleteFavorito(g.getId());
                    getFavoritos();
                }
            }
        };

    }
}
