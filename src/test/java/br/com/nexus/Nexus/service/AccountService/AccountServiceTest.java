package br.com.nexus.Nexus.service.AccountService;

import br.com.nexus.Nexus.entity.account.Account;
import br.com.nexus.Nexus.repository.AccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unused")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountValidation accountValidation;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    @DisplayName("A validação das informações de registro deve ser positiva")
    void registerAccountCase1() {

        var account = newAccountToRegister();
        accountValidation.validateInformationToRegister(account);
        persistAccount(account);

        var result = accountRepository.findByEmail(account.getEmail());
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("A validação das informações de registro deve ser negativa pois já existe uma conta com o mesmo e-mail")
    void registerAccountCase2() {

        registerAccount();
        var account2 = newAccountToRegister();

        assertThrows(RuntimeException.class, () -> accountValidation.validateInformationToRegister(account2));
    }

    @Test
    @Transactional
    @DisplayName("O usuário deve conseguir fazer login")
    void validateEmailAndPasswordCase1() {

        registerAccount();
        var account2 = new Account("gvdcv9@gmail.com", "teste123");

        assertDoesNotThrow(() -> accountValidation.validateEmailAndPassword(account2));
    }

    @Test
    @Transactional
    @DisplayName("O usuário não deve conseguir fazer login")
    void validateEmailAndPasswordCase2() {

        registerAccount();
        var account2 = new Account("gvdcv99999@gmail.com", "teste123");

        assertThrows(RuntimeException.class, () -> accountValidation.validateEmailAndPassword(account2));
    }

    @Test
    @Transactional
    @DisplayName("O usuário deverá conseguir deletar sua conta")
    void deleteAccountCase1() {

        registerAccount();
        var account2 = new Account("gvdcv9@gmail.com", "teste123");
        accountValidation.validateDelete(account2);

        var result = accountRepository.findByEmail(account2.getEmail());
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("O usuário não deverá conseguir deletar sua conta")
    void deleteAccountCase2() {

        registerAccount();
        var account2 = new Account("gvdcv9@gmail.com", "noDelete123");

        assertThrows(RuntimeException.class, () -> accountValidation.validateDelete(account2));
    }

    private void registerAccount() {
        var account = newAccountToRegister();
        accountValidation.validateInformationToRegister(account);
        persistAccount(account);
    }

    private Account newAccountToRegister() {
        return new Account("Gabriel", "gvdcv9@gmail.com", "teste123");
    }

    private void persistAccount(Account account) {
        entityManager.persist(account);
        entityManager.flush();
    }
}