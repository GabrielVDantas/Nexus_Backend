package br.com.nexus.Nexus.DTO;

import br.com.nexus.Nexus.entity.account.Account;

public record RegisterRequest(
         String name,
         String email,
         String password) {

    public Account convertInformationToRegister() {
        return new Account(name, email, password);
    }

}
