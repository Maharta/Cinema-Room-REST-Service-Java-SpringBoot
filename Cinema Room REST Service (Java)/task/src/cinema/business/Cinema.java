package cinema.business;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Cinema {

    private final int totalRows;
    private final int totalColumns;
    private final List<Seat> availableSeats;

    public Cinema() {
        List<Seat> aList = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                aList.add(new Seat(i, j));
            }
        }
        this.availableSeats = aList;
        this.totalRows = 9;
        this.totalColumns = 9;
    }

    public void removeSeatFromList(Seat seat) {
        availableSeats.remove(seat);
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < totalRows * totalColumns; i++) {
            string.append(availableSeats.get(i)).append("\n");
        }
        return string.toString();
    }
}
