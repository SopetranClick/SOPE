package com.sope.sopetran_click.service.transport;

import com.sope.sopetran_click.dto.transport.MotorbikeRequestDTO;
import com.sope.sopetran_click.dto.transport.MotorbikeResponseDTO;
import java.util.List;

public interface MotorbikeService {
    // CRUD estándar
    MotorbikeResponseDTO crear(MotorbikeRequestDTO dto);
    MotorbikeResponseDTO actualizar(Long id, MotorbikeRequestDTO dto);
    void eliminar(Long id);
    MotorbikeResponseDTO buscarPorId(Long id);
    List<MotorbikeResponseDTO> listarTodos();

    // Métodos específicos por relaciones
    List<MotorbikeResponseDTO> buscarPorNombreAsociacion(String nombre);
    List<MotorbikeResponseDTO> buscarPorIdTransporte(Long idTransporte);
}