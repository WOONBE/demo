package StudyWeb.domain;

import StudyWeb.status.GoalStatus;
import StudyWeb.status.GroupStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class StudyGroup {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private GroupStatus groupStatus;

    @OneToMany(mappedBy = "group")
    private List<User> users = new ArrayList<>();


}
