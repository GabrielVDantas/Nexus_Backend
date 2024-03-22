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

        var account = createNewAccount();
        accountValidation.validateInformationToRegister(account);
        entityManager.persist(account);

        var result = accountRepository.findByEmail(account.getEmail());
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("A validação das informações de registro deve ser negativa pois já existe uma conta com o mesmo e-mail")
    void registerAccountCase2() {

        var account1 = createNewAccount();
        accountValidation.validateInformationToRegister(account1);
        entityManager.persist(account1);
        entityManager.flush();

        var account2 = createNewAccount();

        assertThrows(RuntimeException.class, () -> accountValidation.validateInformationToRegister(account2));
    }

    @Test
    @Transactional
    @DisplayName("O usuário deve conseguir fazer login")
    void validateEmailAndPasswordCase1() {

    }

    @Test
    void deleteAccount() {
    }

    private Account createNewAccount() {
        return new Account("Gabriel", "gvdcv9@gmail.com", "teste123");
    }
}