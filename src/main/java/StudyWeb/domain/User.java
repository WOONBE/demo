package StudyWeb.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    private String username;

    private String password;

    private boolean emailConfirm;

    private String emailAuthKey;

    private String category;

    //@OneToOne(fetch = FetchType.LAZY,mappedBy = "mileage")
    private Long mileage;

    //@OneToOne(fetch = FetchType.LAZY,mappedBy = "cash")
    private Long cash;

    @OneToMany(mappedBy = "user")
    private List<Timer> timers = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id" )
    private StudyGroup group;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user")
    private Goal goal;

//    @OneToMany(mappedBy = "user")
//    private List<Post> posts = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user")
    private JoinStudy joinStudy;

    public void updateUserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void changeUsername(String username) {
        this.username = username;
    }
    public void changePassword(String password) {
        this.password = password;
    }

//    @JsonBackReference
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Post> posts = new ArrayList<>();



}
