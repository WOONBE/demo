package StudyWeb.domain;

import StudyWeb.status.CameraStatus;
import StudyWeb.status.CompleteStatus;
import StudyWeb.status.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "join_list")
public class JoinStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_list_id")
    private Long id;

    //private Long totalMoney;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Goal goal;

    //isCamera
    @Enumerated(EnumType.STRING)
    private CameraStatus cameraStatus;

    //isPayed
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    //isCompleted
    @Enumerated(EnumType.STRING)
    private CompleteStatus completeStatus;


    private Long mileage = user.getMileage();

    private Long cash = user.getCash();

}
