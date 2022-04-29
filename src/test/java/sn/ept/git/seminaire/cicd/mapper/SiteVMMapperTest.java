package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.SiteDTOTestData;
import sn.ept.git.seminaire.cicd.dto.SiteDTO;
import sn.ept.git.seminaire.cicd.mappers.SiteMapper;
import sn.ept.git.seminaire.cicd.models.Site;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class SiteVMMapperTest extends  MapperBaseTest{

    SiteDTO dto;
    Site entity;

    @Autowired
    private SiteMapper mapper;


    @BeforeEach
    void setUp() {
        dto = SiteDTOTestData.defaultDTO();
        entity = SiteDTOTestData.defaultEntity(entity);
    }


    @Test
    void toEntity() {
        dto = mapper.asDTO(entity);
        entity = mapper.asEntity(dto);
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getCreatedDate()).isEqualTo(dto.getCreatedDate());
        assertThat(entity.getLastModifiedDate()).isEqualTo(dto.getLastModifiedDate());
        assertThat(entity.getVersion()).isEqualTo(dto.getVersion());
        assertThat(entity.isDeleted()).isEqualTo(dto.isDeleted());
        assertThat(entity.isEnabled()).isEqualTo(dto.isEnabled());
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getEmail()).isEqualTo(dto.getEmail());
        assertThat(entity.getLongitude()).isEqualTo(dto.getLongitude());
        assertThat(entity.getLatitude()).isEqualTo(dto.getLatitude());
        assertThat(entity.getPhone()).isEqualTo(dto.getPhone());
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
        assertThat(dto.getEmail()).isEqualTo(entity.getEmail());
        assertThat(dto.getLongitude()).isEqualTo(entity.getLongitude());
        assertThat(dto.getLatitude()).isEqualTo(entity.getLatitude());
        assertThat(dto.getPhone()).isEqualTo(entity.getPhone());
    }
}