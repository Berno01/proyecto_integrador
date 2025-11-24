package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.in.web.controller;

import com.sistemastarija.api_repuestos.compra.application.service.RepuestoCompraService;
import com.sistemastarija.api_repuestos.compra.domain.model.Repuesto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compras")
public class RepuestoCompraController {
    private final RepuestoCompraService repuestoCompraService;

    public RepuestoCompraController(RepuestoCompraService repuestoCompraService){
        this.repuestoCompraService = repuestoCompraService;
    }

    @GetMapping("/repuesto/findAll")
    public List<Repuesto> findAll() {
        return repuestoCompraService.findAll();
    }

    @GetMapping("/repuesto/findById/{idRepuesto}")
    public Optional<Repuesto> findById(@PathVariable Integer idRepuesto) {
        return repuestoCompraService.findById(idRepuesto);
    }

}
