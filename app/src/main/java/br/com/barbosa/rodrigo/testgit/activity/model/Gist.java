package br.com.barbosa.rodrigo.testgit.activity.model;

import java.util.Map;

/**
 * Created by rodrigobarbosa on 04/12/17.
 */

public class Gist {

    private Owner owner;
    private Map<String, File> files;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Map<String, File> getFiles() {
        return files;
    }

    public void setFiles(Map<String, File> files) {
        this.files = files;
    }
}
