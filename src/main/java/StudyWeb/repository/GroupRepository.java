package StudyWeb.repository;

import StudyWeb.domain.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupRepository extends JpaRepository<StudyGroup, Long> {







}
