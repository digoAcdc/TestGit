package br.com.barbosa.rodrigo.testgit.activity.ui.activity;

import java.util.List;

import br.com.barbosa.rodrigo.testgit.activity.model.Gist;

/**
 * Created by rodrigobarbosa on 04/12/17.
 */

public interface MainView {

    void showData(List<Gist> gists);

    void showError(String erro);

}