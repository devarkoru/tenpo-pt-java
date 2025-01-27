package org.tenpopt.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tenpopt.Entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findTransactionById(int id);
}
