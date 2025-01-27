package org.tenpopt.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tenpopt.Entity.Transaction;
import org.tenpopt.Entity.TransactionEdit;

public interface TransactionEditRepository extends JpaRepository<TransactionEdit, Integer> {
    TransactionEdit findTransactionEditById(int id);
}
