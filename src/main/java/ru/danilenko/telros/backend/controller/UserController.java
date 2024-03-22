package ru.danilenko.telros.backend.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilenko.telros.backend.entity.User;
import ru.danilenko.telros.backend.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model){
        return "/login";
        //return login form
    }
    @GetMapping("/profile")
    public void profile(Model model){


    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") int id,
                    Model model){
        Optional<User> user = userService.getUser(id);
        if(user.isPresent()) {
            model.addAttribute("user", user.get());
            System.out.println(user.toString());
            return "done";
        }
        else
            return "exeption";

    }

    @GetMapping("/")
    public String getAll(){
        return userService.findAll().stream().map(User::toString).toString();
    }

    @GetMapping("/add")
    public void addNewForm(@ModelAttribute("user") User user){

    }
    @PostMapping("/add")
    public String addNew(@Valid @ModelAttribute("user") User user, BindingResult result){
        if(result.hasErrors()){
            return "exeption";
        }
        userService.add(user);
        return "added";
    }

    @PostMapping("/{id}/remove")
    public void remove(@PathVariable("id") int id){
//        userService.delete(id);
    }


    @GetMapping("/{id}/edit")
    public void updateForm(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.getUser(id));
    }
    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") int id,@Valid @ModelAttribute("user") User user, BindingResult result){
        if(result.hasErrors()){
            return "exeption";
        }
        userService.add(user);
        return "updated";
    }
}
