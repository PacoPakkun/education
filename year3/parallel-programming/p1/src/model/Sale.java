package model;

import java.time.LocalDate;
import java.util.List;

public class Sale {
    private int idShow;
    private LocalDate saleTime;
    private int soldSeatsNr;
    private List<Integer> soldSeats;
    private int sum;

    public Sale(int idShow, LocalDate saleTime, int soldSeatsNr, List<Integer> soldSeats, int sum) {
        this.idShow = idShow;
        this.saleTime = saleTime;
        this.soldSeatsNr = soldSeatsNr;
        this.soldSeats = soldSeats;
        this.sum = sum;
    }

    public int getIdShow() {
        return idShow;
    }

    public LocalDate getSaleTime() {
        return saleTime;
    }

    public int getSoldSeatsNr() {
        return soldSeatsNr;
    }

    public List<Integer> getSoldSeats() {
        return soldSeats;
    }

    public void setSoldSeatsNr(int soldSeatsNr) {
        this.soldSeatsNr = soldSeatsNr;
    }

    public void setSoldSeats(List<Integer> soldSeats) {
        this.soldSeats = soldSeats;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return idShow+", "+saleTime+", "+soldSeatsNr+"\n"+
                soldSeats+"\n"+sum;
    }
}
