package com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.in.web.controller;

import com.sistemastarija.api_repuestos.categoria.application.port.in.CreateCategoriaUseCase;
import com.sistemastarija.api_repuestos.categoria.application.port.in.DeleteCategoriaUseCase;
import com.sistemastarija.api_repuestos.categoria.application.port.in.FindCategoriaUseCase;
import com.sistemastarija.api_repuestos.categoria.application.port.in.UpdateCategoriaUseCase;
import com.sistemastarija.api_repuestos.categoria.domain.model.Categoria;
import com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.in.web.dto.CategoriaDTO;
import com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.in.web.mapper.CategoriaMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
    private final CreateCategoriaUseCase createCategoriaUseCase;
    private final UpdateCategoriaUseCase updateCategoriaUseCase;
    private final DeleteCategoriaUseCase deleteCategoriaUseCase;
    private final FindCategoriaUseCase findCategoriaUseCase;
    private final CategoriaMapper categoriaMapper;

    public CategoriaController(CreateCategoriaUseCase createCategoriaUseCase, UpdateCategoriaUseCase updateCategoriaUseCase, DeleteCategoriaUseCase deleteCategoriaUseCase, FindCategoriaUseCase findCategoriaUseCase,  CategoriaMapper categoriaMapper) {
        this.createCategoriaUseCase = createCategoriaUseCase;
        this.updateCategoriaUseCase = updateCategoriaUseCase;
        this.deleteCategoriaUseCase = deleteCategoriaUseCase;
        this.findCategoriaUseCase = findCategoriaUseCase;
        this.categoriaMapper = categoriaMapper;
    }


    @PostMapping
    public ResponseEntity<Map<String, String>> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toCategoriaDomain(categoriaDTO);
        createCategoriaUseCase.save(categoria);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Categoria registrada exitosamente");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updateCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toCategoriaDomain(categoriaDTO);
        updateCategoriaUseCase.update(categoria.getIdCategoria(), categoria);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Categoria actualizada exitosamente");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/findAll")
    public List<Categoria> findAllVenta() {
        return findCategoriaUseCase.findAll();
    }

    @GetMapping("/findById/{id}")
    public Optional<Categoria> findById(@PathVariable Integer id) {
        return findCategoriaUseCase.findById(id);
    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable Integer id) {
        deleteCategoriaUseCase.delete(id);
        return true;
    }

}
