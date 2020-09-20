package com.azrul.ebanking.depositaccount.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azrul.ebanking.depositaccount.web.rest.TestUtil;

public class DepositAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepositAccountDTO.class);
        DepositAccountDTO depositAccountDTO1 = new DepositAccountDTO();
        depositAccountDTO1.setId(1L);
        DepositAccountDTO depositAccountDTO2 = new DepositAccountDTO();
        assertThat(depositAccountDTO1).isNotEqualTo(depositAccountDTO2);
        depositAccountDTO2.setId(depositAccountDTO1.getId());
        assertThat(depositAccountDTO1).isEqualTo(depositAccountDTO2);
        depositAccountDTO2.setId(2L);
        assertThat(depositAccountDTO1).isNotEqualTo(depositAccountDTO2);
        depositAccountDTO1.setId(null);
        assertThat(depositAccountDTO1).isNotEqualTo(depositAccountDTO2);
    }
}
