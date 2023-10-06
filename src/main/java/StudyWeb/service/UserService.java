package StudyWeb.service;

import StudyWeb.domain.User;
import StudyWeb.dto.UserDTO;
import StudyWeb.exception.UserNotFoundException;
import StudyWeb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입
    public User createUser(final UserDTO userDtO){
        User user = getByEmail(userDtO.getEmail());
        checkDupUsername(userDtO.getUsername());
        user.updateUserInfo(userDtO.getUsername(),userDtO.getPassword());
        return user;
    }


    //중복 유저 확인
    private void checkDupUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            log.error("이미 존재하는 username = {}",username);
            throw new IllegalStateException("이미 존재하는 username입니다.");
        }
    }

    public User getByEmail(final String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if (!user.isEmailConfirm()) {
            log.warn("이메일 인증이 완료되지 않은 이메일입니다.");
        }
        return user;
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
       // user.getPosts().clear();
    }

//    @Transactional
//    public UserDTO updateUsername(String before, String username) {
//        User user = userRepository.findByUsername(before).orElseThrow(UserNotFoundException::new);
//        user.changeUsername(username);
//        log.info("username was changed {} to {}",before,username);
//        return UserDTO.builder()
//                .username(username)
//                .email(user.getEmail())
//                .build();
//    }




    //전체 회원 조회
    public List<User> getUsers(){
        return userRepository.findAll();
    }





}
