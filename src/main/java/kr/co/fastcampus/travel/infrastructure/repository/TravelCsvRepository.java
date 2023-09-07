package kr.co.fastcampus.travel.infrastructure.repository;

import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;

public class TravelCsvRepository extends FileIORepository {

    private static final String EXTENSION = ".csv";
    private static final String ROOT_PATH = "travel/csv";
    private static final String TRIP_LIST_FILENAME = ROOT_PATH + "/trips" + EXTENSION;
    private static final String TRIP_LIST_HEADER = "id,name,startAt,endAt";
    private static final String TRIP_INFO_FILENAME_PREFIX = ROOT_PATH + "/trip/trip_";
    private static final String TRIP_INFO_HEADER = "id,departure,destination,departureAt,arriveAt,accommodation,checkInAt,checkOutAt";
    private static final String SEPARATE = ",";

    public void saveTripFile(Trip trip) {
        saveFile(TRIP_LIST_HEADER, TRIP_LIST_FILENAME, parserCsv(trip));
    }

    public void saveItineraryFile(Itinerary itinerary) {
        if (itinerary.getTrip().getId() == null) {
            throw new RuntimeException();
        }

        String filename = TRIP_INFO_FILENAME_PREFIX + itinerary.getTrip().getId()
                + EXTENSION;
        saveFile(TRIP_INFO_HEADER, filename, parserCsv(itinerary));
    }

    private void saveFile(String header, String filename, String csvObject) {
        String content = getCsvContent(header, filename);
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
