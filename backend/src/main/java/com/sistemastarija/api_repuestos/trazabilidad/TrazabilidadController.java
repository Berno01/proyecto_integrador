package com.sistemastarija.api_repuestos.trazabilidad;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trazabilidad")
@RequiredArgsConstructor
public class TrazabilidadController {

    private final MovimientoRepository movimientoRepository;

    @GetMapping
    public List<Movimiento> obtenerTrazabilidad() {
        return movimientoRepository.findAll();
    }
}
