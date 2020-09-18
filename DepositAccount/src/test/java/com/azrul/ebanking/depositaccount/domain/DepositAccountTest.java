package com.azrul.ebanking.depositaccount.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azrul.ebanking.depositaccount.web.rest.TestUtil;

public class DepositAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepositAccount.class);
        DepositAccount depositAccount1 = new DepositAccount();
        depositAccount1.setId(1L);
        DepositAccount depositAccount2 = new DepositAccount();
        depositAccount2.setId(depositAccount1.getId());
        assertThat(depositAccount1).isEqualTo(depositAccount2);
        depositAccount2.setId(2L);
        assertThat(depositAccount1).isNotEqualTo(depositAccount2);
        depositAccount1.setId(null);
        assertThat(depositAccount1).isNotEqualTo(depositAccount2);
    }
}
