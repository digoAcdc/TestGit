package br.com.barbosa.rodrigo.testgit.activity.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.barbosa.rodrigo.testgit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SobreFragment extends Fragment {

    private TextView tvSobre;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sobre, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvSobre = (TextView)view.findViewById(R.id.tvSobre);

        tvSobre.setText(Html.fromHtml(getString(R.string.sobre)));
    }
}
