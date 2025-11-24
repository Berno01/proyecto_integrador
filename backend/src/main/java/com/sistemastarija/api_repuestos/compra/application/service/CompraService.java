package com.sistemastarija.api_repuestos.compra.application.service;

import com.sistemastarija.api_repuestos.compra.application.port.in.CreateCompraUseCase;
import com.sistemastarija.api_repuestos.compra.application.port.in.DeleteCompraUseCase;
import com.sistemastarija.api_repuestos.compra.application.port.in.FindCompraUseCase;
import com.sistemastarija.api_repuestos.compra.application.port.in.UpdateCompraUseCase;
import com.sistemastarija.api_repuestos.compra.application.port.out.CompraPersistantPort;
import com.sistemastarija.api_repuestos.compra.domain.exception.CompraFailedException;
import com.sistemastarija.api_repuestos.compra.domain.exception.RepuestoFailedException;
import com.sistemastarija.api_repuestos.compra.domain.model.Compra;
import com.sistemastarija.api_repuestos.compra.domain.model.DetalleCompra;
import com.sistemastarija.api_repuestos.compra.application.port.out.RepuestoPersistantPort;
import com.sistemastarija.api_repuestos.compra.domain.model.Repuesto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CompraService implements CreateCompraUseCase, UpdateCompraUseCase, FindCompraUseCase, DeleteCompraUseCase {
    private final CompraPersistantPort compraPersistantPort;
    private final RepuestoPersistantPort repuestoPersistantPort;


    @Transactional
    @Override
    public Compra save(Compra compra) {
        for (DetalleCompra detalle : compra.getDetalleCompra()) {

            Integer repuestoId = detalle.getIdRepuesto();
            Optional<Repuesto> opcionalRepuesto = repuestoPersistantPort.findById(repuestoId);
            Repuesto repuesto = opcionalRepuesto.orElseThrow(() ->
                    new RepuestoFailedException("El repuesto con ID " + repuestoId + " no fue encontrado.")
            );
            try {
                repuesto.encreaseStockRepuesto(detalle.getCantidad());
            } catch (RepuestoFailedException e) {
                throw new CompraFailedException("No se pudo completar la compra: " + e.getMessage(), e);
            }
            repuesto.setCostoRepuesto(detalle.getCostoRepuesto());
            repuestoPersistantPort.save(repuesto);
        }

        return compraPersistantPort.save(compra);
    }

    @Transactional
    @Override
    public Compra update(Integer idCompra, Compra compra) {

        Optional<Compra> opcionalCompra = compraPersistantPort.findById(idCompra);
        Compra compraAntigua = opcionalCompra.orElseThrow(() ->
                new CompraFailedException("La compra con ID " + idCompra + " no fue encontrada.")
        );

        //devolvemos los repuestos al proveedor
        for (DetalleCompra detalle : compraAntigua.getDetalleCompra()) {

            Integer repuestoId = detalle.getIdRepuesto();
            Optional<Repuesto> opcionalRepuesto = repuestoPersistantPort.findById(repuestoId);
            Repuesto repuesto = opcionalRepuesto.orElseThrow(() ->
                    new RepuestoFailedException("El repuesto con ID " + repuestoId + " no fue encontrado.")
            );
            try {
                repuesto.decreaseStockRepuesto(detalle.getCantidad());
            } catch (RepuestoFailedException e) {
                throw new CompraFailedException("No se pudo completar la actualizacion de la compra: " + e.getMessage(), e);
            }
            repuestoPersistantPort.save(repuesto);
        }
        //compramos los repuestos de la nueva compra
        for (DetalleCompra detalle : compra.getDetalleCompra()) {

            Integer repuestoId = detalle.getIdRepuesto();
            Optional<Repuesto> opcionalRepuesto = repuestoPersistantPort.findById(repuestoId);
            Repuesto repuesto = opcionalRepuesto.orElseThrow(() ->
                    new RepuestoFailedException("El repuesto con ID " + repuestoId + " no fue encontrado.")
            );
            try {
                repuesto.encreaseStockRepuesto(detalle.getCantidad());
            } catch (RepuestoFailedException e) {
                throw new CompraFailedException("No se pudo completar la compra: " + e.getMessage(), e);
            }
            repuesto.setCostoRepuesto(detalle.getCostoRepuesto());
            repuestoPersistantPort.save(repuesto);
        }
        compra.setIdCompra(idCompra);
        return compraPersistantPort.save(compra);
    }

    @Override
    public Optional<Compra> findById(Integer idCompra) {
        Optional<Compra> opcionalCompra = compraPersistantPort.findById(idCompra);

        return opcionalCompra;
    }

    @Override
    public List<Compra> findAll() {
        return compraPersistantPort.findAll();

    }

    @Transactional
    @Override
    public void delete(Integer idCompra) {
        Optional<Compra> opcionalCompra = compraPersistantPort.findById(idCompra);
        Compra compra = opcionalCompra.orElseThrow(() ->
                new CompraFailedException("La compra con ID " + idCompra + " no fue encontrada.")
        );
        //devolvemos los repuestos al proveedor
        for (DetalleCompra detalle : compra.getDetalleCompra()) {

            Integer repuestoId = detalle.getIdRepuesto();
            Optional<Repuesto> opcionalRepuesto = repuestoPersistantPort.findById(repuestoId);
            Repuesto repuesto = opcionalRepuesto.orElseThrow(() ->
                    new RepuestoFailedException("El repuesto con ID " + repuestoId + " no fue encontrado.")
            );
            try {
                repuesto.decreaseStockRepuesto(detalle.getCantidad());
            } catch (RepuestoFailedException e) {
                throw new CompraFailedException("No se pudo devolver los repuestos " + e.getMessage(), e);
            }
            repuestoPersistantPort.save(repuesto);
        }
        compra.setEstadoCompra(false);
        compraPersistantPort.save(compra);
    }
}
