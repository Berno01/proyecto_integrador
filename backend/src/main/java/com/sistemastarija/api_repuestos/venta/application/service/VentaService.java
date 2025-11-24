package com.sistemastarija.api_repuestos.venta.application.service;

import com.sistemastarija.api_repuestos.venta.application.port.in.CreateVentaUseCase;
import com.sistemastarija.api_repuestos.venta.application.port.in.DeleteVentaUseCase;
import com.sistemastarija.api_repuestos.venta.application.port.in.FindVentaUseCase;
import com.sistemastarija.api_repuestos.venta.application.port.in.UpdateVentaUseCase;
import com.sistemastarija.api_repuestos.venta.application.port.out.RepuestoPersistantPort;
import com.sistemastarija.api_repuestos.venta.domain.exception.RepuestoNotFoundException;
import com.sistemastarija.api_repuestos.venta.domain.exception.VentaFailedException;
import com.sistemastarija.api_repuestos.venta.domain.model.DetalleVenta;
import com.sistemastarija.api_repuestos.venta.domain.model.Repuesto;
import com.sistemastarija.api_repuestos.venta.domain.model.Venta;
import com.sistemastarija.api_repuestos.venta.application.port.out.VentaPersistantPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VentaService implements CreateVentaUseCase, UpdateVentaUseCase, FindVentaUseCase, DeleteVentaUseCase {
    private final VentaPersistantPort ventaPersistantPort;
    private final RepuestoPersistantPort repuestoPersistantPort;





    @Transactional
    @Override
    public Venta save(Venta venta) {
        for (DetalleVenta detalle : venta.getDetalleVenta()) {

            Integer repuestoId = detalle.getIdRepuesto();
            Optional<Repuesto> opcionalRepuesto = repuestoPersistantPort.findById(repuestoId);
            Repuesto repuesto = opcionalRepuesto.orElseThrow(() ->
                    new RepuestoNotFoundException("El repuesto con ID " + repuestoId + " no fue encontrado.")
            );
            try {
                repuesto.decreaseStockRepuesto(detalle.getCantidad());
            } catch (RepuestoNotFoundException e) {
                throw new VentaFailedException("No se pudo completar la venta: " + e.getMessage(), e);
            }
            repuestoPersistantPort.save(repuesto);
        }

        return ventaPersistantPort.save(venta);
    }

    @Transactional
    @Override
    public Venta update(Integer idVenta, Venta venta) {

        Optional<Venta> opcionalVenta = ventaPersistantPort.findById(idVenta);
        Venta ventaAntigua = opcionalVenta.orElseThrow(() ->
                new VentaFailedException("La venta con ID " + idVenta + " no fue encontrada.")
        );

        //devolvemos los repuestos
        for (DetalleVenta detalle : ventaAntigua.getDetalleVenta()) {

            Integer repuestoId = detalle.getIdRepuesto();
            Optional<Repuesto> opcionalRepuesto = repuestoPersistantPort.findById(repuestoId);
            Repuesto repuesto = opcionalRepuesto.orElseThrow(() ->
                    new RepuestoNotFoundException("El repuesto con ID " + repuestoId + " no fue encontrado.")
            );
            try {
                repuesto.encreaseStockRepuesto(detalle.getCantidad());
            } catch (RepuestoNotFoundException e) {
                throw new VentaFailedException("No se pudo completar la actualizacion de la venta: " + e.getMessage(), e);
            }
            repuestoPersistantPort.save(repuesto);
        }
        //vendemos los repuestos de la nueva venta
        for (DetalleVenta detalle : venta.getDetalleVenta()) {

            Integer repuestoId = detalle.getIdRepuesto();
            Optional<Repuesto> opcionalRepuesto = repuestoPersistantPort.findById(repuestoId);
            Repuesto repuesto = opcionalRepuesto.orElseThrow(() ->
                    new RepuestoNotFoundException("El repuesto con ID " + repuestoId + " no fue encontrado.")
            );
            try {
                repuesto.decreaseStockRepuesto(detalle.getCantidad());
            } catch (RepuestoNotFoundException e) {
                throw new VentaFailedException("No se pudo completar la venta: " + e.getMessage(), e);
            }
            repuestoPersistantPort.save(repuesto);
        }
        venta.setIdVenta(idVenta);
        return ventaPersistantPort.save(venta);
    }

    @Override
    public Optional<Venta> findById(Integer idVenta) {
        Optional<Venta> opcionalVenta = ventaPersistantPort.findById(idVenta);

        return opcionalVenta;
    }

    @Override
    public List<Venta> findAll() {
        return ventaPersistantPort.findAll();

    }

    @Transactional
    @Override
    public void delete(Integer idVenta) {
        Optional<Venta> opcionalVenta = ventaPersistantPort.findById(idVenta);
        Venta venta = opcionalVenta.orElseThrow(() ->
                new VentaFailedException("La venta con ID " + idVenta + " no fue encontrada.")
        );
        //devolvemos los repuestos
        for (DetalleVenta detalle : venta.getDetalleVenta()) {

            Integer repuestoId = detalle.getIdRepuesto();
            Optional<Repuesto> opcionalRepuesto = repuestoPersistantPort.findById(repuestoId);
            Repuesto repuesto = opcionalRepuesto.orElseThrow(() ->
                    new RepuestoNotFoundException("El repuesto con ID " + repuestoId + " no fue encontrado.")
            );
            try {
                repuesto.encreaseStockRepuesto(detalle.getCantidad());
            } catch (RepuestoNotFoundException e) {
                throw new VentaFailedException("No se pudo devolver los repuestos " + e.getMessage(), e);
            }
            repuestoPersistantPort.save(repuesto);
        }
        venta.setEstadoVenta(false);
        ventaPersistantPort.save(venta);
    }
}
