package com.example.techassist.Web;

import com.example.techassist.DAO.RegisterDAO;
import com.example.techassist.Entities.ServiceField;
import com.example.techassist.Repositories.ServiceFieldRepository;
import com.example.techassist.Utilities.ConstList;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping
@Controller
@SessionAttributes({"errorMessage"})
@AllArgsConstructor
public class RegisterController {

    private final HttpSession httpSession;
    List<ServiceField> serviceFieldList;
    @Autowired
    RegisterDAO registerDAO;
    @Autowired
    ServiceFieldRepository serviceFieldRepository;
    @Autowired
    ConstList constList;

    @GetMapping(path="/user/register")
    public String initialCreateAccount(ModelMap model) {
        serviceFieldList = serviceFieldRepository.findAllByOrderByIdAsc();
        model.addAttribute(constList.KEY_SERVICE_FIELD_LIST, serviceFieldList);

        return "user/register";
    }

    @PostMapping(path="/createAccount")
    public String createAccount(ModelMap model, @RequestParam("inUserName")String userName, @RequestParam("inFullName")String fullName,
                                @RequestParam("inPassword")String password, @RequestParam(value="inJobDescription",defaultValue = "")String jobDescription,
                                @RequestParam(value="selServiceField", defaultValue="")String selServiceField, @RequestParam("userType")String userType) {
        userName = userName.trim();
        fullName = fullName.trim();
        password = password.trim();
        jobDescription = jobDescription.trim();

        int count = 0;
        int type = 0;
        int id = 0;
        if(userType.equals(constList.KEY_CLIENT)) {
            count = registerDAO.insertClient();
            if(count != 0) {
                id = registerDAO.getNewClientId();
                count = 0;
            } else {
                model.addAttribute(constList.KEY_ERROR_MESSAGE, "Failure to create account.");

                return "redirect:/user/register";
            }
        } else {
            int serviceField = Integer.parseInt(selServiceField);
            type = 1;
            count = registerDAO.insertTechnician(jobDescription, serviceField);
            if(count != 0) {
                id = registerDAO.getNewTechnicianId();
                count = 0;
            } else {
                model.addAttribute(constList.KEY_ERROR_MESSAGE, "Failure to create account.");

                return "redirect:/user/register";
            }
        }

        count = registerDAO.insertUser(userName, fullName, password, id, type);
       if(count != 0) {
           if(type == 0) {
               //client
               httpSession.setAttribute(constList.KEY_USER_NAME, userName);

               return "clientHome";
           } else {
               //technician
               httpSession.setAttribute(constList.KEY_USER_NAME, userName);

               return "technician/technicianHome";
           }
       }

       return "/";
    }
}
