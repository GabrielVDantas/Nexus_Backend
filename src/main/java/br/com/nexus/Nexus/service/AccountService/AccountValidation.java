package br.com.nexus.Nexus.service.AccountService;

import br.com.nexus.Nexus.entity.account.Account;
import br.com.nexus.Nexus.exception.EmailException;
import br.com.nexus.Nexus.exception.PasswordConfirmationException;
import br.com.nexus.Nexus.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountValidation {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Boolean confirmation;

    public void validateInfoToSignUp(Account account) {
        if (accountExist(account)) {
            throw new EmailException("Já existe uma conta com esse e-mail");
        }
        confirmation = account.getPassword().equals(account.getConfirmPassword());
        validatePassword(confirmation);
    }

    public void validateInfoToSignIn(Account account) {
        var existingAccount = searchAccount(account);
        if (!accountExist(account)) {
            throw new EmailException("Não existe uma conta com esse e-mail");
        }
        confirmation = passwordEncoder.matches(account.getPassword(), existingAccount.getPassword());
        validatePassword(confirmation);
    }

    private void validatePassword(boolean confirmation) {
        if (!confirmation) {
            throw new PasswordConfirmationException("Senha inválida," +
                    " verifique se a senha foi digitada corretamente");
        }
    }

    private Account searchAccount(Account account) {
        return (Account) accountRepository.findByEmail(account.getEmail());
    }

    private boolean accountExist(Account account) {
        return searchAccount(account) != null;
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
