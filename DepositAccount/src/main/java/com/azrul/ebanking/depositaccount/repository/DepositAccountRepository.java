package com.azrul.ebanking.depositaccount.repository;

import com.azrul.ebanking.depositaccount.domain.DepositAccount;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DepositAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepositAccountRepository extends JpaRepository<DepositAccount, Long> {
    
    @Query("select d from DepositAccount d where d.accountNumber = ?1")
    Optional<DepositAccount> findByAccountNumber(String accountNumber);
}
