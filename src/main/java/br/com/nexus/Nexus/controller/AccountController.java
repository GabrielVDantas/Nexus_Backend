package br.com.nexus.Nexus.controller;

import br.com.nexus.Nexus.DTO.*;
import br.com.nexus.Nexus.service.AccountService.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Void> registAccount(@RequestBody RegisterRequest registerRequest) {

        var account = registerRequest.convertInformationToRegister();
        accountService.registerAccount(account);

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    public ResponseEntity<Void> loginAccount(@RequestBody LoginRequest loginRequest) {

        var account = loginRequest.convertUserInformationToLogin();
        accountService.authenticateAccount(account);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteAccount(@RequestBody DeleteRequest deleteRequest) {

        var account = deleteRequest.convertToDelete();
        accountService.deleteAccount(account);

        return ResponseEntity.ok().build();
    }
}
