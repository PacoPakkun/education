package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Show {
    private int id;
    private LocalDate showDate;
    private String title;
    private int ticketPrice;
    private List<Integer> soldSeats;
    private int sold;

    public Show(int id, LocalDate showDate, String title, int ticketPrice, int sold) {
        this.id = id;
        this.showDate = showDate;
        this.title = title;
        this.ticketPrice = ticketPrice;
        this.sold = sold;
        soldSeats=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public String getTitle() {
        return title;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public List<Integer> getSoldSeats() {
        return soldSeats;
    }

    public int getSold() {
        return sold;
    }
    public void addSoldSum(int sum)
    {
       sold+=sum;
    }
    public void addSeats(List<Integer> seats)
    {
        soldSeats.addAll(seats);
    }

    @Override
    public String toString() {
        return id+", "+showDate+" "+title+" "+ticketPrice+"\n"+
                soldSeats+"\n"+sold;
    }
}
