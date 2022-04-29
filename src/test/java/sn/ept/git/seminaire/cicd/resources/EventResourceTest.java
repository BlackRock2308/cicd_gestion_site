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
import sn.ept.git.seminaire.cicd.data.EventVMTestData;;
import sn.ept.git.seminaire.cicd.dto.EventDTO;
import sn.ept.git.seminaire.cicd.dto.vm.EventVM;
import sn.ept.git.seminaire.cicd.mappers.EventMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.EventVMMapper;
import sn.ept.git.seminaire.cicd.models.Event;
import sn.ept.git.seminaire.cicd.services.IEventService;
import sn.ept.git.seminaire.cicd.utils.SizeMapping;
import sn.ept.git.seminaire.cicd.utils.TestUtil;
import sn.ept.git.seminaire.cicd.utils.UrlMapping;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class EventResourceTest extends BasicResourceTest {


    private static EventVM vm;
    @Autowired
    private IEventService service;
    private EventDTO dto;
    private static Event event;
    @Autowired
    @Valid
    EventVMMapper vmMapper;
    @Autowired
    @Valid
    EventMapper mapper;

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
    void findById_withBadId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(UrlMapping.Site.FIND_BY_ID, UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }




    @Test
    void add_withTitleMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setTitle(RandomStringUtils.random(SizeMapping.Name.MIN - 1));
        mockMvc.perform(post(UrlMapping.Site.ADD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void add_withDescriptionMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        vm.setDescription(RandomStringUtils.random(SizeMapping.Name.MAX + 1));
        mockMvc.perform(post(UrlMapping.Site.ADD)
                        .contentType(MediaType.APPLICATION_JSON)
                //.content(TestUtil.convertObjectToJsonBytes(vm)))
        ).andExpect(status().isBadRequest());
    }




    @Test
    void update_withTitleeMinLengthExceeded_shouldReturnBadRequest() throws Exception {
        event = EventVMTestData.defaultEntity(event); //add site
        vm = vmMapper.asDTO(event); //map site with vmMapper
        //dto = service.save(vm);
        vm.setTitle(RandomStringUtils.random(SizeMapping.Title.MIN - 1));
        mockMvc.perform(put(UrlMapping.Site.UPDATE, vm.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vm)))
                .andExpect(status().is4xxClientError()); //change notFound to BadRequest
    }

    @Test
    @Rollback(value = false)
    void update_withDescriptionMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
        event = EventVMTestData.defaultEntity(event); //add site
        vm = vmMapper.asDTO(event); //map site with vmMapper
        //dto = service.save(vm);
        vm.setDescription(RandomStringUtils.random(SizeMapping.Description.MAX + 1));
        mockMvc.perform(put(UrlMapping.Site.UPDATE, vm.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(vm))) //dto to vm
                .andExpect(status().is4xxClientError());
    }





    @Test
    void delete_shouldDeleteSite() throws Exception {
        event = EventVMTestData.defaultEntity(event); //add site
        //dto = service.save(vm);
        dto = mapper.asDTO(event); //map site with vmMapper
        //dto = service.save(vm);
        mockMvc.perform(
                delete(UrlMapping.Site.DELETE, dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    void delete_withBadId_shouldReturnNotFound() throws Exception {
        event = EventVMTestData.defaultEntity(event); //add site
        vm = vmMapper.asDTO(event); //map site with vmMapper
        //dto = service.save(vm);
        mockMvc.perform(
                delete(UrlMapping.Site.DELETE, UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

}
