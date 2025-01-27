package org.tenpopt.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tenpopt.Entity.TransactionRefund;

public interface TransactionRefundRepository extends JpaRepository<TransactionRefund, Integer> {
    TransactionRefund findTransactionRefundById(int id);
}
