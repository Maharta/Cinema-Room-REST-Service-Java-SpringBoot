package cinema.business;

import java.util.UUID;

public record SeatPurchase(Seat seat, UUID token) {
}
