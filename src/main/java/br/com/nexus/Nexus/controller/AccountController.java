package br.com.nexus.Nexus.controller;

import br.com.nexus.Nexus.DTO.AccountRequest;
import br.com.nexus.Nexus.entity.Account;
import br.com.nexus.Nexus.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> RegistAccount(@RequestBody AccountRequest accountRequest) {

    }

}
