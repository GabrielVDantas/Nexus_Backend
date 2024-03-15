package br.com.nexus.Nexus.service.AccountService;

import br.com.nexus.Nexus.DTO.LoginResponse;
import br.com.nexus.Nexus.entity.account.Account;
import br.com.nexus.Nexus.DTO.RegisterResponse;
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

    @Autowired
    private AccountValidation accountValidation;

    public RegisterResponse signUp(Account account) {

        accountValidation.validateInfoToSignUp(account);

        account.setPassword(accountValidation.encodePassword(account.getPassword()));

        var randomCode = RandomCodeGenerator.generateRandomCode(64);
        account.setVerificationCode(randomCode);
        account.setEnabled(false);
        var accountSaved = accountRepository.save(account);
        var accountResponse = new RegisterResponse
                (accountSaved.getId(), accountSaved.getName(), accountSaved.getEmail(), accountSaved.getPassword());
        mailService.sendVerificationCode(account);

        return accountResponse;
    }

    public LoginResponse signIn(Account account) {

        accountValidation.validateInfoToSignIn(account);

        return new LoginResponse(
                account.getEmail(), account.getPassword()
        );
    }

    public boolean verifyCode(String verificationCode) {

        var account = accountRepository.findByVerificationCode(verificationCode);

        if (account == null || account.isEnabled()) {
            return false;
        } else {

            account.setVerificationCode(null);
            account.setEnabled(true);
            accountRepository.save(account);

            return true;
        }
    }

}
