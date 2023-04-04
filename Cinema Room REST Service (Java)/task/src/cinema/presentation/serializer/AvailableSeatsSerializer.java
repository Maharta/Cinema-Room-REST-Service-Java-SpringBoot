package cinema.presentation.serializer;

import cinema.business.Seat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class AvailableSeatsSerializer extends JsonSerializer<List<Seat>> {

    @Override
    public void serialize(List<Seat> availableSeats, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (Seat availableSeat : availableSeats) {
            if (availableSeat.isBooked()) {
                continue;
            }
            gen.writeObject(availableSeat);
        }
        gen.writeEndArray();
    }
}
