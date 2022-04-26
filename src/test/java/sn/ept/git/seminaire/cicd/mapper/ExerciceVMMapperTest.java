package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.ExerciceDTOTestData;
import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;
import sn.ept.git.seminaire.cicd.mappers.ExerciceMapper;
import sn.ept.git.seminaire.cicd.models.Exercice;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class ExerciceVMMapperTest extends  MapperBaseTest{

    ExerciceDTO dto;
    Exercice entity;

    @Autowired
    private ExerciceMapper mapper;


    @BeforeEach
    void setUp() {
        dto = ExerciceDTOTestData.defaultDTO();
    }


    @Test
    void toEntity() {
        entity = mapper.asEntity(dto);
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getCreatedDate()).isEqualTo(dto.getCreatedDate());
        assertThat(entity.getLastModifiedDate()).isEqualTo(dto.getLastModifiedDate());
        assertThat(entity.getVersion()).isEqualTo(dto.getVersion());
        assertThat(entity.isDeleted()).isEqualTo(dto.isDeleted());
        assertThat(entity.isEnabled()).isEqualTo(dto.isEnabled());
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getStatus().compareTo(dto.getStatus()));
    }

    @Test
    void toVM() {
        entity = mapper.asEntity(dto);
        dto =mapper.asDTO(entity);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getCreatedDate()).isEqualTo(entity.getCreatedDate());
        assertThat(dto.getLastModifiedDate()).isEqualTo(entity.getLastModifiedDate());
        assertThat(dto.getVersion()).isEqualTo(entity.getVersion());
        assertThat(dto.isDeleted()).isEqualTo(entity.isDeleted());
        assertThat(dto.isEnabled()).isEqualTo(entity.isEnabled());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getStatus().compareTo(entity.getStatus()));
    }
}
