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
import org.tenpopt.Entity.TransactionEdit;
import org.tenpopt.Service.TransactionEditService;
import org.tenpopt.Service.TransactionService;
import org.tenpopt.Utils.Exceptions;

import java.util.List;

@RestController
@RequestMapping("/transactionEdit")
public class TransactionEditController {

    @Autowired
    private TransactionEditService transactionEditService;

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Get all transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the transactions",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionEdit.class)) })
    })
    @GetMapping
    public List<TransactionEdit> getAllTransactions() {
        return transactionEditService.getAllTransactions();
    }

    @Operation(summary = "Get a TransactionEdit by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the TransactionEdit",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionEdit.class)) }),
            @ApiResponse(responseCode = "404", description = "TransactionEdit not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionEdit> getTransactionById(@PathVariable int id) {
        TransactionEdit transactionEdit = transactionEditService.getTransactionById(id);
        return ResponseEntity.ok(transactionEdit);
    }

    @Operation(summary = "Create a new TransactionEdit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TransactionEdit created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionEdit.class)) })
    })
    @PostMapping
    public ResponseEntity<TransactionEdit> createTransaction(@RequestBody TransactionEdit transactionEdit) {
        System.out.println("Inicio Validar TransactionEdit");
        TransactionEdit existingTransactionEdit = transactionEditService.getTransactionById(transactionEdit.getId());
        if (existingTransactionEdit == null) {
            System.out.println("TransactionEdit con id " + transactionEdit.getId() + " no existe. Inicio Validar trxOriginal");
            Transaction transaction = transactionService.getTransactionById(transactionEdit.getTrxOriginalId());
            if(transaction != null){
                System.out.println("Transaction con id " + transactionEdit.getTrxOriginalId() + " existe. Inicio Actualizar Transaction");
                transaction.setTrxMonto(transactionEdit.getTrxMonto());
                transaction.setTrxTipo("Editado");
                transactionService.updateTransaction(transaction.getId(), transaction);
            } else {
                throw new Exceptions("transaction original con id " + transactionEdit.getTrxOriginalId() + " no existe.");
            }
            TransactionEdit createdTransactionEdit = transactionEditService.createTransaction(transactionEdit);
            return ResponseEntity.ok(createdTransactionEdit);
        }
        throw new Exceptions("transactionEdit con id " + transactionEdit.getId() + " ya existe.");


    }

    @Operation(summary = "Update an existing TransactionEdit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TransactionEdit updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionEdit.class)) }),
            @ApiResponse(responseCode = "404", description = "TransactionEdit not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<TransactionEdit> updateTransaction(@PathVariable int id, @RequestBody TransactionEdit transactionEdit) {
        TransactionEdit updatedTransactionEdit = transactionEditService.updateTransaction(id, transactionEdit);
        return ResponseEntity.ok(updatedTransactionEdit);
    }

    @Operation(summary = "Delete a TransactionEdit by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "TransactionEdit deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "TransactionEdit not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        boolean deleted = transactionEditService.deleteTransaction(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}