package sn.ept.git.seminaire.cicd.resources;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import sn.ept.git.seminaire.cicd.data.ExerciceVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;
import sn.ept.git.seminaire.cicd.dto.vm.ExerciceVM;
import sn.ept.git.seminaire.cicd.services.IExerciceService;
import sn.ept.git.seminaire.cicd.utils.SizeMapping;
import sn.ept.git.seminaire.cicd.utils.TestUtil;
import sn.ept.git.seminaire.cicd.utils.UrlMapping;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
class ExerciceResourceTest extends BasicResourceTest {

    static private ExerciceVM vm;
    @Autowired
    private IExerciceService service;
    private ExerciceDTO dto;


    @BeforeAll
    static void beforeAll() {
        log.info(" before all ");
    }

    @BeforeEach
    void beforeEach() {
        log.info(" before each ");
        service.deleteAll();
        vm = ExerciceVMTestData.defaultVM();
    }

    @Test
    void findAll_shouldReturnExercices() throws Exception {
        //dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Exercice.ALL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    void findById_shouldReturnExercice() throws Exception {
        //dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Exercice.FIND_BY_ID, vm.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(UrlMapping.Exercice.FIND_BY_ID, UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void add_shouldCreateExercice() throws Exception {
        mockMvc.perform(
                        post(UrlMapping.Exercice.ADD)
                                .contentType(MediaType.APPLICATION_JSON)
                        //.content(TestUtil.convertObjectToJsonBytes(vm))
                )
                .andExpect(status().isBadRequest());
    }






    @Test
    void update_shouldUpdateExercice() throws Exception {
        // dto = service.save(vm);
        vm.setName(TestData.Update.name);
        vm.setStart(TestData.Update.start);
        vm.setEnd(TestData.Update.end);
        vm.setStatus(TestData.Update.status);
        mockMvc.perform(
                        put(UrlMapping.Exercice.UPDATE, vm.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                        //.content(TestUtil.convertObjectToJsonBytes(vm))
                )
                .andExpect(status().isBadRequest());

    }




}