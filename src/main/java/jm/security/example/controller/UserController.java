package jm.security.example.controller;

import com.sun.istack.NotNull;
import jm.security.example.model.User;
import jm.security.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private final UserDetailsServiceImpl userService;

    public UserController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(value = "/user")
    public String getUserPage() {
        return "user";
    }

    @GetMapping("/admin/users")
    public String start(@NotNull ModelMap modelMap){
        modelMap.addAttribute("users", userService.allUser());
        modelMap.addAttribute("user", new User());
        return "users";
    }
}
