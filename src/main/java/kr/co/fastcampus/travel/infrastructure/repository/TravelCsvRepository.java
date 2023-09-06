package kr.co.fastcampus.travel.infrastructure.repository;

import kr.co.fastcampus.travel.domain.Trip;

public class TravelCsvRepository extends FileIORepository {

    private static final String EXTENSION = ".csv";
    private static final String ROOT_PATH = "travel/csv";
    private static final String TRIP_LIST_FILENAME = ROOT_PATH + "/trips" + EXTENSION;
    private static final String TRIP_LIST_HEADER = "id,name,startAt,endAt";
    private static final String SEPARATE = ",";

    public void saveTripFile(Trip trip) {
        String content = getTripContent(TRIP_LIST_FILENAME);
        content += parserCsvTrip(trip);
        writeFile(TRIP_LIST_FILENAME, content);
    }

    private String getTripContent(String filename) {
        if (!isExist(filename)) {
            return TRIP_LIST_HEADER + "\n";
        }
        return readFile(filename) + "\n";
    }

    private static String parserCsvTrip(Trip trip) {
        return trip.getId() + SEPARATE
                + trip.getName() + SEPARATE
                + trip.getStartAt() + SEPARATE
                + trip.getEndAt();
    }
}
