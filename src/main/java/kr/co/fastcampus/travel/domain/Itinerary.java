package kr.co.fastcampus.travel.domain;

public class Itinerary {
    private Long id;
    private RouteInfo routeInfo;
    private LodgeInfo lodgeInfo;

    public Itinerary(Long id, RouteInfo routeInfo, LodgeInfo lodgeInfo) {
        this.id = id;
        this.routeInfo = routeInfo;
        this.lodgeInfo = lodgeInfo;
    }

    public Long getId() {
        return id;
    }

    public RouteInfo getMoveInfo() {
        return routeInfo;
    }

    public LodgeInfo getLodgeInfo() {
        return lodgeInfo;
    }
}
