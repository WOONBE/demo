package StudyWeb.repository;

import StudyWeb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findById(Long id);
//    Boolean existsByEmail(String email);
//
//    Optional<User> findByEmailAuthKey(String authKey);
//    Optional<User> findById(String id);
//
    Boolean existsByUsername(String username);
//
    Optional<User> findByEmail(String email);
//
    Optional<User> findByUsername(String username);



}
