package com.example.myboard.controller;

import com.example.myboard.dto.UserCreateForm;
import com.example.myboard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserAccountController{
    private final UserService userService;

    public UserAccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("signup")
    public String signup(UserCreateForm userCreateForm){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
        System.out.println(userCreateForm);
        if(bindingResult.hasErrors()){
            return "signup";
        }

        if(! userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){
            bindingResult.rejectValue("password2", "passwordIncorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "signup";
        }

        try{
            userService.createUser(userCreateForm);
        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.rejectValue("signupFailed", "이미 등록된 사용자입니다.");
            return "signup";

        }catch(Exception e){
            bindingResult.rejectValue("signupFailed", e.getMessage());
            return "signup";
        }

        userService.createUser(userCreateForm);

        return "redirect:/";

    }

}
