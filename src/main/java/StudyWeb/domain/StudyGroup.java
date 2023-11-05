package StudyWeb.domain;

import StudyWeb.status.GoalStatus;
import StudyWeb.status.GroupStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class StudyGroup extends Post {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private GroupStatus groupStatus;

    @OneToMany(mappedBy = "group")
    private List<User> users = new ArrayList<>();

    @Builder
    public StudyGroup(Long id, User user, String title,
                      String content, Long hit,
                      List<Comment> comments,
                      List<PostTag> tags, GroupStatus groupStatus) {
        this.groupStatus = groupStatus;
    }

    public void updateStatus(GroupStatus studyStatus) {
        this.groupStatus = groupStatus;
    }




}
