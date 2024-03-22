package br.com.nexus.Nexus.service.AccountService;

import br.com.nexus.Nexus.entity.account.Account;
import br.com.nexus.Nexus.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@SuppressWarnings("unused")
@Service
public class AccountValidation {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void validateInformationToRegister(Account account) {

        if (searchAccountByEmail(account).isPresent()) {
            throw new RuntimeException("Já existe uma conta com esses dados");
        }
        account.setPassword(encodeAccountPassword(account));
    }

    public Account validateEmailAndPassword(Account account) {
        var existingAccount = getExistingAccount(account);
        validatePassword(account, existingAccount);

        return existingAccount;
    }

    public void validateDelete(Account account) {

        var getExistingAccount = validateEmailAndPassword(account);

        accountRepository.deleteById((getExistingAccount).getId());
    }

    private Account getExistingAccount(Account account) {
        Optional<Account> existingAccount = searchAccountByEmail(account);

        return existingAccount.orElseThrow(() ->
                new RuntimeException("Erro, verifique se o e-mail e senha foram digitados corretamente"));
    }

    private void validatePassword(Account account, Account existingAccount) {
        if (!compareTwoCodedPasswords(account, existingAccount)) {
            throw new RuntimeException("Erro, verifique se o e-mail e senha foram digitados corretamente");
        }
    }

    private Optional<Account> searchAccountByEmail(Account account) {
        return accountRepository.findByEmail(account.getEmail());
    }

    private boolean compareTwoCodedPasswords(Account account, Account getExistingAccount) {
        return passwordEncoder.matches(account.getPassword(), getExistingAccount.getPassword());
    }

    private String encodeAccountPassword(Account account) {
        return passwordEncoder.encode(account.getPassword());
    }
}
