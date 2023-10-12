package kr.co.fastcampus.travel.service;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;

    public Itinerary findItinerary(Long id) {
        Optional<Itinerary> response = itineraryRepository.findById(id);
        return response.orElseThrow(TravelDoesNotExistException::new);
    }

    public List<Itinerary> findItineraries(Trip trip) {
        return itineraryRepository.findByTrip(trip);
    }

    public List<Itinerary> saveItineraries(
            Trip trip,
            List<Itinerary> itineraries
    ) {
        for (int i = 0; i < itineraries.size(); i++) {
            Itinerary itinerary = itineraries.get(i);
            itinerary.setTrip(trip);
            itineraryRepository.save(itinerary);
        }
        return itineraries;
        // todo: JPA 옵션을 사용하면 위에 코드 삭제, 아래 코드 주석 해제
//        return itineraries.stream()
//                .map(itinerary -> {
//                    itinerary.setTrip(trip);
//                    return itineraryRepository.save(itinerary);
//                })
//                .collect(Collectors.toList());
    }
}
