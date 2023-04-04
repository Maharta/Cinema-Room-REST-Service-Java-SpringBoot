package cinema.presentation;

import cinema.business.Cinema;
import cinema.business.CinemaService;
import cinema.business.Seat;
import cinema.business.SeatRequest;
import cinema.exceptions.SeatAlreadyBookedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            Seat purchased = cinemaService.purchaseSeat(seatRequest);
            System.out.println(purchased);
            return ResponseHandler.generatePurchaseResponse(HttpStatus.OK, null, purchased);
        } catch (IllegalArgumentException | SeatAlreadyBookedException e) {
            return ResponseHandler.generatePurchaseResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

}
