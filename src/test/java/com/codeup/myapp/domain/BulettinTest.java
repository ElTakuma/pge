package com.codeup.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.codeup.myapp.web.rest.TestUtil;

public class BulettinTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bulettin.class);
        Bulettin bulettin1 = new Bulettin();
        bulettin1.setId(1L);
        Bulettin bulettin2 = new Bulettin();
        bulettin2.setId(bulettin1.getId());
        assertThat(bulettin1).isEqualTo(bulettin2);
        bulettin2.setId(2L);
        assertThat(bulettin1).isNotEqualTo(bulettin2);
        bulettin1.setId(null);
        assertThat(bulettin1).isNotEqualTo(bulettin2);
    }
}
