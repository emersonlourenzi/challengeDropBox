package com.challange.contract.v1.user;

import com.challange.contract.v1.user.model.request.UserRequest;
import com.challange.contract.v1.user.model.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public UserResponse create(@RequestBody UserRequest user) throws Exception {
        return facade.create(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) throws Exception {
        facade.deleteById(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable String id, @RequestBody @Valid UserRequest user) {
        return facade.update(id, user);
    }

    @GetMapping("/teste")
    public String teste() {
        return "TESTANDO ESSA BAGAÇA . . . FUNCIONOU ATÉ QUE ENFIM . . . PIIIIIIIII . . .";
    }
}
