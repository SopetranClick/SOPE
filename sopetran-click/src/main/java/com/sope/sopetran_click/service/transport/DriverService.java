package com.sope.sopetran_click.service.transport;

import com.sope.sopetran_click.dto.transport.DriverRequestDTO;
import com.sope.sopetran_click.dto.transport.DriverResponseDTO;

import java.util.List;

public interface DriverService {

    DriverResponseDTO crear(DriverRequestDTO dto);
    DriverResponseDTO actualizar(Long id, DriverRequestDTO dto);
    void eliminar(Long id);
    DriverResponseDTO buscarPorId(Long id);
    List<DriverResponseDTO> listarTodos();
    List<DriverResponseDTO> listarPorTipoVehiculo(String tipoVehiculo);
}
