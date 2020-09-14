package com.codeup.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.codeup.myapp.web.rest.TestUtil;

public class ClasseLtTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasseLt.class);
        ClasseLt classeLt1 = new ClasseLt();
        classeLt1.setId(1L);
        ClasseLt classeLt2 = new ClasseLt();
        classeLt2.setId(classeLt1.getId());
        assertThat(classeLt1).isEqualTo(classeLt2);
        classeLt2.setId(2L);
        assertThat(classeLt1).isNotEqualTo(classeLt2);
        classeLt1.setId(null);
        assertThat(classeLt1).isNotEqualTo(classeLt2);
    }
}
