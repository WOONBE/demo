package StudyWeb.repository;

import StudyWeb.domain.Chat;
import StudyWeb.domain.Post;
import StudyWeb.domain.StudyGroup;
import StudyWeb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> , PostRepositoryImpl {
    Optional<Chat> findChatById(Long id);
    Optional<StudyGroup> findStudyById(Long id);

    @Query("select c from Chat c")
    List<Chat> findAllChatsWithoutSorting();

    @Query("select s from StudyGroup s")
    List<StudyGroup> findAllStudiesWithoutSorting();

    Optional<List<Chat>> findAllChatsByUser(User user);
    Optional<List<StudyGroup>> findAllStudiesByUser(User user);

    Optional<List<Post>> findAllByUserId(Long id);
}