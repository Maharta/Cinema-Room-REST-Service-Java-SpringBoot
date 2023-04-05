package cinema.business;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Statistics(int currentIncome, int numberOfAvailableSeats, int numberOfPurchasedTickets) {
}
