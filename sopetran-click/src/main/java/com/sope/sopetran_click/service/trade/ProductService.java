package com.sope.sopetran_click.service.trade;

import com.sope.sopetran_click.dto.trade.ProductRequestDTO;
import com.sope.sopetran_click.dto.trade.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO crearProducto(ProductRequestDTO dto);
    ProductResponseDTO actualizarProducto(Long id, ProductRequestDTO dto);
    ProductResponseDTO buscarPorId(Long id);
    List<ProductResponseDTO> listarTodos();
    void eliminarProducto(Long id);
    List<ProductResponseDTO> listarProductosPorLocal(Long localId);
}
