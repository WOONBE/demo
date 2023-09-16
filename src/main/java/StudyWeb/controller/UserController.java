package StudyWeb.controller;

import StudyWeb.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;



    @GetMapping("/")
    private ResponseEntity home() {
        return ResponseEntity.ok().body("home test");
    }









}
