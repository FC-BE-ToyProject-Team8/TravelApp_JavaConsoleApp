package kr.co.fastcampus.travel.infrastructure.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;

public class TravelCsvRepository extends FileIORepository {

    private static final String EXTENSION = ".csv";
    private static final String ROOT_PATH = "travel/csv";
    private static final String TRIP_LIST_FILENAME = ROOT_PATH + "/trips" + EXTENSION;
    private static final String TRIP_INFO_FILENAME_PREFIX = ROOT_PATH + "/trip/trip_";
    private static final String SEPARATE = ",";

    private static final String[] TRIP_LIST_COLUMN = {
            "id",
            "name",
            "startAt",
            "endAt"
    };
    private static final String[] TRIP_INFO_COLUMN = {
            "id",
            "departure",
            "destination",
            "departureAt",
            "arriveAt",
            "accommodation",
            "checkInAt",
            "checkOutAt"
    };

    public List<Trip> findAllTrip() {
        String content = readFile(TRIP_LIST_FILENAME);
        String[] lines = content.split("\n");
        if (lines.length < 2) {
            return List.of();
        }

        List<Trip> trips = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String[] csvTrip = lines[i].split(",");
            if (csvTrip.length != TRIP_LIST_COLUMN.length) {
                throw new RuntimeException();
            }
            trips.add(parseTrip(csvTrip));
        }
        return trips;
    }

    public void saveTripFile(Trip trip) {
        saveFile(TRIP_LIST_COLUMN, TRIP_LIST_FILENAME, parserCsv(trip));
    }

    public void saveItineraryFile(Itinerary itinerary) {
        if (itinerary.getTrip().getId() == null) {
            throw new RuntimeException();
        }

        String filename = TRIP_INFO_FILENAME_PREFIX + itinerary.getTrip().getId()
                + EXTENSION;
        saveFile(TRIP_INFO_COLUMN, filename, parserCsv(itinerary));
    }

    private Trip parseTrip(String[] csvTrip) {
        return Trip.builder()
                .id(Long.parseLong(csvTrip[0]))
                .name(csvTrip[1])
                .startAt(LocalDate.parse(csvTrip[2]))
                .endAt(LocalDate.parse(csvTrip[3]))
                .build();
    }

    private void saveFile(String[] columns, String filename, String csvObject) {
        String content = getCsvContent(String.join(SEPARATE, columns), filename);
        content +=  "\n" + csvObject;
        writeFile(filename, content);
    }

    private String getCsvContent(String header, String filename) {
        String content = readFile(filename);
        if (content.isEmpty()) {
            return header;
        }
        return content;
    }

    private static String parserCsv(Trip trip) {
        return trip.getId() + SEPARATE
                + trip.getName() + SEPARATE
                + trip.getStartAt() + SEPARATE
                + trip.getEndAt();
    }

    private static String parserCsv(Itinerary itinerary) {
        return itinerary.getId() + SEPARATE
                + itinerary.getRoute().getDeparture() + SEPARATE
                + itinerary.getRoute().getDestination() + SEPARATE
                + itinerary.getRoute().getDepartureAt() + SEPARATE
                + itinerary.getRoute().getArriveAt() + SEPARATE
                + itinerary.getLodge().getAccommodation() + SEPARATE
                + itinerary.getLodge().getCheckInAt() + SEPARATE
                + itinerary.getLodge().getCheckOutAt();
    }
}
