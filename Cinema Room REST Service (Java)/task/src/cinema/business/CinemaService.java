package cinema.business;

import cinema.exceptions.SeatAlreadyBookedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

@Service
public class CinemaService {
    private final Cinema cinema;

    @Autowired
    public CinemaService(Cinema cinema) {
        this.cinema = cinema;
    }

    public Cinema getCinemaStatus() {
        return cinema;
    }

    public synchronized Seat purchaseSeat(SeatRequest seatRequest) throws IllegalArgumentException {
        if (seatRequest.getRow() > cinema.getTotalRows() || seatRequest.getRow() < 1
                || seatRequest.getColumn() > cinema.getTotalColumns() || seatRequest.getColumn() < 1) {
            throw new IllegalArgumentException("The number of a row or a column is out of bounds!");
        }

        Optional<Seat> seatToPurchase = cinema.getAvailableSeats().stream()
                .filter(getSameSeat(seatRequest))
                .findFirst();

        if (seatToPurchase.isPresent()) {
            Seat seat = seatToPurchase.get();
            cinema.removeSeatFromList(seat);
            return seat;
        } else {
            throw new SeatAlreadyBookedException("The ticket has been already purchased!");
        }

    }

    private static Predicate<Seat> getSameSeat(SeatRequest seatRequest) {
        return (seat) -> seat.getCol() == seatRequest.getColumn()
                && seat.getRow() == seatRequest.getRow();
    }


}
