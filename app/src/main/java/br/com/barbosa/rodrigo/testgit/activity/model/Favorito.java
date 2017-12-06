package br.com.barbosa.rodrigo.testgit.activity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rodrigobarbosa on 05/12/17.
 */

public class Favorito implements Parcelable {

    public String id;
    public String nome;
    public String titulo;
    public String idioma;
    public String imagem;
    public String caminhoArquivo;

    public Favorito() {

    }


    protected Favorito(Parcel in) {
        id = in.readString();
        nome = in.readString();
        titulo = in.readString();
        idioma = in.readString();
        imagem = in.readString();
        caminhoArquivo = in.readString();
    }

    public static final Creator<Favorito> CREATOR = new Creator<Favorito>() {
        @Override
        public Favorito createFromParcel(Parcel in) {
            return new Favorito(in);
        }

        @Override
        public Favorito[] newArray(int size) {
            return new Favorito[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nome);
        dest.writeString(titulo);
        dest.writeString(idioma);
        dest.writeString(imagem);
        dest.writeString(caminhoArquivo);
    }
}
