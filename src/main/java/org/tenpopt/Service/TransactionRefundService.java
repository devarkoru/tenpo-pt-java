package org.tenpopt.Service;
import org.springframework.stereotype.Service;
import org.tenpopt.Entity.TransactionRefund;
import org.tenpopt.Repository.TransactionRefundRepository;

import java.util.List;

@Service
public class TransactionRefundService {

    private final TransactionRefundRepository transactionRefundRepository;

    public TransactionRefundService(TransactionRefundRepository transactionRefundRepository) {
        this.transactionRefundRepository = transactionRefundRepository;
    }

    public List<TransactionRefund> getAllTransactionRefunds() {
        return transactionRefundRepository.findAll();
    }

    public TransactionRefund getTransactionRefundById(int id) {
        TransactionRefund transactionRefund = transactionRefundRepository.findTransactionRefundById(id);
        if (transactionRefund == null) {
            return null;
        } else {
            return transactionRefund;
        }
    }

    public TransactionRefund createTransactionRefund(TransactionRefund transactionRefund) {
        return transactionRefundRepository.save(transactionRefund);
    }

    public TransactionRefund updateTransactionRefund(int id, TransactionRefund transactionRefund) {
        TransactionRefund existingTransactionRefund = getTransactionRefundById(id);
        existingTransactionRefund.setTrxMonto(transactionRefund.getTrxMonto());
        existingTransactionRefund.setTrxGiroComercio(transactionRefund.getTrxGiroComercio());
        existingTransactionRefund.setTenpista(transactionRefund.getTenpista());
        existingTransactionRefund.setTrxFecha(transactionRefund.getTrxFecha());
        existingTransactionRefund.setTrxTipo(transactionRefund.getTrxTipo());
        existingTransactionRefund.setTrxOriginalId(transactionRefund.getTrxOriginalId());
        return transactionRefundRepository.save(existingTransactionRefund);
    }

    public boolean deleteTransactionRefund(int id) {
        // Verifica si la transacción existe en la base de datos
        if (transactionRefundRepository.existsById(id)) {
            // Si existe, la elimina y retorna true
            transactionRefundRepository.deleteById(id);
            return true;
        } else {
            // Si no existe, retorna false
            return false;
        }
    }
}
