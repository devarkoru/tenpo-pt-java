package org.tenpopt.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_refunds")
public class TransactionRefund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int trxMonto;

    @Column(nullable = false, length = 100)
    private String trxGiroComercio;

    @Column(nullable = false, length = 100)
    private String tenpista;

    @Column(nullable = false)
    private LocalDateTime trxFecha;

    @Column(nullable = false, length = 50)
    private String trxTipo;

    @Column(nullable = false)
    private int trxOriginalId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrxMonto() {
        return trxMonto;
    }

    public void setTrxMonto(int trxMonto) {
        this.trxMonto = trxMonto;
    }

    public String getTrxGiroComercio() {
        return trxGiroComercio;
    }

    public void setTrxGiroComercio(String trxGiroComercio) {
        this.trxGiroComercio = trxGiroComercio;
    }

    public String getTenpista() {
        return tenpista;
    }

    public void setTenpista(String tenpista) {
        this.tenpista = tenpista;
    }

    public LocalDateTime getTrxFecha() {
        return trxFecha;
    }

    public void setTrxFecha(LocalDateTime trxFecha) {
        this.trxFecha = trxFecha;
    }

    public String getTrxTipo() {
        return trxTipo;
    }

    public void setTrxTipo(String trxTipo) {
        this.trxTipo = trxTipo;
    }

    public int getTrxOriginalId() {
        return trxOriginalId;
    }

    public void setTrxOriginalId(int trxOriginalId) {
        this.trxOriginalId = trxOriginalId;
    }
}