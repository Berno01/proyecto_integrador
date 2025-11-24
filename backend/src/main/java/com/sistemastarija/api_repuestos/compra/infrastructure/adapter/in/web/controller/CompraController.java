package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.in.web.controller;

import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.in.web.dto.CompraDTO;
import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.in.web.mapper.CompraMapper;
import com.sistemastarija.api_repuestos.compra.application.port.in.CreateCompraUseCase;
import com.sistemastarija.api_repuestos.compra.application.port.in.DeleteCompraUseCase;
import com.sistemastarija.api_repuestos.compra.application.port.in.FindCompraUseCase;
import com.sistemastarija.api_repuestos.compra.application.port.in.UpdateCompraUseCase;
import com.sistemastarija.api_repuestos.compra.domain.model.Compra;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/compras")
public class CompraController {


    private final CreateCompraUseCase createCompraUseCase;
    private final UpdateCompraUseCase updateCompraUseCase;
    private final FindCompraUseCase findCompraUseCase;
    private final DeleteCompraUseCase deleteCompraUseCase;
    private final CompraMapper compraMapper;

    public CompraController(CreateCompraUseCase createCompraUseCase, CompraMapper compraMapper, UpdateCompraUseCase updateCompraUseCase, FindCompraUseCase findCompraUseCase, DeleteCompraUseCase deleteCompraUseCase) {
        this.createCompraUseCase = createCompraUseCase;
        this.compraMapper = compraMapper;

        this.updateCompraUseCase  = updateCompraUseCase;
        this.findCompraUseCase  = findCompraUseCase;
        this.deleteCompraUseCase = deleteCompraUseCase;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registrarCompra(@RequestBody CompraDTO compraDTO) {
        // DTO a entidad de dominio
        Compra compra = compraMapper.toDomain(compraDTO);

        createCompraUseCase.save(compra);


        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Compra registrada exitosamente");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updateCompra(@RequestBody CompraDTO compraDTO) {
        //DTO a entidad de dominio
        Compra compra = compraMapper.toDomain(compraDTO);

        updateCompraUseCase.update(compra.getIdCompra(), compra);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Compra actualizada exitosamente");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/findAll")
    public List<Compra> findAllCompra() {
        return findCompraUseCase.findAll();
    }

    @GetMapping("/findById/{id}")
    public Optional<Compra> findById(@PathVariable Integer id) {
        return findCompraUseCase.findById(id);
    }

    @GetMapping("/delete/{id_compra}")
    public boolean delete(@PathVariable Integer id_compra) {
        deleteCompraUseCase.delete(id_compra);
        return true;
    }


}