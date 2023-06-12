package com.example.myprevisaotempo;


        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.lang.reflect.Array;
        import java.util.ArrayList;
        import java.util.Collections;

public class DBInterface {
    public static SQLiteDatabase TempoDB;

    public void criarBanco() {
        try {
            TempoDB = SQLiteDatabase.openOrCreateDatabase("Myprevisaotempo", null);
            TempoDB.execSQL("CREATE TABLE IF NOT EXISTS Historico( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Cidade VARCHAR," +
                    "Latitude VARCHAR," +
                    "Longitude VARCHAR)");
            TempoDB.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InserirHistorico(String Latitude, String Longitude, String Cidade) {
        TempoDB = SQLiteDatabase.openOrCreateDatabase("Myprevisaotempo", null);
        String Add = "Insert into Historico (Cidade, Latitude, Longitude) VALUES (" + Cidade + "," + Latitude + "," + Longitude + ")";
        TempoDB.execSQL(Add);
    }

    public void InserirHistorico(String Latitude, String Longitude) {
        TempoDB = SQLiteDatabase.openOrCreateDatabase("Myprevisaotempo", null);
        String Add = "Insert into Historico (Cidade, Latitude, Longitude) VALUES (" + "Not Specified" + "," + Latitude + "," + Longitude + ")";
        TempoDB.execSQL(Add);
    }

    public static ArrayList<SaidaDB> ReturnList() {
        int id;
        String Cidade;
        String Latitude;
        String Longitude;
    try {
        TempoDB = SQLiteDatabase.openOrCreateDatabase("Myprevisaotempo", null);
        Cursor C = TempoDB.rawQuery("SELECT id, Cidade, Latitude, Longitude FROM Historico", null);
        ArrayList<SaidaDB> Result = new ArrayList<SaidaDB>();

        C.moveToFirst();
        while (C != null){
            id = C.getInt(C.getColumnIndexOrThrow("id"));
            Cidade = C.getString(C.getColumnIndexOrThrow("Cidade"));
            Latitude = C.getString(C.getColumnIndexOrThrow("Latitude"));
            Longitude = C.getString(C.getColumnIndexOrThrow("Longitude"));
            Result.add(new SaidaDB(id,Cidade,Latitude,Longitude));
        }

        Collections.reverse(Result);
        TempoDB.close();
        return Result;
    }catch(Exception e){
        e.printStackTrace();
    }
    return new ArrayList<SaidaDB>();
    }
}

class SaidaDB{
    public int id;
    public String Cidade;
    public String Latitude;
    public String Longitude;

    public SaidaDB(int id, String Cidade, String Latitude, String Longitude){
        this.id = id;
        this.Cidade = Cidade;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }
}