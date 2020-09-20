package com.azrul.ebanking.depositaccount.service.mapper;


import com.azrul.ebanking.depositaccount.domain.*;
import com.azrul.ebanking.depositaccount.service.dto.DepositAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DepositAccount} and its DTO {@link DepositAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepositAccountMapper extends EntityMapper<DepositAccountDTO, DepositAccount> {



    default DepositAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setId(id);
        return depositAccount;
    }
}
