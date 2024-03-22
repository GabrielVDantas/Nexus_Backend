package br.com.nexus.Nexus.DTO;

import br.com.nexus.Nexus.entity.account.Account;

public record DeleteRequest (
        String email,
        String password) {

    public Account convertToDelete() {
        return new Account(email, password);
    }

}
