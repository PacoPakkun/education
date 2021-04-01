package socialnetwork.domain;

import java.sql.Date;
import java.time.LocalDateTime;


public class Prietenie extends Entity<Tuple<Long, Long>> {

    Date date;

    public Prietenie(Tuple tuple) {
        super.setId(tuple);
        this.date = Date.valueOf(LocalDateTime.now().toLocalDate());
    }

    /**
     * @return the date when the friendship was created
     */
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "user #" + super.getId().getLeft() + " became friends with user #" + super.getId().getRight() + ", at " + date;
    }
}
