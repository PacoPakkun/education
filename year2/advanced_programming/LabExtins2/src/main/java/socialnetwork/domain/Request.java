package socialnetwork.domain;

import java.sql.Date;
import java.time.LocalDateTime;


public class Request extends Entity<Tuple<Long, Long>> {

    String status;
    String nume1, prenume1, nume2, prenume2;

    public Request(Tuple tuple, String status) {
        super.setId(tuple);
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" + super.toString() + " " + status +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNume1() {
        return nume1;
    }

    public void setNume1(String nume1) {
        this.nume1 = nume1;
    }

    public String getPrenume1() {
        return prenume1;
    }

    public void setPrenume1(String prenume1) {
        this.prenume1 = prenume1;
    }

    public String getNume2() {
        return nume2;
    }

    public void setNume2(String nume2) {
        this.nume2 = nume2;
    }

    public String getPrenume2() {
        return prenume2;
    }

    public void setPrenume2(String prenume2) {
        this.prenume2 = prenume2;
    }
}
