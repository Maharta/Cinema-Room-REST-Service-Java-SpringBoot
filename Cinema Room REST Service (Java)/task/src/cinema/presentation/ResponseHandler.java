package cinema.presentation;

import cinema.business.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generatePurchaseResponse(HttpStatus status, String message, Seat seat) {
        Map<String, Object> map = new HashMap<>();
        if (status == HttpStatus.OK) {
            map.put("row", seat.getRow());
            map.put("column", seat.getCol());
            map.put("price", seat.getPrice());
        } else {
            map.put("error", message);
        }
        return new ResponseEntity<>(map, status);
    }
}
