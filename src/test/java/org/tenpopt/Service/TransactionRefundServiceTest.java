package org.tenpopt.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tenpopt.Entity.Transaction;
import org.tenpopt.Entity.TransactionRefund;
import org.tenpopt.Repository.TransactionRefundRepository;
import org.tenpopt.Repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionRefundServiceTest {

    @Mock
    private TransactionRefundRepository transactionRepository;

    @InjectMocks
    private TransactionRefundService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar mocks y service
    }

    @Test
    void shouldGetAllTransactions() {
        // Arrange
        TransactionRefund transaction1 = new TransactionRefund();
        transaction1.setId(1);
        transaction1.setTrxMonto(100);
        transaction1.setTrxGiroComercio("Store A");
        transaction1.setTenpista("Tenant X");
        transaction1.setTrxFecha(LocalDateTime.now());

        TransactionRefund transaction2 = new TransactionRefund();
        transaction2.setId(2);
        transaction2.setTrxMonto(200);
        transaction2.setTrxGiroComercio("Store B");
        transaction2.setTenpista("Tenant Y");
        transaction2.setTrxFecha(LocalDateTime.now());

        when(transactionRepository.findAll()).thenReturn(List.of(transaction1, transaction2));

        // Act
        List<TransactionRefund> transactions = transactionService.getAllTransactionRefunds();

        // Assert
        assertThat(transactions).hasSize(2);
        assertThat(transactions.get(0).getTrxGiroComercio()).isEqualTo("Store A");
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void shouldGetTransactionById() {
        // Arrange
        TransactionRefund transaction = new TransactionRefund();
        transaction.setId(1);
        transaction.setTrxMonto(100);
        transaction.setTrxGiroComercio("Store A");
        transaction.setTenpista("Tenant X");
        transaction.setTrxFecha(LocalDateTime.now());
        transaction.setTrxTipo("Venta");

        when(transactionRepository.findTransactionRefundById(1)).thenReturn((transaction));

        // Act
        TransactionRefund result = transactionService.getTransactionRefundById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTrxGiroComercio()).isEqualTo("Store A");
        verify(transactionRepository, times(1)).findTransactionRefundById(1);
    }

    @Test
    void shouldCreateTransaction() {
        // Arrange
        TransactionRefund transaction = new TransactionRefund();
        transaction.setTrxMonto(100);
        transaction.setTrxGiroComercio("Store A");
        transaction.setTenpista("Tenant X");
        transaction.setTrxFecha(LocalDateTime.now());

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Act
        TransactionRefund result = transactionService.createTransactionRefund(transaction);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTrxGiroComercio()).isEqualTo("Store A");
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void shouldUpdateTransaction() {
        // Arrange
        TransactionRefund existingTransaction = new TransactionRefund();
        existingTransaction.setId(4);
        existingTransaction.setTrxMonto(100);
        existingTransaction.setTrxGiroComercio("Store A");
        existingTransaction.setTenpista("Tenant X");
        existingTransaction.setTrxFecha(LocalDateTime.now());
        existingTransaction.setTrxTipo("Anulado");

        TransactionRefund updatedTransaction = new TransactionRefund();
        updatedTransaction.setTrxMonto(200);
        updatedTransaction.setTrxGiroComercio("Store B");
        updatedTransaction.setTenpista("Tenant Y");
        updatedTransaction.setTrxFecha(LocalDateTime.now());
        updatedTransaction.setTrxTipo("Anulado");


        when(transactionRepository.findTransactionRefundById(1)).thenReturn((existingTransaction));
        when(transactionRepository.save(existingTransaction)).thenReturn(existingTransaction);

        // Act
        TransactionRefund result = transactionService.updateTransactionRefund(1, updatedTransaction);

        // Assert
        assertThat(result.getTrxMonto()).isEqualTo(200);
        assertThat(result.getTrxGiroComercio()).isEqualTo("Store B");
        verify(transactionRepository, times(1)).findTransactionRefundById(1);
        verify(transactionRepository, times(1)).save(existingTransaction);
    }

    @Test
    void shouldDeleteTransaction() {
        // Arrange
        int transactionId = 1;
        // Simulamos que la transacción existe
        when(transactionRepository.existsById(transactionId)).thenReturn(true);  // Simula que la transacción existe
        doNothing().when(transactionRepository).deleteById(transactionId);  // Simula la eliminación sin errores

        // Act
        boolean result = transactionService.deleteTransactionRefund(transactionId);  // Llamamos al método deleteTransaction

        // Assert
        assertTrue(result);  // Verificamos que el resultado sea true, es decir, la eliminación fue exitosa
        verify(transactionRepository, times(1)).deleteById(transactionId);  // Verificamos que deleteById fue llamado una vez
    }
}