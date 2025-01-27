package org.tenpopt.Service;
import org.springframework.stereotype.Service;
import org.tenpopt.Entity.Transaction;
import org.tenpopt.Repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(int id) {
        Transaction transaction = transactionRepository.findTransactionById(id);
        if (transaction == null) {
            return null;
        } else {
            return transaction;
        }
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(int id, Transaction transaction) {
        Transaction existingTransaction = getTransactionById(id);
        existingTransaction.setTrxMonto(transaction.getTrxMonto());
        existingTransaction.setTrxGiroComercio(transaction.getTrxGiroComercio());
        existingTransaction.setTenpista(transaction.getTenpista());
        existingTransaction.setTrxFecha(transaction.getTrxFecha());
        existingTransaction.setTrxTipo(transaction.getTrxTipo());
        return transactionRepository.save(existingTransaction);
    }

    public boolean deleteTransaction(int id) {
        // Verifica si la transacción existe en la base de datos
        if (transactionRepository.existsById(id)) {
            // Si existe, la elimina y retorna true
            transactionRepository.deleteById(id);
            return true;
        } else {
            // Si no existe, retorna false
            return false;
        }
    }
}
