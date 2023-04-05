package cinema.presentation;

import cinema.business.Seat;
import cinema.business.SeatPurchase;
import cinema.business.Statistics;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generatePurchaseResponse(HttpStatus status, String message, SeatPurchase seatPurchase) {
        Map<String, Object> map = new HashMap<>();
        if (status == HttpStatus.OK) {
            map.put("token", seatPurchase.token());
            map.put("ticket", Map.of(
                    "row", seatPurchase.seat().getRow(),
                    "column", seatPurchase.seat().getCol(),
                    "price", seatPurchase.seat().getPrice()
            ));
        } else {
            map.put("error", message);
        }
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateRefundResponse(HttpStatus status, String message, Seat refundedSeat) {
        Map<String, Object> response;
        if (status == HttpStatus.OK) {
            response = Map.of("returned_ticket", Map.of(
                    "row", refundedSeat.getRow(),
                    "column", refundedSeat.getCol(),
                    "price", refundedSeat.getPrice()
            ));
        } else {
            response = Map.of("error", message);
        }
        return new ResponseEntity<>(response, status);

    }

    public static ResponseEntity<Object> generateViewStatsResponse(HttpStatus status, String message, Statistics stat) {
        if (status == HttpStatus.OK) {
            return new ResponseEntity<>(stat, status);
        } else {
            return new ResponseEntity<>(Map.of(
                    "error", message
            ), status);
        }
    }
}
