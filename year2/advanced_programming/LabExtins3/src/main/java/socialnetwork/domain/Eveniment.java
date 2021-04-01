package socialnetwork.domain;

import java.time.LocalDate;
import java.sql.Date;
import java.util.Objects;

public class Eveniment extends Entity<Long> {
    Utilizator organizator;
    String titlu;
    String descriere;
    Date data;

    public Eveniment(Utilizator organizator, String titlu, String descriere, Date data) {
        this.organizator = organizator;
        this.titlu = titlu;
        this.descriere = descriere;
        this.data = data;
    }

    public Utilizator getOrganizator() {
        return organizator;
    }

    public void setOrganizator(Utilizator organizator) {
        this.organizator = organizator;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Eveniment event = (Eveniment) o;
        return Objects.equals(organizator, event.organizator) &&
                Objects.equals(titlu, event.titlu) &&
                Objects.equals(descriere, event.descriere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), organizator, titlu, descriere);
    }

    @Override
    public String toString() {
        return "" + titlu + "\n" + descriere;
    }
}
