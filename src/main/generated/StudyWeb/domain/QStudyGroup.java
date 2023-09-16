package StudyWeb.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyGroup is a Querydsl query type for StudyGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyGroup extends EntityPathBase<StudyGroup> {

    private static final long serialVersionUID = 1499903869L;

    public static final QStudyGroup studyGroup = new QStudyGroup("studyGroup");

    public final EnumPath<StudyWeb.status.GroupStatus> groupStatus = createEnum("groupStatus", StudyWeb.status.GroupStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<User, QUser> users = this.<User, QUser>createList("users", User.class, QUser.class, PathInits.DIRECT2);

    public QStudyGroup(String variable) {
        super(StudyGroup.class, forVariable(variable));
    }

    public QStudyGroup(Path<? extends StudyGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyGroup(PathMetadata metadata) {
        super(StudyGroup.class, metadata);
    }

}

