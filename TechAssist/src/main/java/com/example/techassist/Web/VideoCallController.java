package com.example.techassist.Web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class VideoCallController {
    @GetMapping(path = "/videoCall")
    public String index() {
        return "videoCall";
    }
}
