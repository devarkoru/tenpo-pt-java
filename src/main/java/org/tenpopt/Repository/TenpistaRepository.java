package org.tenpopt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tenpopt.Entity.Tenpista;

import java.util.Optional;

public interface TenpistaRepository extends JpaRepository<Tenpista, Integer> {
    Tenpista findByNroCuenta(int nroCuenta);
}