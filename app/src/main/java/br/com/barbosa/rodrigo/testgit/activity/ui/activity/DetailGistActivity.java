package br.com.barbosa.rodrigo.testgit.activity.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
                Intent intent = new Intent(DetailGistActivity.this, FileActivity.class);
                intent.putExtra(Constants.ARQUIVO, favorito.getCaminhoArquivo());
                startActivity(intent);
            }
        });


        favorito = (Favorito) getIntent().getExtras().getParcelable(Constants.FAVORITOS);

        preencheView();

        setToolbar();

    }

    private void preencheView() {
        tvNome.setText(favorito.getNome());

        tvTitulo.setText(favorito.getTitulo() == null ? "Titulo" : favorito.getTitulo());
        tvIdioma.setText(getString(R.string.idioma, favorito.getIdioma()));
        tvVerArquivo.setTag(favorito.getCaminhoArquivo());

        if (favorito.getImagem() != null)
            Picasso.with(this).load(favorito.getImagem()).into(imvAvatar);
    }

    private void setToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if ((this).getSupportActionBar() != null) {
            (this).getSupportActionBar().setTitle(R.string.git_title);
            (this).getSupportActionBar().setSubtitle("Detalhes");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
