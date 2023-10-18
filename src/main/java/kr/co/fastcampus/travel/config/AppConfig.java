package kr.co.fastcampus.travel.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.co.fastcampus.travel.domain.itinerary.controller.ItineraryController;
import kr.co.fastcampus.travel.domain.itinerary.service.ItineraryLowService;
import kr.co.fastcampus.travel.domain.trip.controller.TripController;
import kr.co.fastcampus.travel.domain.itinerary.repository.ItineraryRepository;
import kr.co.fastcampus.travel.domain.itinerary.repository.ItineraryRepositoryImpl;
import kr.co.fastcampus.travel.domain.trip.repository.TripRepository;
import kr.co.fastcampus.travel.domain.trip.repository.TripRepositoryImpl;
import kr.co.fastcampus.travel.domain.file.TravelJsonFileManager;
import kr.co.fastcampus.travel.domain.itinerary.service.ItineraryService;
import kr.co.fastcampus.travel.domain.trip.service.TripLowService;
import kr.co.fastcampus.travel.domain.trip.service.TripService;
import kr.co.fastcampus.travel.view.InputView;

public class AppConfig {

    private static TripController TRAVEL_CONTROLLER;
    private static TripService TRIP_SERVICE;
    private static TripLowService TRIP_LOW_SERVICE;
    private static ItineraryController ITINERARY_CONTROLLER;
    private static ItineraryService ITINERARY_SERVICE;
    private static ItineraryLowService ITINERARY_LOW_SERVICE;
    private static TripRepository TRIP_REPOSITORY;
    private static ItineraryRepository ITINERARY_REPOSITORY;
    private static TravelJsonFileManager TRAVEL_JSON_FILE_MANAGER;
    private static ObjectMapper OBJECT_MAPPER;
    private static InputView INPUT_VIEW;

    private AppConfig() {
    }

    public static InputView inputView() {
        if (INPUT_VIEW == null) {
            INPUT_VIEW = new InputView();
        }
        return INPUT_VIEW;
    }

    public static TripController tripController() {
        if (TRAVEL_CONTROLLER == null) {
            TRAVEL_CONTROLLER = new TripController(tripService());
        }
        return TRAVEL_CONTROLLER;
    }

    public static TripService tripService() {
        if (TRIP_SERVICE == null) {
            TRIP_SERVICE = new TripService(tripLowerService(), itineraryLowerService());
        }
        return TRIP_SERVICE;
    }

    private static TripLowService tripLowerService() {
        if (TRIP_LOW_SERVICE == null) {
            TRIP_LOW_SERVICE = new TripLowService(tripRepository());
        }
        return TRIP_LOW_SERVICE;
    }

    public static ItineraryController itineraryController() {
        if (ITINERARY_CONTROLLER == null) {
            ITINERARY_CONTROLLER = new ItineraryController(itineraryService());
        }
        return ITINERARY_CONTROLLER;
    }

    private static ItineraryLowService itineraryLowerService() {
        if (ITINERARY_LOW_SERVICE == null) {
            ITINERARY_LOW_SERVICE = new ItineraryLowService(itineraryRepository());
        }
        return ITINERARY_LOW_SERVICE;
    }

    public static ItineraryService itineraryService() {
        if (ITINERARY_SERVICE == null) {
            ITINERARY_SERVICE = new ItineraryService(itineraryLowerService(), tripLowerService());
        }
        return ITINERARY_SERVICE;
    }

    public static ItineraryRepository itineraryRepository() {
        if (ITINERARY_REPOSITORY == null) {
            ITINERARY_REPOSITORY = new ItineraryRepositoryImpl(
                    travelJsonFileManager());
        }
        return ITINERARY_REPOSITORY;
    }

    public static TripRepository tripRepository() {
        if (TRIP_REPOSITORY == null) {
            TRIP_REPOSITORY = new TripRepositoryImpl(
                    itineraryRepository(),
                    travelJsonFileManager());
        }
        return TRIP_REPOSITORY;
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