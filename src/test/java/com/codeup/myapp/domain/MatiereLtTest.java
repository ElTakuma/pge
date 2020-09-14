package com.codeup.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.codeup.myapp.web.rest.TestUtil;

public class MatiereLtTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatiereLt.class);
        MatiereLt matiereLt1 = new MatiereLt();
        matiereLt1.setId(1L);
        MatiereLt matiereLt2 = new MatiereLt();
        matiereLt2.setId(matiereLt1.getId());
        assertThat(matiereLt1).isEqualTo(matiereLt2);
        matiereLt2.setId(2L);
        assertThat(matiereLt1).isNotEqualTo(matiereLt2);
        matiereLt1.setId(null);
        assertThat(matiereLt1).isNotEqualTo(matiereLt2);
    }
}
