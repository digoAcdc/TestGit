package br.com.barbosa.rodrigo.testgit.activity.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import br.com.barbosa.rodrigo.testgit.R;
import br.com.barbosa.rodrigo.testgit.activity.model.Constants;

public class FileActivity extends AppCompatActivity implements FileView {

    private String caminhoArquivo = "";

    private TextView tvTextoDoArquivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        tvTextoDoArquivo = findViewById(R.id.tvTextoDoArquivo);

        caminhoArquivo = getIntent().getExtras().getString(Constants.ARQUIVO, "");

        FilePresenter presenter = new FilePresenter(this);
        presenter.getTextFile(caminhoArquivo);

        setToolbar();
    }

    @Override
    public void showData(String texto) {
        tvTextoDoArquivo.setText(texto);
    }

    @Override
    public void showError(String erro) {
        tvTextoDoArquivo.setText(erro);
    }

    private void setToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (((AppCompatActivity) this).getSupportActionBar() != null) {
            ((AppCompatActivity) this).getSupportActionBar().setTitle(R.string.git_title);
            ((AppCompatActivity) this).getSupportActionBar().setSubtitle(getString(R.string.texto_do_arquivo));
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
