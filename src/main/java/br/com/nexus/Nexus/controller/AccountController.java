package br.com.nexus.Nexus.controller;

import br.com.nexus.Nexus.DTO.*;
import br.com.nexus.Nexus.service.AccountService.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Object> registAccount(@RequestBody RegisterRequest registerRequest) {

        var account = registerRequest.convertInformationToRegister();
        var registerResponse = accountService.registerAccount(account);

        return ResponseEntity.ok().body(registerResponse);
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code) {
        if (accountService.verifyCode(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginAccount(@RequestBody LoginRequest loginRequest) {

        var account = loginRequest.convertUserInfoIntoToLogin();
        var loginResponse = accountService.authenticateAccount(account);

        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteAccount(@RequestBody DeleteRequest deleteRequest) {

        var account = deleteRequest.convertToDelete();
        var deleteResponse = accountService.deleteAccount(account);

        return ResponseEntity.ok(deleteResponse);
    }
}
