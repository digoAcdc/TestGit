package br.com.barbosa.rodrigo.testgit.activity.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import br.com.barbosa.rodrigo.testgit.R;
import br.com.barbosa.rodrigo.testgit.activity.api.GistAPI;
import br.com.barbosa.rodrigo.testgit.activity.data.ApiService;
import br.com.barbosa.rodrigo.testgit.activity.model.Constants;
import br.com.barbosa.rodrigo.testgit.activity.model.Favorito;
import br.com.barbosa.rodrigo.testgit.activity.model.Gist;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
        if (((AppCompatActivity) this).getSupportActionBar() != null) {
            ((AppCompatActivity) this).getSupportActionBar().setTitle(R.string.git_title);
            ((AppCompatActivity) this).getSupportActionBar().setSubtitle("Detalhes");
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
