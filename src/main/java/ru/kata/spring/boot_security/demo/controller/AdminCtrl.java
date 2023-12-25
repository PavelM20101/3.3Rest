package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminCtrl {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminCtrl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String homeAdmin() {
        return "redirect:/admin/users";
    }

    @GetMapping("users")
    public String getAllUsers(Model model) {
        model.addAttribute("userSet", userService.getAllUsers());
        return "all-users";
    }

    @GetMapping("addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-info";
    }

    @PostMapping("saveUser")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("role") String roleName) {
        Role role = roleService.getRoleByName(roleName);
        Set<Role> roles = new HashSet<>(Collections.singletonList(role));
        user.setRoles(roles);
        userService.createOrUpdateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("updateUser")
    public String updateUser(@RequestParam("userId") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-info";
    }

    @PostMapping("deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
