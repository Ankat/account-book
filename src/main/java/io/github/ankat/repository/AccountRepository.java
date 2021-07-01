package io.github.ankat.repository;

import io.github.ankat.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "accountRepository")
public interface AccountRepository extends JpaRepository<Account, String> {
}
