package lukin.recepti.dao;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import lukin.recepti.model.Jelo;


//singleton
@Database(entities = {Jelo.class}, version = 1, exportSchema = false)
public abstract class Baza extends RoomDatabase {

    public abstract DAO DAO();

    private static Baza INSTANCE;

    public static Baza getBaza(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Baza.class, "baza").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
