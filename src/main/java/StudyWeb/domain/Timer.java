package StudyWeb.domain;

//import StudyWeb.domain.Calendar;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "timer")
public class Timer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_id")
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDate date;

    
    // 2023-06-06 calendar 관련 추가
//    private int date;


    //매핑 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //사용자 id




}
