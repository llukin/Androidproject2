package lukin.recepti.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import lukin.recepti.dao.Baza;
import lukin.recepti.dao.DAO;
import lukin.recepti.model.Jelo;


public class JeloViewModel extends AndroidViewModel {

    DAO dao;

    private Jelo jelo;


    private LiveData<List<Jelo>> jela;

    public JeloViewModel(Application application) {
        super(application);
        dao = Baza.getBaza(application.getApplicationContext()).DAO();

    }

    public LiveData<List<Jelo>> dohvatiJela() {
        jela = dao.dohvatiJela();
        return jela;
    }

    public void dodajNovoJelo() {

        dao.dodajNovoJelo(jelo);
    }

    public void promjeniJelo() {

        dao.promjeniJelo(jelo);
    }

    public void obrisiJelo() {

        dao.obrisiJelo(jelo);
    }

    public Jelo getJelo() {
        return jelo;
    }

    public void setJelo(Jelo jelo) {
        this.jelo = jelo;
    }
}
