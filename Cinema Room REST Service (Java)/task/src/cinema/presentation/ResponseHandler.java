package cinema.presentation;

import cinema.business.SeatPurchase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generatePurchaseResponse(HttpStatus status, String message, SeatPurchase seatPurchase) {
        Map<String, Object> map = new HashMap<>();
        if (status == HttpStatus.OK) {
            map.put("token", seatPurchase.token());
            map.put("row", seatPurchase.seat().getRow());
            map.put("column", seatPurchase.seat().getCol());
            map.put("price", seatPurchase.seat().getPrice());
        } else {
            map.put("error", message);
        }
        return new ResponseEntity<>(map, status);
    }
}
