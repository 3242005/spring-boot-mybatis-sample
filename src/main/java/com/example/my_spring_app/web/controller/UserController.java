package com.example.my_spring_app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.my_spring_app.web.model.User;
import com.example.my_spring_app.web.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // ユーザー一覧を表示
    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list"; // templates/user-list.html
    }
    
    // 特定のユーザー詳細を表示
    @GetMapping("/{id}")
    public String getUserById(@PathVariable String id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-detail"; // templates/user-detail.html
    }
    
    // ユーザー作成フォームを表示
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form"; // templates/user-form.html
    }
    
    // ユーザーを作成
    @PostMapping
    public String createUser(User user) {
        userService.createUser(user);
        return "redirect:/users"; // 一覧ページにリダイレクト
    }
    
    // ユーザー編集フォームを表示
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-edit"; // templates/user-edit.html
    }
    
    // ユーザーを更新
    @PostMapping("/{id}")
    public String updateUser(@PathVariable String id, User user) {
        userService.updateUser(id, user);
        return "redirect:/users"; // 一覧ページにリダイレクト
    }
    
    // ユーザーを削除
    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "redirect:/users"; // 一覧ページにリダイレクト
    }
}