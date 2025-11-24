package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.in.web.controller;

import com.sistemastarija.api_repuestos.venta.application.port.in.CreateVentaUseCase;
import com.sistemastarija.api_repuestos.venta.application.port.in.DeleteVentaUseCase;
import com.sistemastarija.api_repuestos.venta.application.port.in.FindVentaUseCase;
import com.sistemastarija.api_repuestos.venta.application.port.in.UpdateVentaUseCase;
import com.sistemastarija.api_repuestos.venta.domain.model.Venta;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.in.web.dto.VentaDTO;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.in.web.mapper.VentaMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {


    private final CreateVentaUseCase createVentaUseCase;
    private final UpdateVentaUseCase updateVentaUseCase;
    private final FindVentaUseCase findVentaUseCase;
    private final DeleteVentaUseCase deleteVentaUseCase;
    private final VentaMapper ventaMapper;

    public VentaController(CreateVentaUseCase createVentaUseCase, VentaMapper ventaMapper, UpdateVentaUseCase updateVentaUseCase, FindVentaUseCase findVentaUseCase, DeleteVentaUseCase deleteVentaUseCase) {
        this.createVentaUseCase = createVentaUseCase;
        this.ventaMapper = ventaMapper;

        this.updateVentaUseCase  = updateVentaUseCase;
        this.findVentaUseCase  = findVentaUseCase;
        this.deleteVentaUseCase = deleteVentaUseCase;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registrarVenta(@RequestBody VentaDTO ventaDTO) {
        // DTO a entidad de dominio
        Venta venta = ventaMapper.toDomain(ventaDTO);

        createVentaUseCase.save(venta);


        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Venta registrada exitosamente");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updateVenta(@RequestBody VentaDTO ventaDTO) {
        //DTO a entidad de dominio
        Venta venta = ventaMapper.toDomain(ventaDTO);

        updateVentaUseCase.update(venta.getIdVenta(), venta);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Venta actualizada exitosamente");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/findAll")
    public List<Venta> findAllVenta() {
        return findVentaUseCase.findAll();
    }

    @GetMapping("/findById/{id}")
    public Optional<Venta> findById(@PathVariable Integer id) {
        return findVentaUseCase.findById(id);
    }

    @GetMapping("/delete/{id_venta}")
    public boolean delete(@PathVariable Integer id_venta) {
        deleteVentaUseCase.delete(id_venta);
        return true;
    }


}