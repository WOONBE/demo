package StudyWeb.demo;

import StudyWeb.domain.User;
import StudyWeb.repository.UserRepository;
import StudyWeb.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PersistenceContext
    EntityManager em;

//    @Test
//    public void 회원가입() throws Exception {
////Given
//        User user = new User();
//        user.setUsername("kim");
////When
//        Long saveId = userService.join(user);
////Then
//        Assertions.assertEquals(user, userRepository.findById(saveId));
//    }


}
