package com.sistemastarija.api_repuestos.application;


import com.sistemastarija.api_repuestos.venta.application.port.out.RepuestoPersistantPort;
import com.sistemastarija.api_repuestos.venta.application.port.out.VentaPersistantPort;
import com.sistemastarija.api_repuestos.venta.application.service.VentaService;
import com.sistemastarija.api_repuestos.venta.domain.model.DetalleVenta;
import com.sistemastarija.api_repuestos.venta.domain.model.Repuesto;
import com.sistemastarija.api_repuestos.venta.domain.model.Venta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {

    @Mock
    private RepuestoPersistantPort repuestoPersistantPort;
    @Mock
    private VentaPersistantPort ventaPersistantPort;

    @InjectMocks
    private VentaService ventaService;

    @Test
    void deberiaGuardarVentaCorrectamenteCuandoHayStock() {
    }





}
