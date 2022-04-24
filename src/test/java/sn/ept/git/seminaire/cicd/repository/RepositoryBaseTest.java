package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.CicdApplicationTests;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {CicdApplicationTests.class})
@Transactional
@TestExecutionListeners({})
class RepositoryBaseTest {
    public static final int DEFAULT_SIZE = 10;
    public static final int DEFAULT_PAGE = 0;
    static final PageRequest PAGE = PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE);

    @Test
    void basic() {
        assertThat(PAGE.getPageSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(PAGE.getPageNumber()).isEqualTo(DEFAULT_PAGE);
    }
}
