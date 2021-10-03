package helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NAME_DB = "DB_TASK";
    public static String TASK_TABLE = "tasks";


    public DbHelper(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TASK_TABLE
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  nome TEXT NOT NULL ); ";
        try{
            db.execSQL(sql);
            Log.i("INFO DB: ","Success...!");
        }catch (Exception ex){
            Log.i("INFO DB: ","Error : " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " + TASK_TABLE + " ;" ;
        try{
            db.execSQL(sql);
            Log.i("INFO DB: ","Update Success !");
        }catch (Exception ex){
            Log.i("INFO DB: ","Update Error : " + ex.getMessage());
        }


    }
}
