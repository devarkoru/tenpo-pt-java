package org.tenpopt.Service;

import org.springframework.stereotype.Service;
import org.tenpopt.Entity.Tenpista;
import org.tenpopt.Repository.TenpistaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TenpistaService {
    private final TenpistaRepository tenpistaRepository;

    public TenpistaService(TenpistaRepository TenpistaRepository) {
        this.tenpistaRepository = TenpistaRepository;
    }

    public List<Tenpista> getAllTenpistas() {
        return tenpistaRepository.findAll();
    }

    public Tenpista getTenpistaById(int id) {
        return tenpistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenpista not found with id " + id));
    }

    public Tenpista getTenpistaByNroCuenta(int nroCuenta) {
        Tenpista tenpista = tenpistaRepository.findByNroCuenta(nroCuenta);
        if (tenpista == null) {
            return null;
        } else {
            return tenpista;
        }

    }

    public Tenpista createTenpista(Tenpista Tenpista) {
        return tenpistaRepository.save(Tenpista);
    }

    public Tenpista updateTenpista(int id, Tenpista Tenpista) {
        Tenpista existingTenpista = getTenpistaById(id);
        existingTenpista.setNombre(Tenpista.getNombre());
        existingTenpista.setApellido(Tenpista.getApellido());
        existingTenpista.setNroCuenta(Tenpista.getNroCuenta());
        return tenpistaRepository.save(existingTenpista);
    }

    public boolean deleteTenpista(int id) {
        // Verifica si la transacción existe en la base de datos
        if (tenpistaRepository.existsById(id)) {
            // Si existe, la elimina y retorna true
            tenpistaRepository.deleteById(id);
            return true;
        } else {
            // Si no existe, retorna false
            return false;
        }
    }


}
