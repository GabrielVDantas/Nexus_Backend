package br.com.nexus.Nexus.DTO;

import br.com.nexus.Nexus.entity.account.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DeleteRequest (

        @Email
        @Size(max = 100)
        @NotNull(message = "Todos os campos devem ser preenchidos")
        @NotBlank(message = "Todos os campos devem ser preenchidos") String email,

        @Size(max = 100)
        @NotNull(message = "Todos os campos devem ser preenchidos")
        @NotBlank(message = "Todos os campos devem ser preenchidos") String password) {

    public Account convertToDelete() {
        return new Account(email, password);
    }

}
