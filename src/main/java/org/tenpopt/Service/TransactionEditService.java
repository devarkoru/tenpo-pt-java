package org.tenpopt.Service;
import org.springframework.stereotype.Service;
import org.tenpopt.Entity.TransactionEdit;
import org.tenpopt.Repository.TransactionEditRepository;
import java.util.List;

@Service
public class TransactionEditService {

    private final TransactionEditRepository transactionEditRepository;

    public TransactionEditService(TransactionEditRepository transactionEditEditRepository) {
        this.transactionEditRepository = transactionEditEditRepository;
    }

    public List<TransactionEdit> getAllTransactions() {
        return transactionEditRepository.findAll();
    }

    public TransactionEdit getTransactionById(int id) {
        TransactionEdit transactionEdit = transactionEditRepository.findTransactionEditById(id);
        if (transactionEdit == null) {
            return null;
        } else {
            return transactionEdit;
        }
    }

    public TransactionEdit createTransaction(TransactionEdit transactionEdit) {
        return transactionEditRepository.save(transactionEdit);
    }

    public TransactionEdit updateTransaction(int id, TransactionEdit transactionEdit) {
        TransactionEdit existingTransaction = getTransactionById(id);
        existingTransaction.setTrxMonto(transactionEdit.getTrxMonto());
        existingTransaction.setTrxGiroComercio(transactionEdit.getTrxGiroComercio());
        existingTransaction.setTenpista(transactionEdit.getTenpista());
        existingTransaction.setTrxFecha(transactionEdit.getTrxFecha());
        existingTransaction.setTrxTipo(transactionEdit.getTrxTipo());
        existingTransaction.setTrxOriginalId(transactionEdit.getTrxOriginalId());
        return transactionEditRepository.save(existingTransaction);
    }

    public boolean deleteTransaction(int id) {
        // Verifica si la transacción existe en la base de datos
        if (transactionEditRepository.existsById(id)) {
            // Si existe, la elimina y retorna true
            transactionEditRepository.deleteById(id);
            return true;
        } else {
            // Si no existe, retorna false
            return false;
        }
    }
}
