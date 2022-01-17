package model;

import java.io.Serializable;
import java.util.List;

public class SaleDTO implements Serializable {
    private final int idShow;
    private final List<Integer> seats;

    public int getIdShow() {
        return idShow;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public SaleDTO(int idShow, List<Integer> seats) {
        this.idShow = idShow;
        this.seats = seats;
    }
    public void addSeat(int seat)
    {
        seats.add(seat);
    }

    @Override
    public String toString() {
        return "SaleDTO{" +
                "idShow=" + idShow +
                ", seatNr=" + seats.size() +
                ", seats=" + seats +
                '}';
    }
}
