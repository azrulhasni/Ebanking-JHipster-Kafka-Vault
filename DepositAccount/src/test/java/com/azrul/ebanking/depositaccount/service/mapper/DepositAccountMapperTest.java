package com.azrul.ebanking.depositaccount.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DepositAccountMapperTest {

    private DepositAccountMapper depositAccountMapper;

    @BeforeEach
    public void setUp() {
        depositAccountMapper = new DepositAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(depositAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(depositAccountMapper.fromId(null)).isNull();
    }
}
