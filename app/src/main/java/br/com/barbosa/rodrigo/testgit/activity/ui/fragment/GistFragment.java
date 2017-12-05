package br.com.barbosa.rodrigo.testgit.activity.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import br.com.barbosa.rodrigo.testgit.R;
import br.com.barbosa.rodrigo.testgit.activity.adapter.GistAdapter;
import br.com.barbosa.rodrigo.testgit.activity.model.Gist;
import br.com.barbosa.rodrigo.testgit.activity.ui.MainActivity;
import br.com.barbosa.rodrigo.testgit.activity.ui.MainPresenter;
import br.com.barbosa.rodrigo.testgit.activity.ui.MainView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GistFragment extends Fragment implements MainView {


    private int page = 0;
    private int per_page = 15;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView rvGits;
    private GistAdapter mAdapter;
    private MainPresenter mainPresenter;

    private LinearLayout containerLoading;



    public GistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvGits = (RecyclerView)view.findViewById(R.id.rvGits);
        containerLoading = (LinearLayout)view.findViewById(R.id.containerLoading);

        containerLoading.setVisibility(View.VISIBLE);
        initRecyclerView();

        mainPresenter = new MainPresenter(this);

        mainPresenter.load(page, per_page);
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

    }

    private GistAdapter.GistOnClickListener onclickView() {
        return new GistAdapter.GistOnClickListener() {
            @Override
            public void OnClick(View view, int index) {

            }
        };

    }
    public void showData(List<Gist> gists) {
        Log.i("TAG", "SUCESSO");

        mAdapter.addAll(gists);
        containerLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError(String erro) {
        Log.i("TAG", "ERRO");
        Toast.makeText(getContext(), erro, Toast.LENGTH_LONG).show();
        containerLoading.setVisibility(View.GONE);
    }

}
