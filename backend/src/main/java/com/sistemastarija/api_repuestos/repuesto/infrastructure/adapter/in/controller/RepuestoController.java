package com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.in.controller;

import com.sistemastarija.api_repuestos.repuesto.application.port.in.CreateRepuestoUseCase;
import com.sistemastarija.api_repuestos.repuesto.application.port.in.DeleteRepuestoUseCase;
import com.sistemastarija.api_repuestos.repuesto.application.port.in.FindRepuestoUseCase;
import com.sistemastarija.api_repuestos.repuesto.application.port.in.UpdateRepuestoUseCase;
import com.sistemastarija.api_repuestos.repuesto.domain.model.Repuesto;
import com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.in.dto.RepuestoRequestDTO;
import com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.in.mapper.RepuestoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/repuesto")
public class RepuestoController {
    private final CreateRepuestoUseCase createRepuestoUseCase;
    private final UpdateRepuestoUseCase updateRepuestoUseCase;
    private final DeleteRepuestoUseCase deleteRepuestoUseCase;
    private final FindRepuestoUseCase findRepuestoUseCase;
    private final RepuestoMapper repuestoMapper;

    public RepuestoController(CreateRepuestoUseCase createRepuestoUseCase, UpdateRepuestoUseCase updateRepuestoUseCase, DeleteRepuestoUseCase deleteRepuestoUseCase, FindRepuestoUseCase findRepuestoUseCase, RepuestoMapper repuestoMapper) {
        this.createRepuestoUseCase = createRepuestoUseCase;
        this.updateRepuestoUseCase = updateRepuestoUseCase;
        this.deleteRepuestoUseCase = deleteRepuestoUseCase;
        this.findRepuestoUseCase = findRepuestoUseCase;
        this.repuestoMapper = repuestoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createRepuesto(@RequestBody RepuestoRequestDTO repuestoRequestDTO){
        Repuesto repuesto = repuestoMapper.toDomain(repuestoRequestDTO);
        createRepuestoUseCase.save(repuesto);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Repuesto registrado exitosamente");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updateRepuesto(@RequestBody RepuestoRequestDTO repuestoRequestDTO) {

        Repuesto repuesto = repuestoMapper.toDomain(repuestoRequestDTO);

        updateRepuestoUseCase.update(repuesto);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Venta actualizada exitosamente");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/findAll")
    public List<Repuesto> findAllVenta() {
        return findRepuestoUseCase.findAll();
    }

    @GetMapping("/findById/{id}")
    public Optional<Repuesto> findById(@PathVariable Integer id) {
        return findRepuestoUseCase.findById(id);
    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable Integer id) {
        deleteRepuestoUseCase.delete(id);
        return true;
    }


}
