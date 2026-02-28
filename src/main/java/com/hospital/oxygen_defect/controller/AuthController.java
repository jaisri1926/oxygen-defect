package com.hospital.oxygen_defect.controller;

import com.hospital.oxygen_defect.model.User;
import com.hospital.oxygen_defect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Show Login Page
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    // Process Login
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            session.setAttribute("loggedUser", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid username or password!");
            return "login";
        }
    }

    // Show Register Page
    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    // Process Registration
    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String fullName,
                                  Model model) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setFullName(fullName);
        newUser.setRole("USER");
        userRepository.save(newUser);
        return "redirect:/login";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}