package com.azrul.ebanking.depositaccount.web.rest;

import com.azrul.ebanking.depositaccount.service.DepositAccountService;
import com.azrul.ebanking.depositaccount.web.rest.errors.BadRequestAlertException;
import com.azrul.ebanking.depositaccount.service.dto.DepositAccountDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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

    private final DepositAccountService depositAccountService;

    public DepositAccountResource(DepositAccountService depositAccountService) {
        this.depositAccountService = depositAccountService;
    }

    /**
     * {@code POST  /deposit-accounts} : Create a new depositAccount.
     *
     * @param depositAccountDTO the depositAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depositAccountDTO, or with status {@code 400 (Bad Request)} if the depositAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deposit-accounts")
    public ResponseEntity<DepositAccountDTO> createDepositAccount(@RequestBody DepositAccountDTO depositAccountDTO) throws URISyntaxException {
        log.debug("REST request to save DepositAccount : {}", depositAccountDTO);
        if (depositAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new depositAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepositAccountDTO result = depositAccountService.save(depositAccountDTO);
        return ResponseEntity.created(new URI("/api/deposit-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deposit-accounts} : Updates an existing depositAccount.
     *
     * @param depositAccountDTO the depositAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depositAccountDTO,
     * or with status {@code 400 (Bad Request)} if the depositAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depositAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deposit-accounts")
    public ResponseEntity<DepositAccountDTO> updateDepositAccount(@RequestBody DepositAccountDTO depositAccountDTO) throws URISyntaxException {
        log.debug("REST request to update DepositAccount : {}", depositAccountDTO);
        if (depositAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepositAccountDTO result = depositAccountService.save(depositAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, depositAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /deposit-accounts} : get all the depositAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of depositAccounts in body.
     */
    @GetMapping("/deposit-accounts")
    public ResponseEntity<List<DepositAccountDTO>> getAllDepositAccounts(Pageable pageable) {
        log.debug("REST request to get a page of DepositAccounts");
        Page<DepositAccountDTO> page = depositAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deposit-accounts/:id} : get the "id" depositAccount.
     *
     * @param id the id of the depositAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depositAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deposit-accounts/{id}")
    public ResponseEntity<DepositAccountDTO> getDepositAccount(@PathVariable Long id) {
        log.debug("REST request to get DepositAccount : {}", id);
        Optional<DepositAccountDTO> depositAccountDTO = depositAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depositAccountDTO);
    }

    /**
     * {@code DELETE  /deposit-accounts/:id} : delete the "id" depositAccount.
     *
     * @param id the id of the depositAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deposit-accounts/{id}")
    public ResponseEntity<Void> deleteDepositAccount(@PathVariable Long id) {
        log.debug("REST request to delete DepositAccount : {}", id);
        depositAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
