package br.com.nexus.Nexus.service.AccountService;

import br.com.nexus.Nexus.entity.account.Account;
import br.com.nexus.Nexus.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountValidation {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void validateRegister(Account account) {

        if (searchAccountByEmail(account).isPresent()) {
            throw new RuntimeException("Já existe uma conta com esses dados");
        }
        if (!comparePassword(account)) {
            throw new RuntimeException("As senhas não batem");
        }
        setEncodePassword(account);
    }

    public Account validateEmailAndPassword(Account account) {

        var existingAccount = searchAccountByEmail(account);

        if (existingAccount.isEmpty()) {
            throw new RuntimeException("Não existe uma conta com esses dados");
        }

        var getExistingAccount = existingAccount.get();
        var comparingPassword = compareCodedPassword(account, getExistingAccount);

        if (!comparingPassword) {
            throw new RuntimeException("Senha incorreta");
        }

        return getExistingAccount;
    }

    public void validateDelete(Account account) {

        var getExistingAccount = validateEmailAndPassword(account);

        accountRepository.deleteById((getExistingAccount).getId());
    }

    private Optional<Account> searchAccountByEmail(Account account) {
        return accountRepository.findByEmail(account.getEmail());
    }

    private boolean compareCodedPassword(Account account, Account getExistingAccount) {
        return passwordEncoder.matches(account.getPassword(), getExistingAccount.getPassword());
    }

    private boolean comparePassword(Account account) {
        return account.getPassword().equals(account.getConfirmPassword());
    }

    public void setEncodePassword(Account account) {
        account.setPassword(passwordEncoder(account));
    }

    public String passwordEncoder(Account account) {
        return passwordEncoder.encode(account.getPassword());
    }
}
