package com.example.my_spring_app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.my_spring_app.web.model.User;
import com.example.my_spring_app.web.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// ユーザー一覧を取得するメソッド
	@GetMapping(produces = {"application/json", "text/html"})
	public Object getAllUsers(Model model, HttpServletRequest request) {
	    List<User> users = userService.getAllUsers();
	    
	    // Accept: application/jsonヘッダーがある場合はJSONを返す
	    String acceptHeader = request.getHeader("Accept");
	    if (acceptHeader != null && acceptHeader.contains("application/json")) {
	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_JSON)
	                .body(users);
	    }
	    
	    // HTMLを返す場合
	    model.addAttribute("users", users);
	    return "user-list";
	}
	

	// 特定のユーザー詳細を表示
	@GetMapping(value = "/{id}", produces = { "text/html", "application/json" })
	public Object getUserById(@PathVariable String id, Model model, HttpServletRequest request) {
		User user = userService.getUserById(id);

	    // Accept: application/jsonヘッダーがある場合はJSONを返す
	    String acceptHeader = request.getHeader("Accept");
	    if (acceptHeader != null && acceptHeader.contains("application/json")) {
	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_JSON)
	                .body(user);
	    }

		model.addAttribute("user", user);
		return "user-detail";
	}
	
	
	// ユーザー編集フォームを表示（GET）
	@GetMapping(value = "/{id}/edit", produces = "text/html")
	public String showEditForm(@PathVariable String id, Model model) {
	    User user = userService.getUserById(id);
	    model.addAttribute("user", user);
	    return "user-edit";
	}

	// ユーザー作成（POST）
	@PostMapping
	public String createUser(User user) {
	    userService.createUser(user);
	    return "redirect:/users";
	}
	
	// ユーザー作成フォームを表示
	@GetMapping("/new")
	public String showCreateForm(Model model) {
	    model.addAttribute("user", new User());
	    return "user-form"; // templates/user-form.html
	}
	
//	@PutMapping(value = "/{id}", 
//	           consumes = {"application/x-www-form-urlencoded", "application/json"},
//	           produces = {"text/html", "application/json"})
//	public Object updateUser(@PathVariable String id, 
//	                        @RequestBody(required = false) User jsonUser,
//	                        User formUser,
//	                        HttpServletRequest request) {
//	    User user;
//	    
//	    // Accept: application/jsonヘッダーがある場合はJSONを返す
//	    String acceptHeader = request.getHeader("Accept");
//	    if (acceptHeader != null && acceptHeader.contains("application/json")) {
//	        user = userService.updateUser(id, jsonUser != null ? jsonUser : formUser);
//	        return ResponseEntity.ok()
//	                .contentType(MediaType.APPLICATION_JSON)
//	                .body(user);
//	    } 
//	    // フォームからのリクエストの場合
//	    else {
//	        user = userService.updateUser(id, formUser);
//	        return "redirect:/users";  // リダイレクト
//	    }
//	}
//
//	// ユーザー削除処理
//	@DeleteMapping(value = "/{id}", produces = {"text/html", "application/json"})
//	public Object deleteUser(@PathVariable String id, HttpServletRequest request) {
//	    userService.deleteUser(id);
//	    
//	    // Accept: application/jsonヘッダーがある場合はJSONを返す
//	    String acceptHeader = request.getHeader("Accept");
//	    if (acceptHeader != null && acceptHeader.contains("application/json")) {
//	        return ResponseEntity.noContent().build();  // 204 No Content
//	    } 
//	    // HTMLリクエストの場合
//	    else {
//	        return "redirect:/users";  // リダイレクト
//	    }
//	}
//	
	// ユーザー更新処理（PUT）
	@PutMapping(value = "/{id}")
	public String updateUser(@PathVariable String id, User user) {
	    userService.updateUser(id, user);
	    return "redirect:/users";
	}
	
	// ユーザー削除処理（DELETE）
	@DeleteMapping(value = "/{id}")
	public String deleteUser(@PathVariable String id) {
	    userService.deleteUser(id);
	    return "redirect:/users";
	}
	
}