package br.com.nexus.Nexus.service;

import br.com.nexus.Nexus.entity.Account;
import br.com.nexus.Nexus.exception.AccountAlreadyExistsException;
import br.com.nexus.Nexus.repository.AccountRepository;
import br.com.nexus.Nexus.util.RandomCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account signUp(Account account) {
        if (verifyIfEmailToSignUpAlreadyExists(account)) {
            throw new AccountAlreadyExistsException("Já existe uma conta com esse e-mail");
        } else {
            var encodedPassword = encodePassword(account.getPassword());
            account.setPassword(encodedPassword);

            var randomCode = RandomCodeGenerator.generateRandomCode(64);
            account.setVerificationCode(randomCode);
            account.setEnabled(false);
            var accountSaved = accountRepository.save(account);
            return accountSaved;
        }
    }

    private boolean verifyIfEmailToSignUpAlreadyExists(Account account) {
        return accountRepository.findByEmail(account.getEmail()) != null;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
