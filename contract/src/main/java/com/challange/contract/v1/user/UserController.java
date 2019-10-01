package com.challange.contract.v1.user;

import com.challange.contract.v1.user.model.request.UserRequest;
import com.challange.contract.v1.user.model.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/users")
@RestController
@AllArgsConstructor
public class UserController {

    private UserContractFacade facade;

    @GetMapping
    public List<UserResponse> findAll() {
        return facade.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable String id) throws Exception {
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
