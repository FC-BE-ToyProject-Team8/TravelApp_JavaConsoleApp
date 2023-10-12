package kr.co.fastcampus.travel.service;

import java.util.List;
import kr.co.fastcampus.travel.common.exception.EntityNotFoundException;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.repository.TripRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripService {

    private final ItineraryService itineraryService;
    private final TripRepository tripRepository;

    public List<Trip> findAllTrips() {
        return tripRepository.findAll();
    }

    public Trip findTrip(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("잘못된 여행 번호입니다."));
    }

    public Trip saveTrip(Trip trip) {
        trip = tripRepository.save(trip);
        // todo: cascade, orphanRemoval 옵션 설정 시, 아래 코드 삭제
        itineraryService.saveItineraries(trip, trip.getItineraries());
        return trip;
    }
}
