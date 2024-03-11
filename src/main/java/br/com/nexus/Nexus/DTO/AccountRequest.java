package br.com.nexus.Nexus.DTO;

import br.com.nexus.Nexus.entity.Account;

public record AccountRequest(String name, String email, String password) {

    public Account toModel() {
        new Account(name, email, password);

    }

}
