package StudyWeb.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1690126766L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final NumberPath<Long> cash = createNumber("cash", Long.class);

    public final StringPath category = createString("category");

    public final SimplePath<java.security.Timestamp> createDate = createSimple("createDate", java.security.Timestamp.class);

    public final StringPath email = createString("email");

    public final StringPath emailAuthKey = createString("emailAuthKey");

    public final BooleanPath emailConfirm = createBoolean("emailConfirm");

    public final QGoal goal;

    public final QStudyGroup group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QJoinStudy joinStudy;

    public final NumberPath<Long> mileage = createNumber("mileage", Long.class);

    public final StringPath password = createString("password");

    public final ListPath<Post, QPost> posts = this.<Post, QPost>createList("posts", Post.class, QPost.class, PathInits.DIRECT2);

    public final StringPath provider = createString("provider");

    public final StringPath providerId = createString("providerId");

    public final StringPath roles = createString("roles");

    public final ListPath<Timer, QTimer> timers = this.<Timer, QTimer>createList("timers", Timer.class, QTimer.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goal = inits.isInitialized("goal") ? new QGoal(forProperty("goal"), inits.get("goal")) : null;
        this.group = inits.isInitialized("group") ? new QStudyGroup(forProperty("group")) : null;
        this.joinStudy = inits.isInitialized("joinStudy") ? new QJoinStudy(forProperty("joinStudy"), inits.get("joinStudy")) : null;
    }

}

