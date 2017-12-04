package br.com.barbosa.rodrigo.testgit.activity.api;

import java.util.List;


import br.com.barbosa.rodrigo.testgit.activity.model.Gist;
import retrofit2.http.GET;
import rx.Observable;


public interface GistAPI {

    @GET("/gists/public")
    Observable<List<Gist>> getAllPublic();
}
