package org.tenpopt.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tenpopt.Entity.Tenpista;
import org.tenpopt.Service.TenpistaService;
import org.tenpopt.Utils.Exceptions;

import java.util.List;

@RestController
@RequestMapping("/tenpista")
public class TenpistaController {

    @Autowired
    private TenpistaService tenpistaService;

    @Operation(summary = "Get all tenpistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tenpistas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tenpista.class)) })
    })
    @GetMapping
    public List<Tenpista> getAllTenpistas() {
        return tenpistaService.getAllTenpistas();
    }

    @Operation(summary = "Get a tenpista by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tenpista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tenpista.class)) }),
            @ApiResponse(responseCode = "404", description = "Tenpista not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Tenpista> getTenpistaById(@PathVariable int id) {
        Tenpista tenpista = tenpistaService.getTenpistaById(id);
        return ResponseEntity.ok(tenpista);
    }

    @Operation(summary = "Get a tenpista by nroCuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tenpista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tenpista.class)) }),
            @ApiResponse(responseCode = "404", description = "Tenpista not found",
                    content = @Content)
    })
    @GetMapping("/nroCuenta/{nroCuenta}")
    public ResponseEntity<Tenpista> getTenpistaByNroCuenta(@PathVariable int nroCuenta) {
        Tenpista tenpista = tenpistaService.getTenpistaByNroCuenta(nroCuenta);
        return ResponseEntity.ok(tenpista);
    }

    @Operation(summary = "Create a new tenpista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenpista created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tenpista.class)) })
    })
    @PostMapping
    public ResponseEntity<Tenpista> createTenpista(@RequestBody Tenpista tenpista) {
        Tenpista existingTenpista = tenpistaService.getTenpistaByNroCuenta(tenpista.getNroCuenta());
        if(existingTenpista == null) {
            Tenpista createdTenpista = tenpistaService.createTenpista(tenpista);
            return ResponseEntity.ok(createdTenpista);
        } else if (existingTenpista.getId() == tenpista.getId() || existingTenpista.getNroCuenta() == tenpista.getNroCuenta()) {
            throw new Exceptions("Tenpista con nroCuenta " + tenpista.getNroCuenta() + " ya existe.");
        }
        return null;
    }


    @Operation(summary = "Update an existing tenpista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenpista updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tenpista.class)) }),
            @ApiResponse(responseCode = "404", description = "Tenpista not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Tenpista> updateTenpista(@PathVariable int id, @RequestBody Tenpista tenpista) {
        Tenpista updatedTenpista = tenpistaService.updateTenpista(id, tenpista);
        return ResponseEntity.ok(updatedTenpista);
    }

    @Operation(summary = "Delete a tenpista by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tenpista deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Tenpista not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenpista(@PathVariable int id) {
        boolean deleted = tenpistaService.deleteTenpista(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}