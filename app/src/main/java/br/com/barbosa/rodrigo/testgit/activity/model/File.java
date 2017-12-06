package br.com.barbosa.rodrigo.testgit.activity.model;

/**
 * Created by rodrigobarbosa on 04/12/17.
 */

public class File {

    private String filename;
    private String language;
    private String raw_url;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRaw_url() {
        return raw_url;
    }

    public void setRaw_url(String raw_url) {
        this.raw_url = raw_url;
    }
}
