package br.com.nexus.Nexus.entity.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

         @Size(max = 100)
         @NotNull(message = "Todos os campos devem ser preenchidos")
         @NotBlank(message = "Todos os campos devem ser preenchidos") String name,

         @Email
         @Size(max = 100)
         @NotNull(message = "Todos os campos devem ser preenchidos")
         @NotBlank(message = "Todos os campos devem ser preenchidos") String email,

         @Size(max = 100)
         @NotNull(message = "Todos os campos devem ser preenchidos")
         @NotBlank(message = "Todos os campos devem ser preenchidos") String password,

         @Size(max = 100)
         @NotNull(message = "Todos os campos devem ser preenchidos")
         @NotBlank(message = "Todos os campos devem ser preenchidos") String confirmPassword) {

    public Account convertUserInfoIntoToRegister() {
        return new Account(name, email, password, confirmPassword);
    }

}
