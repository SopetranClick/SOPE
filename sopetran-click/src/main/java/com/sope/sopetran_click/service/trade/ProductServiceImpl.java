package com.sope.sopetran_click.service.trade;

import com.sope.sopetran_click.dto.trade.ProductRequestDTO;
import com.sope.sopetran_click.dto.trade.ProductResponseDTO;
import com.sope.sopetran_click.model.category.trade.Local;
import com.sope.sopetran_click.model.category.trade.Product;
import com.sope.sopetran_click.repository.LocalRepository;
import com.sope.sopetran_click.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LocalRepository localRepository;

    @Override
    public ProductResponseDTO crearProducto(ProductRequestDTO dto) {
        Local local = localRepository.findById(dto.getIdLocal()).orElseThrow(() -> new RuntimeException("Local no encontrado"));

        Product product = new Product();
        product.setLocal(local);
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());

        Product productguardado = productRepository.save(product);
        return convertToResponseDTO(productguardado);
    }

    @Override
    public ProductResponseDTO actualizarProducto(Long id, ProductRequestDTO dto) {
        Product productexiste = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: producto no encontrado por ID"));
        Local local = localRepository.findById(dto.getIdLocal()).orElseThrow(() -> new RuntimeException("Local no encontrado"));

        productexiste.setName(dto.getName());
        productexiste.setPrice(dto.getPrice());
        productexiste.setStock(dto.getStock());
        productexiste.setDescription(dto.getDescription());
        productexiste.setImageUrl(dto.getImageUrl());
        productexiste.setLocal(local);

        Product productActualizado = productRepository.save(productexiste);
        return convertToResponseDTO(productActualizado);
    }

    @Override
    public ProductResponseDTO buscarPorId(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: producto no encontrado por ID"));
        return convertToResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> listarTodos() {
        return productRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarProducto(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: producto no encontrado por ID");
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDTO> listarProductosPorLocal(Long localId) {
        return productRepository.findByLocalIdLocal(localId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private ProductResponseDTO convertToResponseDTO(Product product) {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setIdProduct(product.getIdProduct());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setDescription(product.getDescription());
        response.setStock(product.getStock());
        response.setImageUrl(product.getImageUrl());
        response.setIdLocal(product.getLocal().getIdLocal());

        // Obtenemos de forma segura la información de la categoría padre
        if (product.getLocal() != null) {
            response.setLocalName(product.getLocal().getName());
        }
        return response;
    }
}
