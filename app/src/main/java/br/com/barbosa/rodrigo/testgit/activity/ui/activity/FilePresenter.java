package br.com.barbosa.rodrigo.testgit.activity.ui.activity;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rodrigobarbosa on 06/12/17.
 */

public class FilePresenter {

   private FileView mFileView;

    public FilePresenter(FileView fileView) {
        this.mFileView = fileView;
    }

    public void getTextFile(String caminho){
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.execute(caminho);
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
                mFileView.showError(e.getMessage());
            } catch (IOException e) {
                mFileView.showError(e.getMessage());
            }
            return sb.toString();
        }
        @Override
        protected void onPostExecute(String arquivo){
            Log.i("TEXT", arquivo);
            mFileView.showData(arquivo);
        }
    }
}
