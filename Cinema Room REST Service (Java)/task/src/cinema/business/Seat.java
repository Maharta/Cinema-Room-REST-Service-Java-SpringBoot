package cinema.business;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Seat {
    private final int row;
    @JsonProperty("column")
    private int col;
    private final int price;

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
        price = row <= 4 ? 10 : 8;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat seat)) return false;
        return getRow() == seat.getRow() && getCol() == seat.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getCol());
    }
}
