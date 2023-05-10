package com.arrkadique.springmessenger.controller;

import com.arrkadique.springmessenger.entity.Dialog;
import com.arrkadique.springmessenger.entity.Message;
import com.arrkadique.springmessenger.entity.Users;
import com.arrkadique.springmessenger.repository.DialogRepository;
import com.arrkadique.springmessenger.repository.MessageRepository;
import com.arrkadique.springmessenger.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MessengerController {
    private final DialogRepository dialogRepository;
    private final UsersRepository usersRepository;
    private final MessageRepository messageRepository;
    private List<String> lst;
    private List<String> lst2;

    public MessengerController(DialogRepository dialogRepository, UsersRepository usersRepository, MessageRepository messageRepository) {
        this.dialogRepository = dialogRepository;
        this.usersRepository = usersRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/messenger")
    public String Messenger(Map<String, Object> model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Users authUser = usersRepository.findByUsername(currentPrincipalName);

        lst = getColorList(authUser.getColor());
        lst2 = getColorList(authUser.getColorHover());

        List<String> lstb = getColorList(authUser.getColor());
        List<String> lstb2 = getColorList(authUser.getColorHover());

        model.put("color", authUser.getColor());
        model.put("colors", lstb);
        model.put("color_hover", authUser.getColorHover());
        model.put("colors_hover", lstb2);
        model.put("active_username", currentPrincipalName);
        model.put("messages", new Message("admin", "no one messages"));

        return "messenger";
    }

    @GetMapping("/messenger/{username}")
    public String getChatMessages(@PathVariable("username") String username, Map<String, Object> model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Dialog dialog = getDialogWith(username);
        Iterable<Message> messages = messageRepository.findAllByDialogId(dialog.getId());

        model.put("active_username", authentication.getName());
        model.put("dialog_id", dialog.getId());
        model.put("messages", messages);
        return "messenger";
    }

    @PostMapping(value = "/messenger/{username}", params = "send")
    public String sendMessage(@RequestParam String text, @RequestParam Long dialog_id,
                              Map<String, Object> model, @PathVariable String username) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users authUser = usersRepository.findByUsername(authentication.getName());

        messageRepository.save(new Message(dialog_id,authentication.getName(), text, authUser.getAvatar()));

        return getChatMessages(username, model);
    }

    @PostMapping(value = "/messenger", params = "save_color")
    public String saveColor(@RequestParam String color, @RequestParam String color_hover){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Users authUser = usersRepository.findByUsername(currentPrincipalName);
        authUser.setColor(color);
        authUser.setColorHover(color_hover);

        usersRepository.save(authUser);
        return "redirect:/messenger";
    }

    @PostMapping(value = "/messenger", params = "save_password")
    public String savePassword(@RequestParam String password){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Users authUser = usersRepository.findByUsername(currentPrincipalName);
        authUser.setPassword(password);

        usersRepository.save(authUser);
        return "redirect:/messenger";
    }

    @PostMapping(value = "/messenger", params = "save_ava")
    public String saveAvatar(
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String currentPrincipalName = authentication.getName();
                Users authUser = usersRepository.findByUsername(currentPrincipalName);
                authUser.setAvatar(fileName);
                usersRepository.save(authUser);
        return "redirect:/messenger";
    }

    @ModelAttribute("users")
    public Iterable<Users> userListAttribute(){
        return usersRepository.findAll();
    }

    @ModelAttribute("active_user_avatar")
    public String userAvatarAttribute(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users authUser = usersRepository.findByUsername(authentication.getName());
        return authUser.getAvatar();
    }

    @ModelAttribute("active_username")
    public String activeUserAttribute(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @ModelAttribute("color")
    public String colorAttribute(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users authUser = usersRepository.findByUsername(authentication.getName());
        return authUser.getColor();
    }

    @ModelAttribute("colors")
    public Iterable<String> colorsAttribute(){
        return lst;
    }

    @ModelAttribute("color_hover")
    public String colorHoverAttribute(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users authUser = usersRepository.findByUsername(authentication.getName());
        return authUser.getColorHover();
    }

    @ModelAttribute("colors_hover")
    public Iterable<String> colorsHoverAttribute(){
        return lst2;
    }

    public Dialog getDialogWith(String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Users authUser = usersRepository.findByUsername(currentPrincipalName);
        Users user = usersRepository.findByUsername(username);
        Dialog dialog = dialogRepository.findByUserId1AndUserId2(authUser.getId(), user.getId());
        if(dialog == null){
            dialog = dialogRepository.findByUserId1AndUserId2(user.getId(), authUser.getId());
        }
        return dialog;
    }


    public List<String> getColorList(String color){
        Pattern pattern = Pattern.compile("[(\\[{](.*?)[)\\]}]");
        Matcher matcher = pattern.matcher(color);
        List<String> lst = new ArrayList<>();
        while (matcher.find()) {
            lst.add(matcher.group(1));
        }

        String[] str = lst.get(0).split(",");
        List<String> lst2 = new ArrayList<>(Arrays.asList(str));


        return lst2;
    }
}
