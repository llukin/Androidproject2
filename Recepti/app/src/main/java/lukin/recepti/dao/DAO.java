package lukin.recepti.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import lukin.recepti.model.Jelo;


@Dao
public interface DAO {

    @Query("select * from jela order by id")
    LiveData<List<Jelo>> dohvatiJela();

    @Insert
    void dodajNovoJelo(Jelo jelo);

    @Update
    void promjeniJelo(Jelo jelo);

    @Delete
    void obrisiJelo(Jelo jelo);


}
