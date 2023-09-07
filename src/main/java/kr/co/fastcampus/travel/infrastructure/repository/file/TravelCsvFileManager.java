package kr.co.fastcampus.travel.infrastructure.repository.file;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;

public class TravelCsvFileManager extends FileIoRepository {

    private static final String EXTENSION = ".csv";
    private static final String ROOT_PATH = "travel/csv";
    private static final String TRIP_LIST_FILENAME = ROOT_PATH + "/trips" + EXTENSION;
    private static final String ITINERARY_FILENAME_PREFIX = ROOT_PATH + "/trip/trip_";
    private static final String SEPARATE = ",";

    private static final String[] TRIP_LIST_COLUMN = {"id", "name", "startAt", "endAt"};
    private static final String[] ITINERARY_COLUMN = {"id", "departure", "destination",
        "departureAt", "arriveAt", "accommodation", "checkInAt", "checkOutAt"};

    public List<Trip> findAllTrip() {
        String content = readFile(TRIP_LIST_FILENAME);
        String[] lines = content.split("\n");
        if (lines.length < 2) {
            return List.of();
        }

        List<Trip> trips = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String[] csvTrip = lines[i].split(SEPARATE);
            if (csvTrip.length != TRIP_LIST_COLUMN.length) {
                throw new RuntimeException();
            }
            trips.add(parseTrip(csvTrip));
        }
        return trips;
    }

    public Optional<Trip> findByTripId(Long id) {
        return findAllTrip().stream().filter(trip -> trip.getId().equals(id)).findFirst();
    }

    public List<Itinerary> findByTrip(Trip trip) {
        String filename = ITINERARY_FILENAME_PREFIX + trip.getId() + EXTENSION;
        String content = readFile(filename);
        String[] lines = content.split("\n");
        if (lines.length < 2) {
            return List.of();
        }
        return parseItineraries(trip, lines);
    }

    public Optional<Itinerary> findByItineraryId(Long id) {
        List<Trip> trips = findAllTrip();
        for (Trip trip : trips) {
            List<Itinerary> itineraries = findByTrip(trip);
            Optional<Itinerary> findItinerary = itineraries.stream()
                    .filter(itinerary -> itinerary.getId().equals(id)).findFirst();

            if (findItinerary.isPresent()) {
                return findItinerary;
            }
        }
        return Optional.empty();
    }

    public void saveTripFile(Trip trip) {
        saveFile(TRIP_LIST_COLUMN, TRIP_LIST_FILENAME, parseCsv(trip));
    }

    public void saveItineraryFile(Itinerary itinerary) {
        if (itinerary.getTrip().getId() == null) {
            throw new RuntimeException();
        }

        String filename = ITINERARY_FILENAME_PREFIX + itinerary.getTrip().getId() + EXTENSION;
        saveFile(ITINERARY_COLUMN, filename, parseCsv(itinerary));
    }

    private void saveFile(String[] columns, String filename, String csvObject) {
        String content = getCsvContent(String.join(SEPARATE, columns), filename);
        content += "\n" + csvObject;
        writeFile(filename, content);
    }

    private String getCsvContent(String header, String filename) {
        String content = readFile(filename);
        if (content.isEmpty()) {
            return header;
        }
        return content;
    }

    private static String parseCsv(Trip trip) {
        return trip.getId() + SEPARATE + trip.getName() + SEPARATE + trip.getStartAt() + SEPARATE
                + trip.getEndAt();
    }

    private static String parseCsv(Itinerary itinerary) {
        return itinerary.getId() + SEPARATE + itinerary.getRoute().getDeparture() + SEPARATE
                + itinerary.getRoute().getDestination() + SEPARATE + itinerary.getRoute()
                .getDepartureAt() + SEPARATE + itinerary.getRoute().getArriveAt() + SEPARATE
                + itinerary.getLodge().getAccommodation() + SEPARATE + itinerary.getLodge()
                .getCheckInAt() + SEPARATE + itinerary.getLodge().getCheckOutAt();
    }

    private Trip parseTrip(String[] csvTrip) {
        return Trip.builder().id(Long.parseLong(csvTrip[0])).name(csvTrip[1])
                .startAt(LocalDate.parse(csvTrip[2])).endAt(LocalDate.parse(csvTrip[3])).build();
    }

    private Itinerary parseItinerary(String[] csvItinerary) {
        return Itinerary.builder().id(Long.parseLong(csvItinerary[0])).departure(csvItinerary[1])
                .destination(csvItinerary[2]).departureAt(LocalDateTime.parse(csvItinerary[3]))
                .arriveAt(LocalDateTime.parse(csvItinerary[4])).accommodation(csvItinerary[5])
                .checkInAt(LocalDateTime.parse(csvItinerary[6]))
                .checkOutAt(LocalDateTime.parse(csvItinerary[7])).build();
    }

    private List<Itinerary> parseItineraries(Trip trip, String[] lines) {
        List<Itinerary> itineraries = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String[] csvItinerary = lines[i].split(SEPARATE);
            if (csvItinerary.length != ITINERARY_COLUMN.length) {
                throw new RuntimeException();
            }
            Itinerary itinerary = parseItinerary(csvItinerary);
            itinerary.setTrip(trip);
            itineraries.add(itinerary);
        }
        return itineraries;
    }
}
