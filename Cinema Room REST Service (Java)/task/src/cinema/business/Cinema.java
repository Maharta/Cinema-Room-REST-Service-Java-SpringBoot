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

    public Cinema() {
        List<Seat> aList = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                aList.add(new Seat(i, j));
            }
        }
        this.availableSeats = aList;
        this.seatPurchaseMap = new ConcurrentHashMap<>();
        this.totalRows = 9;
        this.totalColumns = 9;
    }

    public SeatPurchase purchaseSeat(Seat seat, UUID purchaseToken) {
        seat.setBooked(true);
        SeatPurchase seatPurchase = new SeatPurchase(seat, purchaseToken);
        addSeatToPurchasedList(seatPurchase, purchaseToken);
        return seatPurchase;
    }

    private void addSeatToPurchasedList(SeatPurchase seatPurchase, UUID purchaseToken) {
        seatPurchaseMap.put(purchaseToken, seatPurchase);
    }

    private void removeSeatFromAvailableList(Seat seat) {
        availableSeats.remove(seat);
    }

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

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < totalRows * totalColumns; i++) {
            string.append(availableSeats.get(i)).append("\n");
        }
        return string.toString();
    }
}
