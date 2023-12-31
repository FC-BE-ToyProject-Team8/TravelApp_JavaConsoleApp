package kr.co.fastcampus.travel.infrastructure.repository.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelJsonFileManager extends FileIoManager {

    public static final String SEQUENCE_FILE = "travel/sequence.json";
    public static final long INITIAL_SEQUENCE = 1L;

    private static final String EXTENSION = ".json";
    private static final String ROOT_PATH = "travel/json";
    private static final String TRIP_LIST_FILENAME = ROOT_PATH + "/trips" + EXTENSION;
    private static final String ITINERARY_FILENAME_PREFIX = ROOT_PATH + "/trip/trip_";

    private final ObjectMapper objectMapper;

    public long getSequence(String fieldName) {
        ObjectNode sequenceNode = (ObjectNode) parseJsonNode(readFile(SEQUENCE_FILE));
        if (sequenceNode.get(fieldName) == null) {
            sequenceNode.put(fieldName, INITIAL_SEQUENCE);
        }

        long sequence = sequenceNode.get(fieldName).asLong(1L);
        sequenceNode.put(fieldName, sequence + INITIAL_SEQUENCE);
        writeFile(SEQUENCE_FILE, sequenceNode.toPrettyString());
        return sequence;
    }

    public List<Trip> findAllTrip() {
        String content = readFile(TRIP_LIST_FILENAME);
        return parseObject(content);
    }

    public Optional<Trip> findByTripId(Long id) {
        return findAllTrip().stream()
                .filter(trip -> trip.getId().equals(id))
                .findFirst();
    }

    public List<Itinerary> findByTrip(Trip trip) {
        String filename = ITINERARY_FILENAME_PREFIX + trip.getId() + EXTENSION;
        String content = readFile(filename);
        ArrayNode jsonItineraries = parseJsonArray(content);
        return parseItineraries(trip, jsonItineraries);
    }

    public Optional<Itinerary> findByItineraryId(Long id) {
        List<Trip> trips = findAllTrip();
        return trips.stream()
                .flatMap(trip -> findByTrip(trip).stream())
                .filter(itinerary -> itinerary.getId().equals(id))
                .findFirst();
    }

    public void saveTripFile(Trip trip) {
        saveFile(TRIP_LIST_FILENAME, parseJson(trip));
    }

    public void saveItineraryFile(Itinerary itinerary) {
        if (itinerary.getTrip().getId() == null) {
            throw new RuntimeException();
        }

        String filename = ITINERARY_FILENAME_PREFIX + itinerary.getTrip().getId() + EXTENSION;
        saveFile(filename, parseJson(itinerary));
    }

    private void saveFile(String filename, ObjectNode node) {
        String content = readFile(filename);
        ArrayNode jsonArray = parseJsonArray(content);
        jsonArray.add(node);
        writeFile(filename, jsonArray.toPrettyString());
    }

    private List<Trip> parseObject(String content) {
        if (content.isEmpty()) {
            return List.of();
        }

        try {
            return objectMapper.readValue(content, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayNode parseJsonArray(String content) {
        JsonNode readJson = parseJsonNode(content);
        if (readJson != null && readJson.isArray()) {
            return (ArrayNode) readJson;
        }
        return objectMapper.createArrayNode();
    }

    private JsonNode parseJsonNode(String content) {
        if (content == null || content.trim().isEmpty()) {
            return objectMapper.createObjectNode();
        }

        try {
            return objectMapper.readTree(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 파싱 오류: " + e.getMessage(), e);
        }
    }

    private ObjectNode parseJson(Trip trip) {
        ObjectNode jsonTrip = objectMapper.createObjectNode();
        jsonTrip.put("id", trip.getId());
        jsonTrip.put("name", trip.getName());
        jsonTrip.put("startAt", trip.getStartAt().toString());
        jsonTrip.put("endAt", trip.getEndAt().toString());
        return jsonTrip;
    }

    private ObjectNode parseJson(Itinerary itinerary) {
        ObjectNode jsonItinerary = objectMapper.createObjectNode();
        jsonItinerary.put("id", itinerary.getId());

        jsonItinerary.put("departure", itinerary.getRoute().getDeparture());
        jsonItinerary.put("destination", itinerary.getRoute().getDestination());

        jsonItinerary.put(
            "departureAt",
            Optional.ofNullable(itinerary.getRoute().getDepartureAt())
                .map(LocalDateTime::toString)
                .orElse("")
        );
        jsonItinerary.put(
            "arriveAt",
            Optional.ofNullable(itinerary.getRoute().getArriveAt())
                .map(LocalDateTime::toString)
                .orElse("")
        );

        jsonItinerary.put("accommodation", itinerary.getLodge().getAccommodation());

        jsonItinerary.put(
            "checkInAt",
            Optional.ofNullable(itinerary.getLodge().getCheckInAt())
                .map(LocalDateTime::toString)
                .orElse("")
        );
        jsonItinerary.put(
            "checkOutAt",
            Optional.ofNullable(itinerary.getLodge().getCheckOutAt())
                .map(LocalDateTime::toString)
                .orElse("")
        );
        return jsonItinerary;
    }

    private Itinerary parseItinerary(JsonNode jsonItinerary) {
        return Itinerary.builder().id(jsonItinerary.get("id").asLong())
                .departure(jsonItinerary.get("departure").asText())
                .destination(jsonItinerary.get("destination").asText())
                .departureAt(parseLocalDateTime(jsonItinerary.get("departureAt").asText()))
                .arriveAt(parseLocalDateTime(jsonItinerary.get("arriveAt").asText()))
                .accommodation(jsonItinerary.get("accommodation").asText())
                .checkInAt(parseLocalDateTime(jsonItinerary.get("checkInAt").asText()))
                .checkOutAt(parseLocalDateTime(jsonItinerary.get("checkOutAt").asText())).build();
    }

    private static LocalDateTime parseLocalDateTime(String strLocalDateTime) {
        if (strLocalDateTime.isEmpty() || strLocalDateTime.equals("null")) {
            return null;
        }
        return LocalDateTime.parse(strLocalDateTime);
    }

    private List<Itinerary> parseItineraries(Trip trip, ArrayNode jsonItineraries) {
        List<Itinerary> itineraries = new ArrayList<>();
        for (JsonNode jsonItinerary : jsonItineraries) {
            Itinerary itinerary = parseItinerary(jsonItinerary);
            itinerary.setTrip(trip);
            itineraries.add(itinerary);
        }
        return itineraries;
    }
}
