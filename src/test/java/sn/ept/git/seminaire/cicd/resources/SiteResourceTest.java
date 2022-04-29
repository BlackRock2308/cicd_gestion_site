package sn.ept.git.seminaire.cicd.resources;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import sn.ept.git.seminaire.cicd.data.SiteVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.SiteDTO;
import sn.ept.git.seminaire.cicd.dto.vm.SiteVM;
import sn.ept.git.seminaire.cicd.mappers.SiteMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.SiteVMMapper;
import sn.ept.git.seminaire.cicd.models.Site;
import sn.ept.git.seminaire.cicd.services.ISiteService;
import sn.ept.git.seminaire.cicd.utils.SizeMapping;
import sn.ept.git.seminaire.cicd.utils.TestUtil;
import sn.ept.git.seminaire.cicd.utils.UrlMapping;

import javax.validation.Valid;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class SiteResourceTest extends BasicResourceTest {


    private static SiteVM vm;
    @Autowired
    private ISiteService service;
    private SiteDTO dto;
    private static Site site;
    @Autowired
    @Valid
    SiteVMMapper vmMapper;
    @Autowired
    @Valid
    SiteMapper mapper;

    @BeforeAll
    static void beforeAll() {
        log.info(" before all ");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");
        //service.deleteAll();
        // vm = SiteVMTestData.defaultVM();

    }

    @Test
    void findAll_shouldReturnSites() throws Exception {
        site = SiteVMTestData.defaultEntity(site);
        vm = vmMapper.asDTO(site);
        dto = service.save(vm);
        mockMvc.perform(
                 get(UrlMapping.Site.ALL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andDo(MockMvcResultHandlers.print()) //can print request details
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[0].id").exists())
                .andExpect(jsonPath("$.content.[0].version").exists())
                .andExpect(jsonPath("$.content.[0].enabled").exists())
                .andExpect(jsonPath("$.content.[0].deleted").exists())
                .andExpect(jsonPath("$.content.[0].enabled", is(true)))
                .andExpect(jsonPath("$.content.[0].deleted").value(false))
                .andExpect(jsonPath("$.content.[0].name", is(dto.getName())))
                .andExpect(jsonPath("$.content.[0].phone").value(dto.getPhone()))
                .andExpect(jsonPath("$.content.[0].email").value(dto.getEmail()))
                .andExpect(jsonPath("$.content.[0].longitude").value(dto.getLongitude()))
                .andExpect(jsonPath("$.content.[0].latitude").value(dto.getLatitude()));

    }


    @Test
    void findById_shouldReturnSite() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        vm = vmMapper.asDTO(site); //map site with vmMapper
        dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Site.FIND_BY_ID, dto.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.id").exists())
                //.andExpect(jsonPath("$.[0].version").exists())
                //.andExpect(jsonPath("$.enabled").exists())
                //.andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.content.name", is(dto.getName())))
                .andExpect(jsonPath("$.phone").value(dto.getPhone()))
                .andExpect(jsonPath("$.email").value(dto.getEmail()))
                .andExpect(jsonPath("$.longitude").value(dto.getLongitude()))
                .andExpect(jsonPath("$.latitude").value(dto.getLatitude()));
    }

    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(UrlMapping.Site.FIND_BY_ID, UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void add_shouldCreateSite() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        vm = vmMapper.asDTO(site); //map site with vmMapper
        mockMvc.perform(
                post(UrlMapping.Site.ADD)
                        .contentType(MediaType.APPLICATION_JSON)
                        //.content(TestUtil.convertObjectToJsonBytes(vm))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.enabled").exists())
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.name").value(vm.getName()))
                .andExpect(jsonPath("$.phone").value(vm.getPhone()))
                .andExpect(jsonPath("$.email").value(vm.getEmail()))
                .andExpect(jsonPath("$.longitude").value(vm.getLongitude()))
                .andExpect(jsonPath("$.latitude").value(vm.getLatitude()));
    }

    @Test
    void add_withNameMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MIN - 1));
        mockMvc.perform(post(UrlMapping.Site.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void add_withNameMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MAX + 1));
        mockMvc.perform(post(UrlMapping.Site.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                //.content(TestUtil.convertObjectToJsonBytes(vm)))
        ).andExpect(status().isBadRequest());
    }


    @Test
    void add_withPhoneMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        vm = vmMapper.asDTO(site); //map site with vmMapper
        vm.setPhone(RandomStringUtils.random(SizeMapping.Phone.MIN - 1));
        mockMvc.perform(post(UrlMapping.Site.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                //.content(TestUtil.convertObjectToJsonBytes(vm)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void add_withPhoneMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setPhone(RandomStringUtils.random(SizeMapping.Phone.MAX + 1));
        mockMvc.perform(post(UrlMapping.Site.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                //.content(TestUtil.convertObjectToJsonBytes(vm)))
        ).andExpect(status().isBadRequest());
    }


    @Test
    void add_withEmailMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MIN - 1));
        mockMvc.perform(post(UrlMapping.Site.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                //.content(TestUtil.convertObjectToJsonBytes(vm)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void add_withEmailMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MAX + 1));
        mockMvc.perform(post(UrlMapping.Site.ADD)
                .contentType(MediaType.APPLICATION_JSON)
                //.content(TestUtil.convertObjectToJsonBytes(vm)))
        ).andExpect(status().isBadRequest());
    }


    @Test
    void update_shouldUpdateSite() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        //dto = mapper.asDTO(site);
        //vm = vmMapper.asDTO(site); //map site with vmMapper
        //dto = service.save(vm);
        site.setName(TestData.Update.name);
        site.setPhone(TestData.Update.phone);
        site.setEmail(TestData.Update.email);
        site.setLongitude(TestData.Update.longitude);
        site.setLatitude(TestData.Update.latitude);
        mockMvc.perform(
                put(UrlMapping.Site.UPDATE, site.getId()) //change dto to vm
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(site))
        )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.enabled").exists())
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.name").value(site.getName()))
                .andExpect(jsonPath("$.phone").value(site.getPhone()))
                .andExpect(jsonPath("$.email").value(site.getEmail()))
                .andExpect(jsonPath("$.longitude").value(site.getLongitude()))
                .andExpect(jsonPath("$.latitude").value(site.getLatitude()));
    }

    @Test
    void update_withNameMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        vm = vmMapper.asDTO(site); //map site with vmMapper
        //dto = service.save(vm);
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MIN - 1));
        mockMvc.perform(put(UrlMapping.Site.UPDATE, vm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError()); //change notFound to BadRequest
    }

    @Test
    @Rollback(value = false)
    void update_withNameMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        vm = vmMapper.asDTO(site); //map site with vmMapper
        //dto = service.save(vm);
        vm.setName(RandomStringUtils.random(SizeMapping.Name.MAX + 1));
        mockMvc.perform(put(UrlMapping.Site.UPDATE, vm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm))) //dto to vm
                .andExpect(status().is4xxClientError());
    }


    @Test
    void update_withPhoneMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        vm = vmMapper.asDTO(site); //map site with vmMapper
        //dto = service.save(vm);
        vm.setPhone(RandomStringUtils.random(SizeMapping.Phone.MIN - 1));
        mockMvc.perform(put(UrlMapping.Site.UPDATE, vm.getId()) //dto to vm
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withPhoneMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        vm = vmMapper.asDTO(site); //map site with vmMapper
        //dto = service.save(vm);
        vm.setPhone(RandomStringUtils.random(SizeMapping.Phone.MAX + 1));
        mockMvc.perform(put(UrlMapping.Site.UPDATE, vm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withEmailMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        vm = vmMapper.asDTO(site); //map site with vmMapper
        //dto = service.save(vm);
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MIN - 1));
        mockMvc.perform(put(UrlMapping.Site.UPDATE, vm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update_withEmailMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        vm = vmMapper.asDTO(site); //map site with vmMapper
        //dto = service.save(vm);
        vm.setEmail(RandomStringUtils.random(SizeMapping.Email.MAX + 1));
        mockMvc.perform(put(UrlMapping.Site.UPDATE, vm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void delete_shouldDeleteSite() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        //dto = service.save(vm);
        dto = mapper.asDTO(site); //map site with vmMapper
        //dto = service.save(vm);
        mockMvc.perform(
                delete(UrlMapping.Site.DELETE, dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    void delete_withBadId_shouldReturnNotFound() throws Exception {
        site = SiteVMTestData.defaultEntity(site); //add site
        vm = vmMapper.asDTO(site); //map site with vmMapper
        //dto = service.save(vm);
        mockMvc.perform(
                delete(UrlMapping.Site.DELETE, UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

}