package cinema.business;

import cinema.exceptions.InvalidRefundTokenException;
import cinema.exceptions.SeatAlreadyBookedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;

@Service
public class CinemaService {
    private final Cinema cinema;

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    @Autowired
    public CinemaService(Cinema cinema) {
        this.cinema = cinema;
    }

    public Cinema getCinemaStatus() {
        readLock.lock();
        try {
            return cinema;
        } finally {
            readLock.unlock();
        }
    }

    public SeatPurchase purchaseSeat(SeatRequest seatRequest) throws IllegalArgumentException {
        if (seatRequest.getRow() > cinema.getTotalRows() || seatRequest.getRow() < 1
                || seatRequest.getColumn() > cinema.getTotalColumns() || seatRequest.getColumn() < 1) {
            throw new IllegalArgumentException("The number of a row or a column is out of bounds!");
        }
        writeLock.lock();
        Optional<Seat> seatToPurchase = cinema.getAvailableSeats().stream()
                .filter(getSameSeat(seatRequest))
                .findFirst();

        try {
            if (seatToPurchase.isPresent() && !seatToPurchase.get().isBooked()) {
                Seat seat = seatToPurchase.get();
                return cinema.purchaseSeat(seat, UUID.randomUUID());
            } else {
                throw new SeatAlreadyBookedException("The ticket has been already purchased!");
            }
        } finally {
            writeLock.unlock();
        }
    }

    private static Predicate<Seat> getSameSeat(SeatRequest seatRequest) {
        return (seat) -> seat.getCol() == seatRequest.getColumn()
                && seat.getRow() == seatRequest.getRow();
    }

    /**
     * refundPurchasedSeat doesn't need a writeLock, it uses a concurrentHashmap
     * as a storage to store purchased seats which is thread safe.
     */
    public Seat refundPurchasedSeat(Map<String, UUID> tokenMap) {
        if (!tokenMap.containsKey("token")) {
            throw new IllegalArgumentException("No token provided!");
        }

        SeatPurchase seatPurchase = cinema.getSeatPurchaseMap().get(tokenMap.get("token"));

        if (seatPurchase == null) {
            throw new InvalidRefundTokenException("Wrong token!");
        }

        Seat purchasedSeat = seatPurchase.seat();
        purchasedSeat.setBooked(false);
        cinema.getSeatPurchaseMap().remove(tokenMap.get("token"));
        cinema.decreaseIncome(purchasedSeat.getPrice());
        return purchasedSeat;
    }

    public Statistics getAdminStatistics() {
        try {
            readLock.lock();
            int numberOfPurchasedTickets = cinema.getSeatPurchaseMap().size();
            int numberOfAvailableSeats = Cinema.CinemaConstant.TOTAL_SEATS - numberOfPurchasedTickets;
            return new Statistics(cinema.getIncome(), numberOfAvailableSeats, numberOfPurchasedTickets);
        } finally {
            readLock.unlock();
        }
    }

}
