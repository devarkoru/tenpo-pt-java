package org.tenpopt.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.tenpopt.Entity.Tenpista;
import org.tenpopt.Service.TenpistaService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TenpistaController.class)
public class TenpistaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TenpistaService tenpistaService;

    @Test
    void getAllTenpistas_ShouldReturnOk() throws Exception {
        Tenpista tenpista1 = new Tenpista();
        tenpista1.setId(1);
        tenpista1.setNombre("Tenpista A");

        Tenpista tenpista2 = new Tenpista();
        tenpista2.setId(2);
        tenpista2.setNombre("Tenpista B");

        when(tenpistaService.getAllTenpistas()).thenReturn(List.of(tenpista1, tenpista2));

        mockMvc.perform(get("/tenpista")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Tenpista A"))
                .andExpect(jsonPath("$[1].nombre").value("Tenpista B"));

        verify(tenpistaService, times(1)).getAllTenpistas();
    }

    @Test
    void getTenpistaById_ShouldReturnOk() throws Exception {
        Tenpista tenpista = new Tenpista();
        tenpista.setId(1);
        tenpista.setNombre("Tenpista A");

        when(tenpistaService.getTenpistaById(1)).thenReturn(tenpista);

        mockMvc.perform(get("/tenpista/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tenpista A"));

        verify(tenpistaService, times(1)).getTenpistaById(1);
    }

    @Test
    void createTenpista_ShouldReturnCreated() throws Exception {
        Tenpista tenpista = new Tenpista();
        tenpista.setNombre("Tenpista A");

        when(tenpistaService.createTenpista(any(Tenpista.class))).thenReturn(tenpista);

        mockMvc.perform(post("/tenpista")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Tenpista A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tenpista A"));

        verify(tenpistaService, times(1)).createTenpista(any(Tenpista.class));
    }

    @Test
    void updateTenpista_ShouldReturnOk() throws Exception {
        Tenpista tenpista = new Tenpista();
        tenpista.setId(1);
        tenpista.setNombre("Tenpista B");

        when(tenpistaService.updateTenpista(eq(1), any(Tenpista.class))).thenReturn(tenpista);

        mockMvc.perform(put("/tenpista/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Tenpista B\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tenpista B"));

        verify(tenpistaService, times(1)).updateTenpista(eq(1), any(Tenpista.class));
    }

    @Test
    void deleteTenpista_ShouldReturnNoContent() throws Exception {
        int tenpistaId = 1;
        when(tenpistaService.deleteTenpista(tenpistaId)).thenReturn(true);

        mockMvc.perform(delete("/tenpista/{id}", tenpistaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(tenpistaService, times(1)).deleteTenpista(tenpistaId);
    }
}