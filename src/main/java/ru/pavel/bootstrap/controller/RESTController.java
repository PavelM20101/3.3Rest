package ru.pavel.bootstrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pavel.bootstrap.model.Role;
import ru.pavel.bootstrap.model.User;
import ru.pavel.bootstrap.service.RoleService;
import ru.pavel.bootstrap.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTController {
    private RoleService roleService;
    private UserService userService;

    @Autowired
    public RESTController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUser(id));
    }

    @PostMapping("/users")
    public ResponseEntity<User> add(@Valid @RequestBody User user, BindingResult bindingResult){
        return ResponseEntity.ok(userService.insertUser(user, bindingResult));
    }

    @PutMapping("/users")
    public ResponseEntity<User> update(@Valid @RequestBody User user, BindingResult bindingResult){
        return ResponseEntity.ok(userService.updateUser(user, bindingResult));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Iterable<Role>> getAllRoles(){
        return ResponseEntity.ok(roleService.findAllRoles());
    }
}
