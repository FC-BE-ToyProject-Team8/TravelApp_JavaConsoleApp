package kr.co.fastcampus.travel.domain;

public class Itinerary {
    private Long id;
    private Route route;
    private Lodge lodge;

    public Itinerary(Long id, Route route, Lodge lodge) {
        this.id = id;
        this.route = route;
        this.lodge = lodge;
    }

    public Long getId() {
        return id;
    }

    public Route getMoveInfo() {
        return route;
    }

    public Lodge getLodgeInfo() {
        return lodge;
    }
}
