package kr.ardent.eatgo.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeCtl {
    @GetMapping("/")
    public String hello(){
        return "Hello, World!!!!";
    }
}
