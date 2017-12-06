package br.com.barbosa.rodrigo.testgit.activity.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
                load("https://gist.githubusercontent.com/anonymous/80e3bcd809b07d3e1ee2b7805f5dbbc1/raw/4ee01d278f97f521d1c874c7c42f549799e79bdb/file.cs");
            }
        });


        favorito = (Favorito) getIntent().getExtras().getParcelable(Constants.FAVORITOS);

        preencheView();

        setToolbar();

    }

    public void load(String link) {
        new DownloadFile().execute(link);

        /*ApiService api = new ApiService();
        Retrofit retrofit = api.getInstance1();
        GistAPI iApiService = retrofit.create(GistAPI.class);

        iApiService.getArquivo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        String erro = "";
                    }

                })
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        String erro = "";
                    }

                    @Override
                    public void onNext(ResponseBody s) {
                        String teste = "";
                        try {
                            String str = new String(s.bytes(), "UTF-8");
                             teste = str;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        teste = "";
                        DownloadImage(s);
                    }
                });*/


    }

    private boolean DownloadImage(ResponseBody body) {

        try {

            InputStream in = null;
            FileOutputStream out = null;

            try {
                in = body.byteStream();
                out = new FileOutputStream(getExternalFilesDir(null) + File.separator + "AndroidTutorialPoint.jpg");
                int c;

                while ((c = in.read()) != -1) {
                    out.write(c);
                }
            }
            catch (IOException e) {

                return false;
            }
            finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }



            return true;

        } catch (IOException e) {

            return false;
        }
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
        if (((AppCompatActivity) this).getSupportActionBar() != null) {
            ((AppCompatActivity) this).getSupportActionBar().setTitle(R.string.git_title);
            ((AppCompatActivity) this).getSupportActionBar().setSubtitle("Detalhes");
        }
    }

    public class DownloadFile extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute(){
            //Codigo
        }
        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    sb.append(str);
                }
                in.close();
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(String arquivo){
            Log.i("TEXT", arquivo);
        }
    }
}
