package socialnetwork.domain;

import java.time.LocalDateTime;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Message extends Entity<Long> {
    Utilizator from;
    List<Utilizator> to;
    String message;
    Date data;
    Long reply;

    public Message() {

    }

    public Message(Utilizator from, List<Utilizator> to, String message, Long reply) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = Date.valueOf(LocalDateTime.now().toLocalDate());
        this.reply = reply;
    }

    public Message(Utilizator from, List<Utilizator> to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = Date.valueOf(LocalDateTime.now().toLocalDate());
        this.reply = -1L;
    }

    public Message(Long id, Utilizator from, Utilizator to, String message) {
        super.setId(id);
        this.from = from;
        this.to = new ArrayList<>();
        this.to.add(to);
        this.message = message;
        this.data = Date.valueOf(LocalDateTime.now().toLocalDate());
        this.reply = -1L;
    }

    public Message(Long id, Utilizator from, List<Utilizator> to, String message, Long reply) {
        super.setId(id);
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = Date.valueOf(LocalDateTime.now().toLocalDate());
        this.reply = reply;
    }

    public Message(Long id, Utilizator from, List<Utilizator> to, String message) {
        super.setId(id);
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = Date.valueOf(LocalDateTime.now().toLocalDate());
        this.reply = (long) -1;
    }

    public Utilizator getFrom() {
        return from;
    }

    public List<Utilizator> getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public Date getData() {
        return data;
    }

    public Long getReply() {
        return reply;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if (reply != -1) {
            return "#" + super.getId() + " \'" + message + "\' {" +
                    "from " + from.getLastName() +
                    ", to " + to +
                    ", at " + data + ", replies to #" + reply + "}";
        }
        return "#" + super.getId() + " \'" + message + "\' {" +
                "from " + from.getLastName() +
                ", to " + to +
                ", at " + data + "}";
    }
}
