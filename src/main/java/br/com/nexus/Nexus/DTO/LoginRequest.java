package br.com.nexus.Nexus.DTO;

import br.com.nexus.Nexus.entity.account.Account;

public record LoginRequest(
        String email,
        String password) {

    public Account convertUserInformationToLogin() {
        return new Account(email, password);
    }

}
