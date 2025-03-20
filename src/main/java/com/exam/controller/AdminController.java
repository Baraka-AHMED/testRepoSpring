package com.exam.controller;

import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // Affiche la liste des utilisateurs avec les rôles
    @GetMapping("/manage-users")
    public String manageUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("roles", UserRole.values()); // Envoie la liste des rôles
        return "manage-users";
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        // Ajouter le username dans la vue
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "admin_dashboard"; // Correspond au fichier admin-dashboard.html
    }


    // Modifier les informations d'un utilisateur
    @PostMapping("/update-user")
    public String updateUser(
            @RequestParam Long id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam UserRole role,
            @RequestParam boolean active
    ) {
        userService.updateUserDetails(id, firstName, lastName, role, active);
        return "redirect:/admin/manage-users";
    }

    // Supprimer un utilisateur
    @GetMapping("/delete-user")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/manage-users";
    }

}
