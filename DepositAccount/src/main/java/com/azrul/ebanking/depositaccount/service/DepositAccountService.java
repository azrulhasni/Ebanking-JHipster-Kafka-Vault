package com.azrul.ebanking.depositaccount.service;

import com.azrul.ebanking.depositaccount.domain.DepositAccount;
import com.azrul.ebanking.depositaccount.repository.DepositAccountRepository;
import com.azrul.ebanking.depositaccount.service.dto.DepositAccountDTO;
import com.azrul.ebanking.depositaccount.service.mapper.DepositAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DepositAccount}.
 */
@Service
@Transactional
public class DepositAccountService {

    private final Logger log = LoggerFactory.getLogger(DepositAccountService.class);

    private final DepositAccountRepository depositAccountRepository;

    private final DepositAccountMapper depositAccountMapper;

    public DepositAccountService(DepositAccountRepository depositAccountRepository, DepositAccountMapper depositAccountMapper) {
        this.depositAccountRepository = depositAccountRepository;
        this.depositAccountMapper = depositAccountMapper;
    }

    /**
     * Save a depositAccount.
     *
     * @param depositAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public DepositAccountDTO save(DepositAccountDTO depositAccountDTO) {
        log.debug("Request to save DepositAccount : {}", depositAccountDTO);
        DepositAccount depositAccount = depositAccountMapper.toEntity(depositAccountDTO);
        depositAccount = depositAccountRepository.save(depositAccount);
        return depositAccountMapper.toDto(depositAccount);
    }

    /**
     * Get all the depositAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DepositAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DepositAccounts");
        return depositAccountRepository.findAll(pageable)
            .map(depositAccountMapper::toDto);
    }


    /**
     * Get one depositAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepositAccountDTO> findOne(Long id) {
        log.debug("Request to get DepositAccount : {}", id);
        return depositAccountRepository.findById(id)
            .map(depositAccountMapper::toDto);
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
