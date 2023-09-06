package kr.co.fastcampus.travel.infrastructure.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    private static final String ROOT_PATH = "travel/json";
    private static final String TRIP_LIST_FILENAME = ROOT_PATH + "/trips";
    private static final String TRIP_INFO_FILENAME = ROOT_PATH + "/trip/trip_";
    private static final String ITINERARY_FILENAME_PREFIX = ROOT_PATH + "/itinerary/trip_";
    private static final String ITINERARY_FILENAME_MIDDLE = "_itinerary_";
    private static final String EXTENSION = ".json";

    private final ObjectMapper objectMapper;

    public void saveTripFile(Trip trip) {
        String filename = TRIP_LIST_FILENAME + EXTENSION;
        ArrayNode jsonTrip = getJsonTrip(filename, trip);
        writeFile(filename, jsonTrip.toPrettyString());
    }

    public void saveTripInfoFile(Itinerary itinerary) {
        String filename = TRIP_INFO_FILENAME + itinerary.getTrip().getId()
                + EXTENSION;
        ObjectNode jsonTripInfo = getJsonTripInfo(filename, itinerary);
        writeFile(filename, jsonTripInfo.toPrettyString());
    }

    public void saveItineraryFile(Itinerary itinerary) {
        String filename = ITINERARY_FILENAME_PREFIX + itinerary.getTrip().getId()
                + ITINERARY_FILENAME_MIDDLE + itinerary.getId()
                + EXTENSION;
        ObjectNode jsonItinerary = parserJsonItinerary(itinerary);
        writeFile(filename, jsonItinerary.toPrettyString());
    }

    private ObjectNode parseJsonNode(String filename) {
        String content = readFile(filename);
        try {
            if (!content.isEmpty()) {
                return (ObjectNode) objectMapper.readTree(content);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return objectMapper.createObjectNode();
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

    private ObjectNode getJsonTripInfo(String filename, Itinerary itinerary) {
        ObjectNode jsonTrip = parseJsonNode(filename);
        if (jsonTrip.isEmpty()) {
            return createJsonTrip(itinerary);
        }
        ArrayNode jsonItineraries = getJsonItineraries(jsonTrip);
        jsonItineraries.add(parserJsonItinerarySummary(itinerary));
        jsonTrip.set("itineraries", jsonItineraries);
        return jsonTrip;
    }

    private ObjectNode createJsonTrip(Itinerary itinerary) {
        ObjectNode jsonTrip = parserJsonTrip(itinerary.getTrip());
        ObjectNode jsonItinerary = parserJsonItinerarySummary(itinerary);
        ArrayNode itinerariesArray = objectMapper.createArrayNode();
        itinerariesArray.add(jsonItinerary);
        jsonTrip.set("itineraries", itinerariesArray);
        return jsonTrip;
    }

    private ArrayNode getJsonItineraries(ObjectNode jsonTrip) {
        if (!jsonTrip.isEmpty()) {
            JsonNode itineraries = jsonTrip.get("itineraries");
            if (itineraries != null) {
                return (ArrayNode) itineraries;
            }
        }
        return objectMapper.createArrayNode();
    }

    private ObjectNode parserJsonTrip(Trip trip) {
        ObjectNode jsonTrip = objectMapper.createObjectNode();
        jsonTrip.put("id", trip.getId());
        jsonTrip.put("name", trip.getName());
        jsonTrip.put("startAt", trip.getStartAt().toString());
        jsonTrip.put("endAt", trip.getEndAt().toString());
        return jsonTrip;
    }

    private ObjectNode parserJsonItinerarySummary(Itinerary itinerary) {
        ObjectNode jsonItinerary = objectMapper.createObjectNode();
        jsonItinerary.put("id", itinerary.getId());
        jsonItinerary.put("departure", itinerary.getRoute().getDeparture());
        jsonItinerary.put("destination", itinerary.getRoute().getDestination());
        jsonItinerary.put("accommodation", itinerary.getLodge().getAccommodation());
        return jsonItinerary;
    }

    private ObjectNode parserJsonItinerary(Itinerary itinerary) {
        ObjectNode jsonItinerary = parserJsonItinerarySummary(itinerary);
        jsonItinerary.put("departureAt", itinerary.getRoute().getDepartureAt().toString());
        jsonItinerary.put("arriveAt", itinerary.getRoute().getArriveAt().toString());
        jsonItinerary.put("checkInAt", itinerary.getLodge().getCheckInAt().toString());
        jsonItinerary.put("checkOutAt", itinerary.getLodge().getCheckOutAt().toString());
        return jsonItinerary;
    }
}
