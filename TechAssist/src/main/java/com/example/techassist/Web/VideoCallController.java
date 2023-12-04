package com.example.techassist.Web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class VideoCallController {
    @GetMapping(path = "/videoCall")
    public String index(@RequestParam Long callId, Model model) {
        model.addAttribute("callId", callId);
        return "videoCall";
    }
}
