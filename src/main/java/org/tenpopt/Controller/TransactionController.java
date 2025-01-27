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
import org.tenpopt.Service.TransactionService;
import org.tenpopt.Utils.Exceptions;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Get all transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the transactions",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class)) })
    })
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @Operation(summary = "Get a transaction by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the transaction",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class)) }),
            @ApiResponse(responseCode = "404", description = "Transaction not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @Operation(summary = "Create a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class)) })
    })
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction existingTransaction = transactionService.getTransactionById(transaction.getId());
        if (existingTransaction == null) {
            Transaction createdTransaction = transactionService.createTransaction(transaction);
            return ResponseEntity.ok(createdTransaction);
        }
        throw new Exceptions("transaction con id " + transaction.getId() + " ya existe.");
    }

    @Operation(summary = "Update an existing transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class)) }),
            @ApiResponse(responseCode = "404", description = "Transaction not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable int id, @RequestBody Transaction transaction) {
        Transaction updatedTransaction = transactionService.updateTransaction(id, transaction);
        return ResponseEntity.ok(updatedTransaction);
    }

    @Operation(summary = "Delete a transaction by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Transaction not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        boolean deleted = transactionService.deleteTransaction(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}