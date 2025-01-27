package org.tenpopt.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tenpopt.Config.RateLimitingFilter;
import org.tenpopt.Entity.Transaction;
import org.tenpopt.Service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RateLimitingFilter rateLimitingFilter;

    @BeforeEach
    void setUp() {
        rateLimitingFilter.resetRateLimiting();
    }

    @MockitoBean // Usamos MockBean para simular el servicio
    private TransactionService transactionService;

    @Test
    void deleteTransaction_ShouldReturnNoContent() throws Exception {
        int transactionId = 1;

        // Simulamos que el servicio eliminará la transacción correctamente
        when(transactionService.deleteTransaction(transactionId)).thenReturn(true);

        // Realizamos la petición DELETE
        mockMvc.perform(delete("/transaction/{id}", transactionId))
                .andExpect(status().isNoContent());  // Verificamos que el status es 204 No Content

        // Verificamos que el método del servicio fue llamado con el ID correcto
        verify(transactionService, times(1)).deleteTransaction(transactionId);
    }

    @Test
    void deleteTransaction_ShouldReturnNotFound() throws Exception {
        int transactionId = 1;

        // Simulamos que no se puede eliminar la transacción porque no existe
        when(transactionService.deleteTransaction(transactionId)).thenReturn(false);

        // Realizamos la petición DELETE
        mockMvc.perform(delete("/transaction/{id}", transactionId))
                .andExpect(status().isNotFound());  // Verificamos que el status es 404 Not Found

        // Verificamos que el método del servicio fue llamado con el ID correcto
        verify(transactionService, times(1)).deleteTransaction(transactionId);
    }

    @Test
    void getAllTransactions_ShouldReturnListOfTransactions() throws Exception {
        Transaction transaction1 = new Transaction();
        transaction1.setId(1);
        transaction1.setTrxMonto(100);
        transaction1.setTrxGiroComercio("Store A");
        transaction1.setTenpista("Tenant X");
        transaction1.setTrxFecha(LocalDateTime.now());
        transaction1.setTrxTipo("Venta");

        Transaction transaction2 = new Transaction();
        transaction2.setId(2);
        transaction2.setTrxMonto(200);
        transaction2.setTrxGiroComercio("Store B");
        transaction2.setTenpista("Tenant Y");
        transaction2.setTrxFecha(LocalDateTime.now());
        transaction2.setTrxTipo("Venta");

        when(transactionService.getAllTransactions()).thenReturn(List.of(transaction1, transaction2));

        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trxGiroComercio").value("Store A"))
                .andExpect(jsonPath("$[1].trxGiroComercio").value("Store B"));
    }

    @Test
    void getTransactionById_ShouldReturnTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setTrxMonto(100);
        transaction.setTrxGiroComercio("Store A");
        transaction.setTenpista("Tenant X");
        transaction.setTrxFecha(LocalDateTime.now());
        transaction.setTrxTipo("Venta");

        when(transactionService.getTransactionById(1)).thenReturn(transaction);

        mockMvc.perform(get("/transaction/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trxGiroComercio").value("Store A"));
    }

    @Test
    void createTransaction_ShouldReturnCreatedTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTrxMonto(1500);
        transaction.setTrxGiroComercio("Amazon");
        transaction.setTenpista("John Doe");
        transaction.setTrxFecha(LocalDateTime.of(2025, 1, 23, 12, 34, 56));
        transaction.setTrxTipo("Venta");

        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trxMonto").value(1500))
                .andExpect(jsonPath("$.trxGiroComercio").value("Amazon"))
                .andExpect(jsonPath("$.tenpista").value("John Doe"))
                .andExpect(jsonPath("$.trxFecha").value("2025-01-23T12:34:56"))
                .andExpect(jsonPath("$.trxTipo").value("Venta"));
    }

    @Test
    void updateTransaction_ShouldReturnUpdatedTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setTrxMonto(2000);
        transaction.setTrxGiroComercio("Ebay");
        transaction.setTenpista("Jane Doe");
        transaction.setTrxFecha(LocalDateTime.of(2025, 1, 23, 12, 34, 56));
        transaction.setTrxTipo("Venta");

        when(transactionService.updateTransaction(anyInt(), any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(put("/transaction/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trxMonto").value(2000))
                .andExpect(jsonPath("$.trxGiroComercio").value("Ebay"))
                .andExpect(jsonPath("$.tenpista").value("Jane Doe"))
                .andExpect(jsonPath("$.trxFecha").value("2025-01-23T12:34:56"))
                .andExpect(jsonPath("$.trxTipo").value("Venta"));
    }

}