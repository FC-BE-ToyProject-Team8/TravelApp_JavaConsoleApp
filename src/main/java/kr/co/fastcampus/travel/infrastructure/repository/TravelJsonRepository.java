package kr.co.fastcampus.travel.infrastructure.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import kr.co.fastcampus.travel.domain.Trip;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelJsonRepository extends FileIORepository {

    private static final String ROOT_PATH = "travel/json";
    private static final String TRIP_LIST_FILENAME = ROOT_PATH + "/trips";
    private static final String EXTENSION = ".json";

    private final ObjectMapper objectMapper;

    public void saveTripFile(Trip trip) {
        String filename = TRIP_LIST_FILENAME + EXTENSION;
        ArrayNode jsonTrip = getJsonTrip(filename, trip);
        writeFile(filename, jsonTrip.toPrettyString());
    }

    private ArrayNode parseJsonArray(String filename) {
        String content = readFile(filename);
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

    private ArrayNode getJsonTrip(String filename, Trip trip) {
        ArrayNode jsonTrips = parseJsonArray(filename);
        jsonTrips.add(parserJsonTrip(trip));
        return jsonTrips;
    }

    private ObjectNode parserJsonTrip(Trip trip) {
        ObjectNode jsonTrip = objectMapper.createObjectNode();
        jsonTrip.put("id", trip.getId());
        jsonTrip.put("name", trip.getName());
        jsonTrip.put("startAt", trip.getStartAt().toString());
        jsonTrip.put("endAt", trip.getEndAt().toString());
        return jsonTrip;
    }
}
