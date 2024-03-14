package br.com.nexus.Nexus.repository;

import br.com.nexus.Nexus.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AccountRepository extends JpaRepository<Account, Long> {

    UserDetails findByEmail(String email);

    Account findByVerificationCode(String verificationCode);

}
