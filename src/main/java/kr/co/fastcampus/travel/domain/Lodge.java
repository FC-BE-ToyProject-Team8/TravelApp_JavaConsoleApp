package kr.co.fastcampus.travel.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Lodge {

    private String accommodation;
    private LocalDateTime checkInAt;
    private LocalDateTime checkOutAt;

    public Lodge(String accommodation,
            LocalDateTime checkInAt,
            LocalDateTime checkOutAt
    ) {
        this.accommodation = accommodation;
        this.checkInAt = checkInAt;
        this.checkOutAt = checkOutAt;
    }
}
