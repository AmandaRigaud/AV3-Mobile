package com.example.myprevisaotempo;


        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

public class DBInterface {
    public SQLiteDatabase TempoDB;

    public void criarBanco(){
        try{
            TempoDB = SQLiteDatabase.openOrCreateDatabase("Myprevisaotempo", null );
            TempoDB.execSQL("CREATE TABLE IF NOT EXISTS Historico( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Cidade VARCHAR," +
                    "Latitude VARCHAR," +
                    "Longitude VARCHAR)");
            TempoDB.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void InserirHistorico(String Latitude, String Longitude, String Cidade){
        TempoDB = SQLiteDatabase.openOrCreateDatabase("Myprevisaotempo",  null );
        String Add = "Insert into Historico (Cidade, Latitude, Longitude) VALUES ("+Cidade+","+Latitude+","+Longitude+")";
        TempoDB.execSQL(Add);
    }
    public void InserirHistorico(String Latitude, String Longitude){
        TempoDB = SQLiteDatabase.openOrCreateDatabase("Myprevisaotempo",  null );
        String Add = "Insert into Historico (Cidade, Latitude, Longitude) VALUES ("+"Not Specified"+","+Latitude+","+Longitude+")";
        TempoDB.execSQL(Add);
    }
}