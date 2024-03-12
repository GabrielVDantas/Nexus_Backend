package br.com.nexus.Nexus.entity.account;

public record AccountDTO(String name, String email, String password, String confirmPassword) {

    public Account convertUserInfoIntoToRegister() {
        return new Account(name, email, password, confirmPassword);
    }

}
