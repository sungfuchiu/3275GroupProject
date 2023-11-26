package com.example.techassist.Web;

import com.example.techassist.Entities.User;
import com.example.techassist.Repositories.UserRepository;
import com.example.techassist.Utilities.ConstList;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@RequestMapping
@Controller
@SessionAttributes({"errorMessage"})
@AllArgsConstructor
public class LoginController {

    private final HttpSession httpSession;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConstList constList;

    @GetMapping(path="/")
    public String initialPage() {
        return "user/login";
    }

    @GetMapping(path = "/login")
    public String login(ModelMap model) {

        return "user/login";
    }

    @GetMapping(path="/user/login")
    public String moveToLogin() {

        return"user/login";
    }

    @GetMapping(path="/loginUser")
    public String loginUser(ModelMap model, @RequestParam("inUserName") String userName, @RequestParam("inPassword") String password) {
        userName = userName.trim();
        password = password.trim();

        var user = userRepository.findByUsernameAndPassword(userName, password).orElse(null);

        if(user == null) {
            model.addAttribute(constList.KEY_ERROR_MESSAGE, "Not found your account. Please try again. WHY");

            return "user/login";
        } else {
            httpSession.setAttribute(constList.KEY_USER_NAME, userName);
            if(user.getClient() != null) {
                return "redirect:/clientHome";
            } else {
                return "redirect:/technicianHome";
            }
        }
    }
}
