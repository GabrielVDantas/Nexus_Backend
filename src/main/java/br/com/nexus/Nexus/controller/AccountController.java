package br.com.nexus.Nexus.controller;

import br.com.nexus.Nexus.entity.account.LoginRequest;
import br.com.nexus.Nexus.entity.account.RegisterRequest;
import br.com.nexus.Nexus.entity.account.AccountResponse;
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
    public ResponseEntity<AccountResponse> RegistAccount(@RequestBody @Valid RegisterRequest registerRequest) {

        var account = registerRequest.convertUserInfoIntoToRegister();
        AccountResponse accountResponse = accountService.signUp(account);

        return ResponseEntity.ok().body(accountResponse);
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
    public ResponseEntity<AccountResponse> LoginAccount(@RequestBody @Valid LoginRequest loginRequest) {

        var account = loginRequest.convertUserInfoIntoToLogin();
        AccountResponse accountResponse = accountService.signIn(account);

        return ResponseEntity.ok().body(accountResponse);
    }



}
