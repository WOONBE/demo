package StudyWeb.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJoinStudy is a Querydsl query type for JoinStudy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJoinStudy extends EntityPathBase<JoinStudy> {

    private static final long serialVersionUID = 251960472L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJoinStudy joinStudy = new QJoinStudy("joinStudy");

    public final EnumPath<StudyWeb.status.CameraStatus> cameraStatus = createEnum("cameraStatus", StudyWeb.status.CameraStatus.class);

    public final NumberPath<Long> cash = createNumber("cash", Long.class);

    public final EnumPath<StudyWeb.status.CompleteStatus> completeStatus = createEnum("completeStatus", StudyWeb.status.CompleteStatus.class);

    public final QGoal goal;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> mileage = createNumber("mileage", Long.class);

    public final EnumPath<StudyWeb.status.PayStatus> payStatus = createEnum("payStatus", StudyWeb.status.PayStatus.class);

    public final QUser user;

    public QJoinStudy(String variable) {
        this(JoinStudy.class, forVariable(variable), INITS);
    }

    public QJoinStudy(Path<? extends JoinStudy> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJoinStudy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJoinStudy(PathMetadata metadata, PathInits inits) {
        this(JoinStudy.class, metadata, inits);
    }

    public QJoinStudy(Class<? extends JoinStudy> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goal = inits.isInitialized("goal") ? new QGoal(forProperty("goal"), inits.get("goal")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

