package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.in.web.controller;

import com.sistemastarija.api_repuestos.venta.application.service.RepuestoVentaService;
import com.sistemastarija.api_repuestos.venta.domain.model.Repuesto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
public class RepuestoVentaController {
    private final RepuestoVentaService repuestoVentaService;

    public RepuestoVentaController(RepuestoVentaService repuestoVentaService){
        this.repuestoVentaService = repuestoVentaService;
    }

    @GetMapping("/repuesto/findAll")
    public List<Repuesto> findAll() {
        return repuestoVentaService.findAll();
    }

    @GetMapping("/repuesto/findById/{idRepuesto}")
    public Optional<Repuesto> findById(@PathVariable Integer idRepuesto) {
        return repuestoVentaService.findById(idRepuesto);
    }

}
