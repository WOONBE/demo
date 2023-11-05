package StudyWeb.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nimbusds.oauth2.sdk.TokenIntrospectionSuccessResponse;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Builder
@Data
@Table(name = "users",uniqueConstraints =
        {@UniqueConstraint(
                name = "USERNAME_UNIQUE",
                columnNames = {"username"})
        })
@AllArgsConstructor
@Entity
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    private String username;

    private String roles;

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

    private User(){};


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id" )
    private StudyGroup group;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user")
    private Goal goal;

    @JsonBackReference
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        this.posts.add(post);
        if (post.getUser() != this) {
            post.setUser(this);
        }
    }

//    @OneToMany(mappedBy = "user")
//    private List<Post> posts = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user")
    private JoinStudy joinStudy;

    // OAuth를 위해 구성한 추가 필드 2개(google, googleId)
    private String provider;
    private String providerId;

    @CreatedDate
    private Timestamp createDate;


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

    // ENUM으로 안하고 ,로 해서 구분해서 ROLE을 입력 -> 그걸 파싱!!
    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

//    @JsonBackReference
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Post> posts = new ArrayList<>();



}
