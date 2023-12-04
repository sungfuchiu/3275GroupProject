package com.example.techassist.Web;

import com.example.techassist.Repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes
@Controller
@AllArgsConstructor
public class VideoCallController {
    private final HttpSession httpSession;
    @Autowired
    UserRepository userRepository;
    @GetMapping(path = "/videoCall")
    public String index(@RequestParam Long callId, Model model) {
        model.addAttribute("callId", callId);
        String username = (String) httpSession.getAttribute("userName");
        var user = userRepository.findByUsername(username).orElse(null);
        if(user.getTechnician() == null){
            model.addAttribute("homePage", "/clientHome");
        }else{
            model.addAttribute("homePage", "/technicianHome");
        }
        return "videoCall";
    }
}
