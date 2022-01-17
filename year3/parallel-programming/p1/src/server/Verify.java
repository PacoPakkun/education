package server;

import model.Hall;
import model.Show;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;

public class Verify extends TimerTask {
    private final Hall hall;
    private Files files;
    private Path path = Paths.get("verify.txt");

    public Verify(Hall hall) throws IOException {
        this.hall = hall;
        Files.writeString(path, "");
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Show show : hall.getShowList()) {
            String verified;
            synchronized (show) {
                if (checkFreeSeats(show) && checkSold(show)) verified = "corect";
                else verified = "incorect";
            }
            stringBuilder.append(LocalDateTime.now() + " ");
            stringBuilder.append(verified + " ");
            stringBuilder.append(show.getId());
            stringBuilder.append("\n");
            try {
                Files.writeString(path, stringBuilder.toString(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkFreeSeats(Show show) {
        List<Integer> showSeats = hall.seatsFromShow(show.getId());
        if (!showSeats.equals(show.getSoldSeats())) {
            return false;
        }
        return true;
    }

    private boolean checkSold(Show show) {
        if (hall.getSumPerShow(show.getId()) != show.getSold()) {
            return false;
        }
        return true;
    }

}
