package br.com.nexus.Nexus.service.AccountService;

import br.com.nexus.Nexus.entity.account.Account;
import br.com.nexus.Nexus.entity.account.AccountResponse;
import br.com.nexus.Nexus.exception.EmailException;
import br.com.nexus.Nexus.exception.PasswordConfirmationException;
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

    @Autowired
    private MailService mailService;

    public AccountResponse signUp(Account account) {

        validateAccountInfo(account);

        var encodedPassword = passwordEncoder(account.getPassword());
        account.setPassword(encodedPassword);

        var randomCode = RandomCodeGenerator.generateRandomCode(64);
        account.setVerificationCode(randomCode);
        account.setEnabled(false);
        var accountSaved = accountRepository.save(account);
        var accountResponse = new AccountResponse
                (accountSaved.getId(), accountSaved.getName(), accountSaved.getEmail(), accountSaved.getPassword());
        mailService.sendVerificationCode(account);

        return accountResponse;
    }

    public AccountResponse signIn(Account account) {
        if (!isEmailRegistered(account)) {
            throw new EmailException("Não existe uma conta com esse e-mail");
        }
        var passwordConfirmation = isPasswordMatches
                (account.getPassword(), accountRepository.findByEmail(account.getEmail()).getPassword());
        if (!passwordConfirmation) {
            throw new PasswordConfirmationException("Verifique se a senha está correta");
        }
        var accountResponse = new AccountResponse(
                account.getId(), account.getName(), account.getEmail(), account.getPassword()
        );
        return accountResponse;
    }

    public boolean verifyCode(String verificationCode) {

        Account account = accountRepository.findByVerificationCode(verificationCode);

        if (account == null || account.isEnabled()) {
            return false;
        } else {

            account.setVerificationCode(null);
            account.setEnabled(true);
            accountRepository.save(account);

            return true;
        }
    }


    private void validateAccountInfo(Account account) {
        if (isEmailRegistered(account)) {
            throw new EmailException("Já existe uma conta com esse e-mail");
        }
        if (!isPasswordEquals(account.getPassword(), account.getConfirmPassword())) {
            throw new PasswordConfirmationException("As senhas não são as mesmas");
        }
    }

    private boolean isEmailRegistered(Account account) {
        return accountRepository.findByEmail(account.getEmail()) != null;
    }

    private boolean isPasswordEquals(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean isPasswordMatches(String password, String confirmPassword) {
        return passwordEncoder.matches(password, confirmPassword);
    }

    private String passwordEncoder(String password) {
        return passwordEncoder.encode(password);
    }
}
