package com.sope.sopetran_click.controller.trade;


import com.sope.sopetran_click.dto.trade.ProductRequestDTO;
import com.sope.sopetran_click.dto.trade.ProductResponseDTO;
import com.sope.sopetran_click.service.trade.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.listarTodos();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.buscarPorId(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO product = productService.actualizarProducto(id, dto);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO product = productService.crearProducto(dto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }



}
