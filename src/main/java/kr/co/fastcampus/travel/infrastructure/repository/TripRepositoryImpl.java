package kr.co.fastcampus.travel.infrastructure.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;

public class TripRepositoryImpl implements TripRepository {

    private static final String TRIP_JSON_PATH = "travel/json/trips.json";

    private static long SEQUENCE_NUMBER = initSequence();

    private final ObjectMapper objectMapper;

    public TripRepositoryImpl() {
        createFile();
        objectMapper = new ObjectMapper();
    }

    @Override
    public List<Trip> findAll(FileType fileType) {
        return null;
    }

    @Override
    public Optional<Trip> findById(FileType fileType, Long id) {
        return Optional.empty();
    }

    @Override
    public Trip save(Trip trip) {
        trip.setId(SEQUENCE_NUMBER);
        ObjectNode jsonTrip = getJsonTrip(trip);
        ArrayNode jsonFindTrips = readFile();
        jsonFindTrips.add(jsonTrip);
        writeFile(jsonFindTrips);

        SEQUENCE_NUMBER++;
        return trip;
    }

    public static long initSequence() {
        // todo: Application 시작 시, 마지막 Sequence로 초기화 로직 필요
        return 1L;
    }

    private void createFile() {
        File file = new File(TRIP_JSON_PATH);
        if (!file.exists()) {
            try {
                if (!file.getParentFile().mkdirs()) {
                    throw new RuntimeException(TRIP_JSON_PATH + " 폴더 생성 실패");
                }

                if (!file.createNewFile()) {
                    throw new RuntimeException(TRIP_JSON_PATH + " 파일 생성 실패");
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private ObjectNode getJsonTrip(Trip trip) {
        ObjectNode jsonTrip = objectMapper.createObjectNode();
        jsonTrip.put("id", trip.getId());
        jsonTrip.put("name", trip.getName());
        jsonTrip.put("startAt", trip.getStartAt().toString());
        jsonTrip.put("endAt", trip.getEndAt().toString());
        return jsonTrip;
    }

    private ArrayNode readFile() {
        try {
            JsonNode readJson = objectMapper.readTree(new File(TRIP_JSON_PATH));
            if (readJson.isArray()) {
                return (ArrayNode) readJson;
            }
        } catch (IOException ignored) {
        }
        return objectMapper.createArrayNode();
    }

    private void writeFile(ArrayNode jsonTrips) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRIP_JSON_PATH))) {
            writer.write(jsonTrips.toPrettyString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
