package socialnetwork.domain;

import java.sql.Date;
import java.time.LocalDateTime;


public class Request extends Entity<Tuple<Long, Long>> {

    String status;

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
}
