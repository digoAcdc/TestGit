package br.com.barbosa.rodrigo.testgit.activity.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.barbosa.rodrigo.testgit.activity.model.Favorito;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "favorito";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_IDIOMA = "idioma";
    public static final String COLUMN_IMAGEM = "imagem";
    public static final String COLUMN_ARQUIVO = "arquivo";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table favorito " +
                        "(id String primary key, nome text,titulo text,idioma text, imagem text, arquivo text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS favorito");
        onCreate(db);
    }

    public boolean insertFavorito(Favorito favorito) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, favorito.getId());
        contentValues.put(COLUMN_NOME, favorito.getNome());
        contentValues.put(COLUMN_TITULO, favorito.getTitulo());
        contentValues.put(COLUMN_IDIOMA, favorito.getIdioma());
        contentValues.put(COLUMN_IMAGEM, favorito.getImagem());
        contentValues.put(COLUMN_ARQUIVO, favorito.getCaminhoArquivo());
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Favorito getData(String id) {
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where id=" + id + "", null);

            while (res.isAfterLast() == false) {
                Favorito f = new Favorito();
                f.setId(res.getString(res.getColumnIndex(COLUMN_ID)));
                f.setNome(res.getString(res.getColumnIndex(COLUMN_NOME)));
                f.setIdioma(res.getString(res.getColumnIndex(COLUMN_IDIOMA)));
                f.setTitulo(res.getString(res.getColumnIndex(COLUMN_TITULO)));
                f.setImagem(res.getString(res.getColumnIndex(COLUMN_IMAGEM)));
                f.setCaminhoArquivo(res.getString(res.getColumnIndex(COLUMN_ARQUIVO)));

                return f;
            }
        } catch (Exception e) {
            return new Favorito();
        }
        return new Favorito();
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }


    public Integer deleteFavorito(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                "id = ? ",
                new String[]{id});
    }

    public ArrayList<Favorito> getAllFavoritos() {
        ArrayList<Favorito> array_list = new ArrayList<Favorito>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                Favorito f = new Favorito();
                f.setId(res.getString(res.getColumnIndex(COLUMN_ID)));
                f.setNome(res.getString(res.getColumnIndex(COLUMN_NOME)));
                f.setIdioma(res.getString(res.getColumnIndex(COLUMN_IDIOMA)));
                f.setTitulo(res.getString(res.getColumnIndex(COLUMN_TITULO)));
                f.setImagem(res.getString(res.getColumnIndex(COLUMN_IMAGEM)));
                f.setCaminhoArquivo(res.getString(res.getColumnIndex(COLUMN_ARQUIVO)));

                array_list.add(f);
                res.moveToNext();
            }
        } catch (Exception e) {
            return array_list;
        }

        return array_list;
    }
}
