package br.com.barbosa.rodrigo.testgit.activity.api;

import java.io.File;
import java.util.List;


import br.com.barbosa.rodrigo.testgit.activity.model.Gist;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface GistAPI {

    @GET("/gists/public")
    Observable<List<Gist>> getAllPublic(@Query("page") long page,
                                        @Query("per_page") long per_page);

    @GET("/gists/{id}")
    Observable<List<Gist>> getAllPublic(@Path(value = "id") long id);

    //@GET("80e3bcd809b07d3e1ee2b7805f5dbbc1/raw/4ee01d278f97f521d1c874c7c42f549799e79bdb/file.cs")
    Observable<ResponseBody> getArquivo();

}
