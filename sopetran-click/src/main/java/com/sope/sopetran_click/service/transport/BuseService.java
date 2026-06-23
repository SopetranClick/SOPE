package com.sope.sopetran_click.service.transport;

import com.sope.sopetran_click.dto.transport.BusesRequestDTO;
import com.sope.sopetran_click.dto.transport.BusesResponseDTO;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de Buses.
 */
public interface BuseService {

    /**
     * Registra un nuevo bus en el sistema.
     * @param dto Datos del bus a crear.
     * @return El objeto respuesta del bus creado.
     */
    BusesResponseDTO crear(BusesRequestDTO dto);

    /**
     * Actualiza un bus existente mediante su ID.
     * @param id ID del bus a actualizar.
     * @param dto Nuevos datos para el bus.
     * @return El objeto respuesta con los datos actualizados.
     */
    BusesResponseDTO actualizar(Long id, BusesRequestDTO dto);

    /**
     * Busca un bus específico por su ID.
     * @param id ID del bus a buscar.
     * @return El objeto respuesta del bus encontrado.
     */
    BusesResponseDTO buscarPorId(Long id);

    /**
     * Lista todos los buses disponibles.
     * @return Una lista de objetos respuesta de buses.
     */
    List<BusesResponseDTO> listarTodos();

    /**
     * Elimina un bus del sistema.
     * @param id ID del bus a eliminar.
     */
    void eliminar(Long id);
}