package br.com.barbosa.rodrigo.testgit.activity.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.barbosa.rodrigo.testgit.R;
import br.com.barbosa.rodrigo.testgit.activity.model.Constants;
import br.com.barbosa.rodrigo.testgit.activity.model.Favorito;

public class DetailGistActivity extends AppCompatActivity {

    private TextView tvNome;
    private TextView tvTitulo;
    private TextView tvIdioma;
    private TextView tvVerArquivo;
    private ImageView imvAvatar;

    private Favorito favorito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gist);


        tvNome = findViewById(R.id.tvNome);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvIdioma = findViewById(R.id.tvIdioma);
        tvVerArquivo = findViewById(R.id.tvVerArquivo);
        imvAvatar = findViewById(R.id.imvAvatar);


        tvVerArquivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        favorito = (Favorito) getIntent().getExtras().getParcelable(Constants.FAVORITOS);

        preencheView();

        setToolbar();

    }

    private void preencheView() {
        tvNome.setText(getString(R.string.nome, favorito.getNome()));

        tvTitulo.setText(favorito.getTitulo() == null ? "Titulo" : favorito.getTitulo());
        tvIdioma.setText(getString(R.string.idioma, favorito.getIdioma()));
        tvVerArquivo.setTag(favorito.getCaminhoArquivo());

        if (favorito.getImagem() != null)
            Picasso.with(this).load(favorito.getImagem()).into(imvAvatar);
    }

    private void setToolbar() {
        if (((AppCompatActivity) this).getSupportActionBar() != null) {
            ((AppCompatActivity) this).getSupportActionBar().setTitle(R.string.git_title);
            ((AppCompatActivity) this).getSupportActionBar().setSubtitle("Detalhes");
        }
    }
}
