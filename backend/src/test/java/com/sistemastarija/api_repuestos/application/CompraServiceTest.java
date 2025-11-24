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
        // Arranque
        String fechaHora = "2025-10-15 23:36:20";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaHoraObj = LocalDateTime.parse(fechaHora, formatter);
        Repuesto repuestoExistente = new Repuesto(1, 10);
        DetalleVenta detalle = new DetalleVenta(2, 50.0, 50.00, 1, 45.8);
        Venta ventaDeEntrada = new Venta(null, "Cliente X",fechaHoraObj, List.of(detalle));


        // comportamiento de los mocks
        when(repuestoPersistantPort.findById(1)).thenReturn(Optional.of(repuestoExistente));
        when(ventaPersistantPort.save(any(Venta.class))).thenReturn(ventaDeEntrada);


        // Act
        Venta ventaGuardada = ventaService.save(ventaDeEntrada);


        // Verificamos los resultados
        assertNotNull(ventaGuardada);
        assertEquals(100.0, ventaGuardada.getTotal());
        assertEquals(8, repuestoExistente.getStockRepuesto());

        verify(repuestoPersistantPort, times(1)).findById(1);
        verify(repuestoPersistantPort, times(1)).save(repuestoExistente);
        verify(ventaPersistantPort, times(1)).save(ventaDeEntrada);
    }





}
