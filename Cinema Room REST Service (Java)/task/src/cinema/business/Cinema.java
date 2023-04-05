package cinema.business;


import cinema.presentation.serializer.AvailableSeatsSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Component
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Cinema {

    private final int totalRows;
    private final int totalColumns;
    @JsonSerialize(using = AvailableSeatsSerializer.class)
    private final List<Seat> availableSeats;
    @JsonIgnore
    private final ConcurrentMap<UUID, SeatPurchase> seatPurchaseMap;
    @JsonIgnore
    private int income;

    public static class CinemaConstant {
        public static final int TOTAL_SEATS = 81;
        public static final int TOTAL_ROWS = 9;
        public static final int TOTAL_COLS = 9;

    }


    public Cinema() {
        List<Seat> aList = new ArrayList<>();
        for (int i = 1; i <= CinemaConstant.TOTAL_ROWS; i++) {
            for (int j = 1; j <= CinemaConstant.TOTAL_COLS; j++) {
                aList.add(new Seat(i, j));
            }
        }
        availableSeats = aList;
        seatPurchaseMap = new ConcurrentHashMap<>();
        totalRows = CinemaConstant.TOTAL_ROWS;
        totalColumns = CinemaConstant.TOTAL_COLS;
        income = 0;
    }

    public SeatPurchase purchaseSeat(Seat seat, UUID purchaseToken) {
        SeatPurchase seatPurchase = new SeatPurchase(seat, purchaseToken);
        addSeatToPurchasedList(seatPurchase, purchaseToken);
        return seatPurchase;
    }

    private void addSeatToPurchasedList(SeatPurchase seatPurchase, UUID purchaseToken) {
        seatPurchaseMap.put(purchaseToken, seatPurchase);
        seatPurchase.seat().setBooked(true);
        increaseIncome(seatPurchase.seat().getPrice());
    }

    private void increaseIncome(int amount) {
        income += amount;
    }

    public void decreaseIncome(int amount) {
        income -= amount;
    }



    /*private void updateStatisticsMap(Integer purchasedTickets, Integer newIncome, Integer newAvailableSeats) {
        statisticsMap.put(Statistics.PURCHASED_TICKETS, purchasedTickets);
        statisticsMap.put(Statistics.CURRENT_INCOME, newIncome);
        statisticsMap.put(Statistics.AVAILABLE_SEATS, newAvailableSeats);
    }*/


    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public ConcurrentMap<UUID, SeatPurchase> getSeatPurchaseMap() {
        return seatPurchaseMap;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public int getIncome() {
        return income;
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
