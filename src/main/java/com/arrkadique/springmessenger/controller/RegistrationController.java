package com.arrkadique.springmessenger.controller;

import com.arrkadique.springmessenger.entity.Dialog;
import com.arrkadique.springmessenger.entity.Role;
import com.arrkadique.springmessenger.entity.Users;
import com.arrkadique.springmessenger.repository.DialogRepository;
import com.arrkadique.springmessenger.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class RegistrationController {
    final UsersRepository usersRepository;
    final
    DialogRepository dialogRepository;

    public RegistrationController(UsersRepository usersRepository, DialogRepository dialogRepository) {
        this.usersRepository = usersRepository;
        this.dialogRepository = dialogRepository;
    }

    @GetMapping("/registration")
    public String login(Map<String, Object> model){
        return "register";
    }

    @PostMapping(value = "/registration")
    public String RegisterUser(@RequestParam String email, @RequestParam String username,
                               @RequestParam String password, Map<String, Object> model){

        Users newUser = new Users();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setColor("rgba(209, 164, 112, 0.5)");
        newUser.setColorHover("rgba(190, 164, 112, 0.5)");
        newUser.setAvatar("ava.jpg");
        newUser.setActive(true);
        newUser.setRoles(Collections.singleton(Role.USER));

        usersRepository.save(newUser);

        Users user = usersRepository.findByUsername(username);

        List<Users> usersList = usersRepository.findAll();
        for (Users u: usersList) {
            if(user.getId() != u.getId()){
                Dialog dialog = new Dialog();
                dialog.setUserId1(user.getId());
                dialog.setUserId2(u.getId());
                dialogRepository.save(dialog);
            }
        }

        return "redirect:/login";
    }
}
