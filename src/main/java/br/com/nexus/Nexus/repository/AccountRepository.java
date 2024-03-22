package br.com.nexus.Nexus.repository;

import br.com.nexus.Nexus.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Account findByVerificationCode(String verificationCode);

}
