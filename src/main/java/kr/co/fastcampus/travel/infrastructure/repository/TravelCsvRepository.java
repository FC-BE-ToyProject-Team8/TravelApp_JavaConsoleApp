package kr.co.fastcampus.travel.infrastructure.repository;

import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;

public class TravelCsvRepository extends FileIORepository {

    private static final String EXTENSION = ".csv";
    private static final String ROOT_PATH = "travel/csv";
    private static final String TRIP_LIST_FILENAME = ROOT_PATH + "/trips" + EXTENSION;
    private static final String TRIP_LIST_HEADER = "id,name,startAt,endAt";
    private static final String TRIP_INFO_PATH = ROOT_PATH + "/trip";
    private static final String TRIP_INFO_FILENAME_PREFIX = TRIP_INFO_PATH + "/trip_";
    private static final String TRIP_INFO_HEADER = "id,name,startAt,endAt,itinerary_id,departure,destination,accommodation";
    private static final String ITINERARY_PATH = ROOT_PATH + "/itinerary";
    private static final String ITINERARY_FILENAME_PREFIX = ITINERARY_PATH + "/trip_";
    private static final String ITINERARY_FILENAME_MIDDLE = "_itinerary_";
    private static final String ITINERARY_HEADER = "id,departure,destination,accommodation,departureAt,arriveAt,checkInAt,checkOutAt";
    private static final String SEPARATE = ",";

    public void saveTripFile(Trip trip) {
        String content = getCsvContent(TRIP_LIST_HEADER, TRIP_LIST_FILENAME);
        content += parserCsvTrip(trip);
        writeFile(TRIP_LIST_FILENAME, content);
    }

    public void saveTripInfoFile(Itinerary itinerary) {
        String filename = TRIP_INFO_FILENAME_PREFIX + itinerary.getTrip().getId()
                + EXTENSION;
        String content = getCsvContent(TRIP_INFO_HEADER, filename);
        content += parserCsvTripInfo(itinerary);
        writeFile(filename, content);
    }

    public void saveItineraryFile(Itinerary itinerary) {
        String filename = ITINERARY_FILENAME_PREFIX + itinerary.getTrip().getId()
                + ITINERARY_FILENAME_MIDDLE + itinerary.getId()
                + EXTENSION;
        String content = getCsvContent(ITINERARY_HEADER, filename);
        content += parserCsvItinerary(itinerary);
        writeFile(filename, content);
    }

    private String getCsvContent(String header, String filename) {
        if (!isExist(filename)) {
            return header + "\n";
        }
        return readFile(filename) + "\n";
    }

    private static String parserCsvTrip(Trip trip) {
        return trip.getId() + SEPARATE
                + trip.getName() + SEPARATE
                + trip.getStartAt() + SEPARATE
                + trip.getEndAt();
    }

    private static String parserCsvTripInfo(Itinerary itinerary) {
        return parserCsvTrip(itinerary.getTrip()) + SEPARATE
                + parserCsvItinerarySummary(itinerary);
    }

    private static String parserCsvItinerarySummary(Itinerary itinerary) {
        return itinerary.getId() + SEPARATE
                + itinerary.getRoute().getDeparture() + SEPARATE
                + itinerary.getRoute().getDestination() + SEPARATE
                + itinerary.getLodge().getAccommodation();
    }

    private static String parserCsvItinerary(Itinerary itinerary) {
        return parserCsvItinerarySummary(itinerary) + SEPARATE
                + itinerary.getRoute().getDepartureAt() + SEPARATE
                + itinerary.getRoute().getArriveAt() + SEPARATE
                + itinerary.getLodge().getCheckInAt() + SEPARATE
                + itinerary.getLodge().getCheckOutAt();
    }
}
