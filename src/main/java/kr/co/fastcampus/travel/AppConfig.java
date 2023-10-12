package kr.co.fastcampus.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.co.fastcampus.travel.controller.TravelController;
import kr.co.fastcampus.travel.repository.ItineraryRepository;
import kr.co.fastcampus.travel.repository.ItineraryRepositoryImpl;
import kr.co.fastcampus.travel.repository.TripRepository;
import kr.co.fastcampus.travel.repository.TripRepositoryImpl;
import kr.co.fastcampus.travel.repository.file.TravelCsvFileManager;
import kr.co.fastcampus.travel.repository.file.TravelJsonFileManager;
import kr.co.fastcampus.travel.service.ItineraryService;
import kr.co.fastcampus.travel.service.TripService;
import kr.co.fastcampus.travel.view.InputView;

public class AppConfig {

    private static TravelController TRAVEL_CONTROLLER;
    private static TripService TRIP_SERVICE;
    private static ItineraryService ITINERARY_SERVICE;
    private static TripRepository TRIP_REPOSITORY;
    private static ItineraryRepository ITINERARY_REPOSITORY;
    private static TravelJsonFileManager TRAVEL_JSON_FILE_MANAGER;
    private static ObjectMapper OBJECT_MAPPER;
    private static TravelCsvFileManager TRAVEL_CSV_FILE_MANAGER;
    private static InputView INPUT_VIEW;

    private AppConfig() {
    }

    public static InputView inputView() {
        if (INPUT_VIEW == null) {
            INPUT_VIEW = new InputView();
        }
        return INPUT_VIEW;
    }

    public static TravelController travelController() {
        if (TRAVEL_CONTROLLER == null) {
            TRAVEL_CONTROLLER = new TravelController(tripService(), itineraryService());
        }
        return TRAVEL_CONTROLLER;
    }

    public static TripService tripService() {
        if (TRIP_SERVICE == null) {
            TRIP_SERVICE = new TripService(itineraryService(), tripRepository());
        }
        return TRIP_SERVICE;
    }

    public static ItineraryService itineraryService() {
        if (ITINERARY_SERVICE == null) {
            ITINERARY_SERVICE = new ItineraryService(itineraryRepository());
        }
        return ITINERARY_SERVICE;
    }

    public static ItineraryRepository itineraryRepository() {
        if (ITINERARY_REPOSITORY == null) {
            ITINERARY_REPOSITORY = new ItineraryRepositoryImpl(
                    travelJsonFileManager(),
                    travelCsvFileManager());
        }
        return ITINERARY_REPOSITORY;
    }

    public static TripRepository tripRepository() {
        if (TRIP_REPOSITORY == null) {
            TRIP_REPOSITORY = new TripRepositoryImpl(
                    itineraryRepository(),
                    travelJsonFileManager(),
                    travelCsvFileManager());
        }
        return TRIP_REPOSITORY;
    }

    public static TravelCsvFileManager travelCsvFileManager() {
        if (TRAVEL_CSV_FILE_MANAGER == null) {
            TRAVEL_CSV_FILE_MANAGER = new TravelCsvFileManager();
        }
        return TRAVEL_CSV_FILE_MANAGER;
    }

    public static TravelJsonFileManager travelJsonFileManager() {
        if (TRAVEL_JSON_FILE_MANAGER == null) {
            TRAVEL_JSON_FILE_MANAGER = new TravelJsonFileManager(objectMapper());
        }
        return TRAVEL_JSON_FILE_MANAGER;
    }

    public static ObjectMapper objectMapper() {
        if (OBJECT_MAPPER == null) {
            OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
        }
        return OBJECT_MAPPER;
    }
}
