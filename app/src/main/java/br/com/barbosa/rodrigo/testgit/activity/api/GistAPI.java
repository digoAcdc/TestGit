package br.com.barbosa.rodrigo.testgit.activity.api;

import java.util.List;


import br.com.barbosa.rodrigo.testgit.activity.model.Gist;
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

}
