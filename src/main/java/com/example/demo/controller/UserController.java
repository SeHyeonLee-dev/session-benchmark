package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username, HttpSession session) {
		User user = new User(UUID.randomUUID().toString(), username);
		session.setAttribute("user", user);
		return ResponseEntity.ok().body("login_success");
	}

	@GetMapping("/user/me")
	public ResponseEntity<?> getUser(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
		}
		return ResponseEntity.ok(user);
	}
}
