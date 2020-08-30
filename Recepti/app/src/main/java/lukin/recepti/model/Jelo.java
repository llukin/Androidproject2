package lukin.recepti.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;


@Entity(tableName = "jela")
public class Jelo implements Serializable {

    public Jelo() {
    }
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String naziv;
    private int vrsta;
    private String priprema;
    private String slika;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getVrsta() {
        return vrsta;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }

    public String getPriprema() {
        return priprema;
    }

    public void setPriprema(String priprema) {
        this.priprema = priprema;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }
}
