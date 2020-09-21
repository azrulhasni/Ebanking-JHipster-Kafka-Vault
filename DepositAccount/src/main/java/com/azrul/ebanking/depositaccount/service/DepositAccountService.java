package com.azrul.ebanking.depositaccount.service;

import com.azrul.ebanking.depositaccount.domain.DepositAccount;
import com.azrul.ebanking.depositaccount.repository.DepositAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DepositAccount}.
 */
@Service
@Transactional
public class DepositAccountService {

    private final Logger log = LoggerFactory.getLogger(DepositAccountService.class);

    private final DepositAccountRepository depositAccountRepository;

    public DepositAccountService(DepositAccountRepository depositAccountRepository) {
        this.depositAccountRepository = depositAccountRepository;
    }

    /**
     * Save a depositAccount.
     *
     * @param depositAccount the entity to save.
     * @return the persisted entity.
     */
    public DepositAccount save(DepositAccount depositAccount) {
        log.debug("Request to save DepositAccount : {}", depositAccount);
        return depositAccountRepository.save(depositAccount);
    }
    
    //Added
    public void save(DepositAccount depositAccount1, DepositAccount depositAccount2) {
        save(depositAccount1);
        save(depositAccount2);
    }
    
    //Added
    @Transactional(readOnly = true)
    public Optional<DepositAccount> findByAccountNumber(String accountNumber) {
        log.debug("Request to get DepositAccount : {}", accountNumber);
        return depositAccountRepository.findByAccountNumber(accountNumber);
    }

    /**
     * Get all the depositAccounts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DepositAccount> findAll() {
        log.debug("Request to get all DepositAccounts");
        return depositAccountRepository.findAll();
    }


    /**
     * Get one depositAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepositAccount> findOne(Long id) {
        log.debug("Request to get DepositAccount : {}", id);
        return depositAccountRepository.findById(id);
    }

    /**
     * Delete the depositAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DepositAccount : {}", id);
        depositAccountRepository.deleteById(id);
    }
}
