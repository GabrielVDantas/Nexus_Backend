package br.com.nexus.Nexus.service.AccountService;

import br.com.nexus.Nexus.entity.account.Account;
import br.com.nexus.Nexus.repository.AccountRepository;
import br.com.nexus.Nexus.util.RandomCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("unused")
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private AccountValidation accountValidation;

    public void registerAccount(Account account) {
        
        accountValidation.validateInformationToRegister(account);

        var randomCode = RandomCodeGenerator.generateRandomCode(64);
        account.setVerificationCode(randomCode);
        account.setEnabled(false);
        accountRepository.save(account);
        mailService.sendVerificationCode(account);
    }

    public void authenticateAccount(Account account) {

        accountValidation.validateEmailAndPassword(account);
    }

    public void deleteAccount(Account account) {

        accountValidation.validateDelete(account);
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
