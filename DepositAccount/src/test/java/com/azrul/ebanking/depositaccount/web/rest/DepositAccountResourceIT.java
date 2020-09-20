package com.azrul.ebanking.depositaccount.web.rest;

import com.azrul.ebanking.depositaccount.DepositAccountApp;
import com.azrul.ebanking.depositaccount.domain.DepositAccount;
import com.azrul.ebanking.depositaccount.repository.DepositAccountRepository;
import com.azrul.ebanking.depositaccount.service.DepositAccountService;
import com.azrul.ebanking.depositaccount.service.dto.DepositAccountDTO;
import com.azrul.ebanking.depositaccount.service.mapper.DepositAccountMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.azrul.ebanking.depositaccount.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DepositAccountResource} REST controller.
 */
@SpringBootTest(classes = DepositAccountApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DepositAccountResourceIT {

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_OPENING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OPENING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_LAST_TRANSACTION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_TRANSACTION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private DepositAccountRepository depositAccountRepository;

    @Autowired
    private DepositAccountMapper depositAccountMapper;

    @Autowired
    private DepositAccountService depositAccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepositAccountMockMvc;

    private DepositAccount depositAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepositAccount createEntity(EntityManager em) {
        DepositAccount depositAccount = new DepositAccount()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .productId(DEFAULT_PRODUCT_ID)
            .openingDate(DEFAULT_OPENING_DATE)
            .status(DEFAULT_STATUS)
            .balance(DEFAULT_BALANCE)
            .lastTransactionDate(DEFAULT_LAST_TRANSACTION_DATE);
        return depositAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepositAccount createUpdatedEntity(EntityManager em) {
        DepositAccount depositAccount = new DepositAccount()
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .productId(UPDATED_PRODUCT_ID)
            .openingDate(UPDATED_OPENING_DATE)
            .status(UPDATED_STATUS)
            .balance(UPDATED_BALANCE)
            .lastTransactionDate(UPDATED_LAST_TRANSACTION_DATE);
        return depositAccount;
    }

    @BeforeEach
    public void initTest() {
        depositAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepositAccount() throws Exception {
        int databaseSizeBeforeCreate = depositAccountRepository.findAll().size();
        // Create the DepositAccount
        DepositAccountDTO depositAccountDTO = depositAccountMapper.toDto(depositAccount);
        restDepositAccountMockMvc.perform(post("/api/deposit-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depositAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the DepositAccount in the database
        List<DepositAccount> depositAccountList = depositAccountRepository.findAll();
        assertThat(depositAccountList).hasSize(databaseSizeBeforeCreate + 1);
        DepositAccount testDepositAccount = depositAccountList.get(depositAccountList.size() - 1);
        assertThat(testDepositAccount.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testDepositAccount.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testDepositAccount.getOpeningDate()).isEqualTo(DEFAULT_OPENING_DATE);
        assertThat(testDepositAccount.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDepositAccount.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testDepositAccount.getLastTransactionDate()).isEqualTo(DEFAULT_LAST_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void createDepositAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depositAccountRepository.findAll().size();

        // Create the DepositAccount with an existing ID
        depositAccount.setId(1L);
        DepositAccountDTO depositAccountDTO = depositAccountMapper.toDto(depositAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepositAccountMockMvc.perform(post("/api/deposit-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depositAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepositAccount in the database
        List<DepositAccount> depositAccountList = depositAccountRepository.findAll();
        assertThat(depositAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDepositAccounts() throws Exception {
        // Initialize the database
        depositAccountRepository.saveAndFlush(depositAccount);

        // Get all the depositAccountList
        restDepositAccountMockMvc.perform(get("/api/deposit-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depositAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].openingDate").value(hasItem(sameInstant(DEFAULT_OPENING_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].lastTransactionDate").value(hasItem(sameInstant(DEFAULT_LAST_TRANSACTION_DATE))));
    }
    
    @Test
    @Transactional
    public void getDepositAccount() throws Exception {
        // Initialize the database
        depositAccountRepository.saveAndFlush(depositAccount);

        // Get the depositAccount
        restDepositAccountMockMvc.perform(get("/api/deposit-accounts/{id}", depositAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(depositAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.openingDate").value(sameInstant(DEFAULT_OPENING_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()))
            .andExpect(jsonPath("$.lastTransactionDate").value(sameInstant(DEFAULT_LAST_TRANSACTION_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingDepositAccount() throws Exception {
        // Get the depositAccount
        restDepositAccountMockMvc.perform(get("/api/deposit-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepositAccount() throws Exception {
        // Initialize the database
        depositAccountRepository.saveAndFlush(depositAccount);

        int databaseSizeBeforeUpdate = depositAccountRepository.findAll().size();

        // Update the depositAccount
        DepositAccount updatedDepositAccount = depositAccountRepository.findById(depositAccount.getId()).get();
        // Disconnect from session so that the updates on updatedDepositAccount are not directly saved in db
        em.detach(updatedDepositAccount);
        updatedDepositAccount
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .productId(UPDATED_PRODUCT_ID)
            .openingDate(UPDATED_OPENING_DATE)
            .status(UPDATED_STATUS)
            .balance(UPDATED_BALANCE)
            .lastTransactionDate(UPDATED_LAST_TRANSACTION_DATE);
        DepositAccountDTO depositAccountDTO = depositAccountMapper.toDto(updatedDepositAccount);

        restDepositAccountMockMvc.perform(put("/api/deposit-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depositAccountDTO)))
            .andExpect(status().isOk());

        // Validate the DepositAccount in the database
        List<DepositAccount> depositAccountList = depositAccountRepository.findAll();
        assertThat(depositAccountList).hasSize(databaseSizeBeforeUpdate);
        DepositAccount testDepositAccount = depositAccountList.get(depositAccountList.size() - 1);
        assertThat(testDepositAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testDepositAccount.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testDepositAccount.getOpeningDate()).isEqualTo(UPDATED_OPENING_DATE);
        assertThat(testDepositAccount.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDepositAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testDepositAccount.getLastTransactionDate()).isEqualTo(UPDATED_LAST_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDepositAccount() throws Exception {
        int databaseSizeBeforeUpdate = depositAccountRepository.findAll().size();

        // Create the DepositAccount
        DepositAccountDTO depositAccountDTO = depositAccountMapper.toDto(depositAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepositAccountMockMvc.perform(put("/api/deposit-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depositAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepositAccount in the database
        List<DepositAccount> depositAccountList = depositAccountRepository.findAll();
        assertThat(depositAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepositAccount() throws Exception {
        // Initialize the database
        depositAccountRepository.saveAndFlush(depositAccount);

        int databaseSizeBeforeDelete = depositAccountRepository.findAll().size();

        // Delete the depositAccount
        restDepositAccountMockMvc.perform(delete("/api/deposit-accounts/{id}", depositAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DepositAccount> depositAccountList = depositAccountRepository.findAll();
        assertThat(depositAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
