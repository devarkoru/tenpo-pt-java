package org.tenpopt.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tenpopt.Entity.Transaction;
import org.tenpopt.Entity.TransactionRefund;
import org.tenpopt.Service.TransactionRefundService;
import org.tenpopt.Service.TransactionService;
import org.tenpopt.Utils.Exceptions;

import java.util.List;

@RestController
@RequestMapping("/transactionRefund")
public class TransactionRefundController {

    @Autowired
    private TransactionRefundService transactionRefundService;

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Get all transactionRefunds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the transactionRefunds",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionRefund.class)) })
    })
    @GetMapping
    public List<TransactionRefund> getAllTransactionRefunds() {
        return transactionRefundService.getAllTransactionRefunds();
    }

    @Operation(summary = "Get a transactionRefund by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the transactionRefund",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionRefund.class)) }),
            @ApiResponse(responseCode = "404", description = "TransactionRefund not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionRefund> getTransactionRefundById(@PathVariable int id) {
        TransactionRefund transactionRefund = transactionRefundService.getTransactionRefundById(id);
        return ResponseEntity.ok(transactionRefund);
    }

    @Operation(summary = "Create a new transactionRefund")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TransactionRefund created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionRefund.class)) })
    })
    @PostMapping
    public ResponseEntity<TransactionRefund> createTransactionRefund(@RequestBody TransactionRefund transactionRefund) {
        TransactionRefund existingTransactionRefund = transactionRefundService.getTransactionRefundById(transactionRefund.getId());
        if (existingTransactionRefund == null) {
            Transaction transaction = transactionService.getTransactionById(transactionRefund.getTrxOriginalId());
            if(transaction != null){
                System.out.println("Transaction con id " + transactionRefund.getTrxOriginalId() + " existe. Inicio Actualizar Transaction");
                transaction.setTrxTipo("Anulado");
                transactionService.updateTransaction(transaction.getId(), transaction);
            } else {
                throw new Exceptions("transaction original con id " + transactionRefund.getTrxOriginalId() + " no existe.");
            }

            TransactionRefund createdTransactionRefund = transactionRefundService.createTransactionRefund(transactionRefund);
            return ResponseEntity.ok(createdTransactionRefund);
        }
        throw new Exceptions("transactionRefund con id " + transactionRefund.getId() + " ya existe.");
    }

    @Operation(summary = "Update an existing transactionRefund")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TransactionRefund updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionRefund.class)) }),
            @ApiResponse(responseCode = "404", description = "TransactionRefund not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<TransactionRefund> updateTransactionRefund(@PathVariable int id, @RequestBody TransactionRefund transactionRefund) {
        TransactionRefund updatedTransactionRefund = transactionRefundService.updateTransactionRefund(id, transactionRefund);
        return ResponseEntity.ok(updatedTransactionRefund);
    }

    @Operation(summary = "Delete a transactionRefund by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "TransactionRefund deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "TransactionRefund not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionRefund(@PathVariable int id) {
        boolean deleted = transactionRefundService.deleteTransactionRefund(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}