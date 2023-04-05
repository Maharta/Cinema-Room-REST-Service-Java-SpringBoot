package cinema.presentation;

import cinema.business.*;
import cinema.exceptions.InvalidRefundTokenException;
import cinema.exceptions.SeatAlreadyBookedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/seats")
    public Cinema getCinemaStatus() {
        return cinemaService.getCinemaStatus();
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseSeat(@RequestBody SeatRequest seatRequest) {
        try {
            SeatPurchase seatPurchase = cinemaService.purchaseSeat(seatRequest);
            return ResponseHandler.generatePurchaseResponse(HttpStatus.OK, null, seatPurchase);
        } catch (IllegalArgumentException | SeatAlreadyBookedException e) {
            return ResponseHandler.generatePurchaseResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<Object> refundPurchasedSeat(@RequestBody Map<String, UUID> tokenMap) {
        try {
            Seat refundedSeat = cinemaService.refundPurchasedSeat(tokenMap);
            return ResponseHandler.generateRefundResponse(HttpStatus.OK, null, refundedSeat);
        } catch (IllegalArgumentException | InvalidRefundTokenException e) {
            return ResponseHandler.generateRefundResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }

    }

    /**
     * Shouldn't this be a get request? eh, whatever, lets just do what the task tell us to do.
     */
    @PostMapping("/stats")
    public ResponseEntity<Object> viewStatsForAdmin(@RequestParam(required = false) String password) {
        if (password == null || !password.equals("super_secret")) {
            return ResponseHandler.generateViewStatsResponse(HttpStatus.UNAUTHORIZED, "The password is wrong!", null);
        }

        Statistics statForAdmin = cinemaService.getAdminStatistics();
        return ResponseHandler.generateViewStatsResponse(HttpStatus.OK, null, statForAdmin);


    }


}
