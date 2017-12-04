package br.com.barbosa.rodrigo.testgit.activity.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rodrigobarbosa on 04/12/17.
 */

public class Owner {

    private String login;
    @SerializedName(value = "avatar_url")
    private String avatarUrl;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
