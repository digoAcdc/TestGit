package br.com.barbosa.rodrigo.testgit.activity.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.barbosa.rodrigo.testgit.R;
import br.com.barbosa.rodrigo.testgit.activity.model.Constants;

public class FileActivity extends AppCompatActivity implements FileView {

    private String caminhoArquivo = "";

    private TextView tvTextoDoArquivo;
    private LinearLayout containerLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        tvTextoDoArquivo = findViewById(R.id.tvTextoDoArquivo);
        containerLoading = findViewById(R.id.containerLoading);

        containerLoading.setVisibility(View.VISIBLE);

        caminhoArquivo = getIntent().getExtras().getString(Constants.ARQUIVO, "");

        FilePresenter presenter = new FilePresenter(this);
        presenter.getTextFile(caminhoArquivo);

        setToolbar();
    }

    @Override
    public void showData(String texto) {
        tvTextoDoArquivo.setText(Html.fromHtml(texto));
        containerLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError(String erro) {
        tvTextoDoArquivo.setText(erro);
        containerLoading.setVisibility(View.GONE);
    }

    private void setToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if ((this).getSupportActionBar() != null) {
            (this).getSupportActionBar().setTitle(R.string.git_title);
            (this).getSupportActionBar().setSubtitle(getString(R.string.texto_do_arquivo));
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
