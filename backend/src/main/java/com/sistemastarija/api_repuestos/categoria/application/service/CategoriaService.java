package com.sistemastarija.api_repuestos.categoria.application.service;

import com.sistemastarija.api_repuestos.categoria.application.port.in.CreateCategoriaUseCase;
import com.sistemastarija.api_repuestos.categoria.application.port.in.DeleteCategoriaUseCase;
import com.sistemastarija.api_repuestos.categoria.application.port.in.FindCategoriaUseCase;
import com.sistemastarija.api_repuestos.categoria.application.port.in.UpdateCategoriaUseCase;
import com.sistemastarija.api_repuestos.categoria.application.port.out.CategoriaPersistantPort;
import com.sistemastarija.api_repuestos.categoria.domain.exception.CategoriaNotFoundException;
import com.sistemastarija.api_repuestos.categoria.domain.model.Categoria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService implements CreateCategoriaUseCase, DeleteCategoriaUseCase, FindCategoriaUseCase, UpdateCategoriaUseCase {

    private final CategoriaPersistantPort categoriaPersistantPort;



    @Override
    public Categoria save(Categoria categoria) {
        return categoriaPersistantPort.save(categoria);
    }

    @Override
    public Boolean delete(Integer idCategoria) {
        Optional<Categoria> categoriaOp = categoriaPersistantPort.findById(idCategoria);
        Categoria categoria = categoriaOp.orElseThrow(() ->
                new CategoriaNotFoundException("La categoria con ID " + idCategoria + " no fue encontrada.")
        );
        categoria.setEstadoCategoria(false);
        categoriaPersistantPort.save(categoria);
        return true;
    }

    @Override
    public Optional<Categoria> findById(Integer idCategoria) {
        return categoriaPersistantPort.findById(idCategoria);

    }

    @Override
    public List<Categoria> findAll() {
        return categoriaPersistantPort.findAll();
    }

    @Override
    public Categoria update(Integer idCategoria, Categoria categoria) {
        return categoriaPersistantPort.findById(idCategoria)
                .map(savedCategoria ->{
                    savedCategoria.setNombreCategoria(categoria.getNombreCategoria());
                    return categoriaPersistantPort.save(savedCategoria);
                })
                .orElseThrow(()-> new CategoriaNotFoundException("La categoria con id: " + idCategoria + " no fue encontrada."));
    }
}
