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

/**
 * Created by rodrigobarbosa on 05/12/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "favorito";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_IDIOMA = "idioma";
    public static final String COLUMN_IMAGEM = "imagem";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts " +
                        "(id String primary key, COLUMN_NOME text,COLUMN_TITULO text,COLUMN_IDIOMA text, COLUMN_IMAGEM text)"
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

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where id=" + id + "", null);
        return res;
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

    public ArrayList<String> getAllFavoritos() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + COLUMN_NOME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(COLUMN_NOME)));
            res.moveToNext();
        }
        return array_list;
    }
}
