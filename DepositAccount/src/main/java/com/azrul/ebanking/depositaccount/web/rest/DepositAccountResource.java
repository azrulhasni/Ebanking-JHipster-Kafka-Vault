package com.azrul.ebanking.depositaccount.web.rest;

import com.azrul.ebanking.depositaccount.domain.DepositAccount;
import com.azrul.ebanking.depositaccount.service.DepositAccountService;
import com.azrul.ebanking.depositaccount.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;

/**
 * REST controller for managing {@link com.azrul.ebanking.depositaccount.domain.DepositAccount}.
 */
@RestController
@RequestMapping("/api")
public class DepositAccountResource {

    private final Logger log = LoggerFactory.getLogger(DepositAccountResource.class);

    private static final String ENTITY_NAME = "depositAccountDepositAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    @Autowired
    VaultTemplate vaultTemplate;

    private final DepositAccountService depositAccountService;

    public DepositAccountResource(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
    }
    
    @PostMapping("/encrypt")
    public String encrypt(){
//        URI baseUrl = URI.create("https://127.0.0.1:8200");
//        VaultTemplate vaultTemplate = new VaultTemplate(VaultEndpoint.from(baseUrl), 
//            new TokenAuthentication("s.6HAohs85JhXqRlA2aHqLZPpx"));
        VaultTransitOperations transitOperations = vaultTemplate.opsForTransit();
        String encrypted = transitOperations.encrypt("my-encryption-key", "Hello World");
        String plain = transitOperations.decrypt("my-encryption-key", encrypted);
        return plain;
        /*VaultTransitPlain plain = new VaultTransitPlain();
        plain.setPlaintext("Sm9uLFNub3csNDExMSAxMTExIDExMTEgMTExMSxyZXN0YXVyYW50LCwxODkyMDMwOTAzCg==");
        
        VaultTransitEncrypted encrypted = webClient.post()
        .uri(baseUrl)
        .header("X-Vault-Token","s.6HAohs85JhXqRlA2aHqLZPpx")
        .body(BodyInserters.fromValue(plain))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()        
        .bodyToMono(VaultTransitEncrypted.class).single().block();
        
        System.out.println(encrypted.getCiphertext());*/
        
    }

    /**
     * {@code POST  /deposit-accounts} : Create a new depositAccount.
     *
     * @param depositAccount the depositAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depositAccount, or with status {@code 400 (Bad Request)} if the depositAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deposit-accounts")
    public ResponseEntity<DepositAccount> createDepositAccount(@RequestBody DepositAccount depositAccount) throws URISyntaxException {
        log.debug("REST request to save DepositAccount : {}", depositAccount);
        if (depositAccount.getId() != null) {
            throw new BadRequestAlertException("A new depositAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepositAccount result = depositAccountService.save(depositAccount);
        return ResponseEntity.created(new URI("/api/deposit-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deposit-accounts} : Updates an existing depositAccount.
     *
     * @param depositAccount the depositAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depositAccount,
     * or with status {@code 400 (Bad Request)} if the depositAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depositAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deposit-accounts")
    public ResponseEntity<DepositAccount> updateDepositAccount(@RequestBody DepositAccount depositAccount) throws URISyntaxException {
        log.debug("REST request to update DepositAccount : {}", depositAccount);
        if (depositAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepositAccount result = depositAccountService.save(depositAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, depositAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /deposit-accounts} : get all the depositAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of depositAccounts in body.
     */
    @GetMapping("/deposit-accounts")
    public List<DepositAccount> getAllDepositAccounts() {
        log.debug("REST request to get all DepositAccounts");
        return depositAccountService.findAll();
    }

    /**
     * {@code GET  /deposit-accounts/:id} : get the "id" depositAccount.
     *
     * @param id the id of the depositAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depositAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deposit-accounts/{id}")
    public ResponseEntity<DepositAccount> getDepositAccount(@PathVariable Long id) {
        log.debug("REST request to get DepositAccount : {}", id);
        Optional<DepositAccount> depositAccount = depositAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depositAccount);
    }

    /**
     * {@code DELETE  /deposit-accounts/:id} : delete the "id" depositAccount.
     *
     * @param id the id of the depositAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deposit-accounts/{id}")
    public ResponseEntity<Void> deleteDepositAccount(@PathVariable Long id) {
        log.debug("REST request to delete DepositAccount : {}", id);
        depositAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
