package org.tenpopt.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tenpopt.Entity.Tenpista;
import org.tenpopt.Repository.TenpistaRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TenpistaServiceTest {

    @Mock
    private TenpistaRepository tenpistaRepository;

    @InjectMocks
    private TenpistaService tenpistaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllTenpistas() {
        // Arrange
        Tenpista tenpista1 = new Tenpista();
        tenpista1.setId(1);
        tenpista1.setNombre("Tenpista A");

        Tenpista tenpista2 = new Tenpista();
        tenpista2.setId(2);
        tenpista2.setNombre("Tenpista B");

        when(tenpistaRepository.findAll()).thenReturn(List.of(tenpista1, tenpista2));

        // Act
        List<Tenpista> tenpistas = tenpistaService.getAllTenpistas();

        // Assert
        assertThat(tenpistas).hasSize(2);
        assertThat(tenpistas.get(0).getNombre()).isEqualTo("Tenpista A");
        verify(tenpistaRepository, times(1)).findAll();
    }

    @Test
    void shouldGetTenpistaById() {
        // Arrange
        Tenpista tenpista = new Tenpista();
        tenpista.setId(1);
        tenpista.setNombre("Tenpista A");

        when(tenpistaRepository.findById(1)).thenReturn(Optional.of(tenpista));

        // Act
        Tenpista result = tenpistaService.getTenpistaById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Tenpista A");
        verify(tenpistaRepository, times(1)).findById(1);
    }

    @Test
    void shouldCreateTenpista() {
        // Arrange
        Tenpista tenpista = new Tenpista();
        tenpista.setNombre("Tenpista A");

        when(tenpistaRepository.save(tenpista)).thenReturn(tenpista);

        // Act
        Tenpista result = tenpistaService.createTenpista(tenpista);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Tenpista A");
        verify(tenpistaRepository, times(1)).save(tenpista);
    }

    @Test
    void shouldUpdateTenpista() {
        // Arrange
        Tenpista existingTenpista = new Tenpista();
        existingTenpista.setId(1);
        existingTenpista.setNombre("Tenpista A");

        Tenpista updatedTenpista = new Tenpista();
        updatedTenpista.setNombre("Tenpista B");

        when(tenpistaRepository.findById(1)).thenReturn(Optional.of(existingTenpista));
        when(tenpistaRepository.save(existingTenpista)).thenReturn(existingTenpista);

        // Act
        Tenpista result = tenpistaService.updateTenpista(1, updatedTenpista);

        // Assert
        assertThat(result.getNombre()).isEqualTo("Tenpista B");
        verify(tenpistaRepository, times(1)).findById(1);
        verify(tenpistaRepository, times(1)).save(existingTenpista);
    }

    @Test
    void shouldDeleteTenpista() {
        // Arrange
        int tenpistaId = 1;
        when(tenpistaRepository.existsById(tenpistaId)).thenReturn(true);
        doNothing().when(tenpistaRepository).deleteById(tenpistaId);

        // Act
        boolean result = tenpistaService.deleteTenpista(tenpistaId);

        // Assert
        assertTrue(result);
        verify(tenpistaRepository, times(1)).deleteById(tenpistaId);
    }
}