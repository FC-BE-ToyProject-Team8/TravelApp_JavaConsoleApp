package kr.co.fastcampus.travel.infrastructure.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelJsonRepository extends FileIORepository {

    private static final String EXTENSION = ".json";
    private static final String ROOT_PATH = "travel/json";
    private static final String TRIP_LIST_FILENAME = ROOT_PATH + "/trips" + EXTENSION;
    private static final String ITINERARY_FILENAME_PREFIX = ROOT_PATH + "/trip/trip_";

    private final ObjectMapper objectMapper;

    public long getTripCount() {
        String content = readFile(TRIP_LIST_FILENAME);
        return parseJsonArray(content).size() + 1;
    }

    public long getItineraryCount() {
        return getFilenames(ROOT_PATH + "/itinerary").length + 1;
    }

    public void saveTripFile(Trip trip) {
        saveFile(TRIP_LIST_FILENAME, parserJson(trip));
    }

    public void saveItineraryFile(Itinerary itinerary) {
        if (itinerary.getTrip().getId() == null) {
            throw new RuntimeException();
        }

        String filename = ITINERARY_FILENAME_PREFIX + itinerary.getTrip().getId()
                + EXTENSION;
        saveFile(filename, parserJson(itinerary));
    }

    private void saveFile(String filename, ObjectNode node) {
        String content = readFile(filename);
        ArrayNode jsonArray = parseJsonArray(content);
        jsonArray.add(node);
        writeFile(filename, jsonArray.toPrettyString());
    }

    private ArrayNode parseJsonArray(String content) {
        try {
            JsonNode readJson = objectMapper.readTree(content);
            if (readJson != null && readJson.isArray()) {
                return (ArrayNode) readJson;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return objectMapper.createArrayNode();
    }

    private ObjectNode parserJson(Trip trip) {
        ObjectNode jsonTrip = objectMapper.createObjectNode();
        jsonTrip.put("id", trip.getId());
        jsonTrip.put("name", trip.getName());
        jsonTrip.put("startAt", trip.getStartAt().toString());
        jsonTrip.put("endAt", trip.getEndAt().toString());
        return jsonTrip;
    }

    private ObjectNode parserJson(Itinerary itinerary) {
        ObjectNode jsonItinerary = objectMapper.createObjectNode();
        jsonItinerary.put("id", itinerary.getId());
        jsonItinerary.put("departure", itinerary.getRoute().getDeparture());
        jsonItinerary.put("destination", itinerary.getRoute().getDestination());
        jsonItinerary.put("departureAt", itinerary.getRoute().getDepartureAt().toString());
        jsonItinerary.put("arriveAt", itinerary.getRoute().getArriveAt().toString());
        jsonItinerary.put("accommodation", itinerary.getLodge().getAccommodation());
        jsonItinerary.put("checkInAt", itinerary.getLodge().getCheckInAt().toString());
        jsonItinerary.put("checkOutAt", itinerary.getLodge().getCheckOutAt().toString());
        return jsonItinerary;
    }
}
