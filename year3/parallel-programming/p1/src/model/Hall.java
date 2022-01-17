package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hall {
    private final int seatNr;
    private List<Show> showList;
    private List<Sale> saleList;

    public Hall(int seatNr) {
        this.seatNr = seatNr;
        showList = new ArrayList<>();
        saleList = new ArrayList<>();
        initializeShows();
    }

    private void initializeShows() {
        Show show1 = new Show(1, LocalDate.of(2021, 1, 1), "Spectacol1", 100, 0);
        Show show2 = new Show(2, LocalDate.of(2021, 1, 2), "Spectacol2", 200, 0);
        Show show3 = new Show(3, LocalDate.of(2021, 1, 3), "Spectacol3", 150, 0);
        addShow(show1);
        addShow(show2);
        addShow(show3);
    }

    public List<Integer> seatsFromShow(int showId) {
        List<Integer> busySeats = new ArrayList<>();
        for(Sale sale: saleList) {
            if(sale.getIdShow() == showId) {
                busySeats.addAll(sale.getSoldSeats());
            }
        }
        return busySeats;
    }

    public Show getTicketPriceOfShow(int showId) {
        return showList.stream().filter(a -> a.getId() == showId).collect(Collectors.toList()).get(0);
    }
    public int getSumPerShow(int showId)
    {
        return saleList.stream().filter(a->a.getIdShow() == showId).map(Sale::getSum).reduce(0, Integer::sum);
    }

    public int getSeatNr() {
        return seatNr;
    }

    public List<Show> getShowList() {
        return showList;
    }

    public List<Sale> getSaleList() {
        return saleList;
    }
    public void addShow(Show show)
    {
        showList.add(show);
    }
    public void addSale(Sale sale)
    {
        saleList.add(sale);
    }
    public void writeShows() throws IOException {
        List<String> strings = new ArrayList<>();
        for (Show show: showList)
        {
            strings.add(show.toString()+"\n");
        }
        Path file = Paths.get("shows.txt");
        Files.write(file, strings);


    }
    public void writeSales() throws IOException {
        List<String> strings = new ArrayList<>();
        for (Sale sale: saleList)
        {
            strings.add(sale.toString()+"\n");
        }
        Path file = Paths.get("sales.txt");
        Files.write(file, strings);

    }
    public void writeHall() throws IOException {
        List<String> strings = new ArrayList<>();
        strings.add(seatNr +"\n");
        StringBuilder stringBuilder= new StringBuilder();
        for (Show show: showList)
        {
            stringBuilder.append(show.getId()+" ");
        }
        strings.add(stringBuilder.toString());

        for (Show show: showList)
        {
            List<Sale> sales=saleList.stream().filter(x->x.getIdShow()==show.getId()).collect(Collectors.toList());
            stringBuilder = new StringBuilder();
            for (Sale sale: sales)
            {
                stringBuilder.append(show.getId()+","+sale.getSaleTime()+";");
            }
            strings.add(stringBuilder.toString());
        }
        Path file = Paths.get("hall.txt");
        Files.write(file, strings);
    }

}
