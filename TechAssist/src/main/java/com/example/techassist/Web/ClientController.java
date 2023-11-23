package com.example.techassist.Web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@SessionAttributes
@Controller
@AllArgsConstructor

public class ClientController {
    @GetMapping(path = "/clientHome")
    public String clientHome() {
        return "client/clientHome";
    }

    @GetMapping(path = "/technicianInfo")
    public String technicianInfo() {
        return "client/technicianInfo";
    }

    @GetMapping(path = "/confirmation")
    public String confirmation() {
        return "client/confirmation";
    }

    @GetMapping(path = "/payment")
    public String payment() {
        return "client/payment";
    }

    @GetMapping(path = "/complete")
    public String complete() {
        return "client/complete";
    }
}
