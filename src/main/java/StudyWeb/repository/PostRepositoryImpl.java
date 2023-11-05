package StudyWeb.repository;

import StudyWeb.domain.Chat;
import StudyWeb.domain.PostSearch;
import StudyWeb.domain.StudyGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryImpl {
    Page<StudyGroup> findAllStudiesBy(Pageable pageable, PostSearch postSearch);
    Page<Chat> findAllChatsBy(Pageable pageable, PostSearch postSearch);

}