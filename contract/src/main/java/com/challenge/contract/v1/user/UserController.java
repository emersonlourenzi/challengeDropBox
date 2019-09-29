package com.challenge.contract.v1.user;

import com.challenge.contract.v1.user.model.request.UserRequest;
import com.challenge.contract.v1.user.model.response.UserResponse;
import com.challenge.impl.user.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/v1/users")
@RestController
@AllArgsConstructor
public class UserController {

    private UserContractFacade facade;

    @GetMapping
    public List<UserModel> findAll() {
        System.out.println("UserController");
        return facade.findAll();
    }

    @GetMapping("/{id}")
    public Optional<UserModel> findById(@PathVariable String id) {
        return facade.findById(id);
    }

    @PostMapping
    public UserResponse create(@RequestBody UserRequest user) {
            return facade.create(user);
    }

    @GetMapping("/teste")
    public String teste() {
        return "TESTANDO ESSA BAGAÇA . . . FUNCIONOU ATÉ QUE ENFIM . . . PIIIIIIIII . . .";
    }
}
