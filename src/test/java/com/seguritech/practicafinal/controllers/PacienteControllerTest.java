package com.seguritech.practicafinal.controllers;

import com.seguritech.practicafinal.domain.Paciente;
import com.seguritech.practicafinal.service.PacienteService;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author surface
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PacienteControllerTest {
    
    private MockMvc mockMvc;
    
    private final String paciente = "{\"dni\" : \"122477735\"},\"nombre\" : \"Luis Miguel\"},\"e_mail\" : \"luismiguel@gmail.com\"},{\"telefono\" : \"3828263783\"},{\"obra\" : 1}";
    private final String pacienteup = "{\"paciente_id\" : 2},{\"dni\" : \"122477735\"},\"nombre\" : \"Luis Miguel\"},\"e_mail\" : \"luismiguel@gmail.com\"},{\"telefono\" : \"3828263783\"},{\"obra\" : 1}";
    
    PacienteController pacienteController;
    
    @Mock
    private PacienteService pacienteService;
    
    public PacienteControllerTest() {
    }

   @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Paciente paciente = new Paciente();
        paciente.setPaciente_id(1L);
        paciente.setDni("122477735");
        paciente.setNombre("Luis Miguel");
        paciente.setE_mail("luismiguel@gmail.com");
        paciente.setTelefono("3828263783");
        paciente.setObra(2);
        Mockito.when(pacienteService.findOne(1L)).thenReturn(paciente);

        pacienteController = new PacienteController(pacienteService);
        
        mockMvc = MockMvcBuilders.standaloneSetup(pacienteController).build();
    } 
    
//    @Test
//    public void testFindAllPacientes() {
//        System.out.println("findAllPacientes");
//        List<Paciente> findAllPacientes = pacienteController.findAllPacientes();
//        assertEquals(findAllPacientes.size(), 2);
//    }
    
    @Test
    public void testGetPaciente_isOkWhenPacienteIsFound() throws Exception {
        System.out.println("getPaciente");
        final long id = 1;

        mockMvc.perform(get("/pacientes/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paciente_id").isNumber())
                .andExpect(jsonPath("$.paciente_id").value(id))
                .andExpect(jsonPath("$.dni").isString())
                .andExpect(jsonPath("$.nombre").isString())
                .andExpect(jsonPath("$.e_mail").isString())
                .andExpect(jsonPath("$.telefono").isString())
                .andExpect(jsonPath("$.obra").isNumber())
                ;
    }

    @Test
    public void testGetPaciente_isError404WhenPacienteNotFound() throws Exception {
        System.out.println("getPaciente");
        final long id = 2;

        mockMvc.perform(get("/pacientes/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
    
    
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        
        mockMvc.perform(post("/pacientes/")
                 .content(paciente)
	         .contentType(APPLICATION_JSON_UTF8))
		 .andExpect(status().isCreated());
    }

    @Test
    public void testListAll_isOkWhenPacienteIsFound() throws Exception {
        System.out.println("listAll");
        final int obra = 1;
        
         mockMvc.perform(get("/pacientes/" + obra)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paciente_id").isNumber())
                .andExpect(jsonPath("$.dni").isString())
                .andExpect(jsonPath("$.nombre").isString())
                .andExpect(jsonPath("$.e_mail").isString())
                .andExpect(jsonPath("$.telefono").isString())
                .andExpect(jsonPath("$.obra").isNumber()) 
                ;
    }

     @Test
    public void testListAll_isError404WhenPacienteNotFound() throws Exception {
        System.out.println("listAll");
        final int obra = 20;

        mockMvc.perform(get("/pacientes/" + obra)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        
        mockMvc.perform(put("/pacientes/")
                 .content(pacienteup)
	         .contentType(APPLICATION_JSON_UTF8))
		 .andExpect(status().isOk());
    }

    @Test
    public void testDeletePaciente() throws Exception {
        System.out.println("deletePaciente");
        Long id = 2L;
        
        mockMvc.perform(delete("/pacientes/" +id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
}
