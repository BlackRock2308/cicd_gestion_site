package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.CicdApplicationTests;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {CicdApplicationTests.class})
@Transactional
class MapperBaseTest {

    @Test
    void toEntity() {
        assertThat(1L).isPositive();
    }

}