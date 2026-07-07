package com.sope.sopetran_click.service.transport;

import com.sope.sopetran_click.dto.transport.DriverRequestDTO;
import com.sope.sopetran_click.dto.transport.DriverResponseDTO;
import com.sope.sopetran_click.model.category.transport.Driver;
import com.sope.sopetran_click.model.category.transport.Transports;
import com.sope.sopetran_click.repository.DriverRepository;
import com.sope.sopetran_click.repository.TransportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private TransportsRepository transportsRepository;

    @Override
    @Transactional
    public DriverResponseDTO crear(DriverRequestDTO dto) {
        Transports transporte = transportsRepository.findById(dto.getIdTransporte())
                .orElseThrow(() -> new RuntimeException("Categoría de transporte base no encontrada."));

        Driver driver = new Driver();
        mapearDtoAEntidad(dto, driver, transporte);
        return convertToResponseDTO(driverRepository.save(driver));
    }

    @Override
    @Transactional
    public DriverResponseDTO actualizar(Long id, DriverRequestDTO dto) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
        Transports transporte = transportsRepository.findById(dto.getIdTransporte())
                .orElseThrow(() -> new RuntimeException("Categoría de transporte base no encontrada."));

        mapearDtoAEntidad(dto, driver, transporte);
        return convertToResponseDTO(driverRepository.save(driver));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new RuntimeException("Conductor no encontrado");
        }
        driverRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public DriverResponseDTO buscarPorId(Long id) {
        return driverRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverResponseDTO> listarTodos() {
        return driverRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverResponseDTO> listarPorTipoVehiculo(String tipoVehiculo) {
        return driverRepository.findByTipoVehiculo(tipoVehiculo).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private void mapearDtoAEntidad(DriverRequestDTO dto, Driver driver, Transports transporte) {
        driver.setNombre(dto.getNombre());
        driver.setPlaca(dto.getPlaca());
        driver.setMarca(dto.getMarca());
        driver.setAnio(dto.getAnio());
        driver.setTelefono(dto.getTelefono());
        driver.setDisponible(dto.getDisponible() != null ? dto.getDisponible() : true);
        driver.setTipoVehiculo(dto.getTipoVehiculo());
        driver.setIdTransports(transporte);
    }

    private DriverResponseDTO convertToResponseDTO(Driver driver) {
        DriverResponseDTO dto = new DriverResponseDTO();
        dto.setIdDriver(driver.getIdDriver());
        dto.setNombre(driver.getNombre());
        dto.setPlaca(driver.getPlaca());
        dto.setMarca(driver.getMarca());
        dto.setAnio(driver.getAnio());
        dto.setTelefono(driver.getTelefono());
        dto.setDisponible(driver.getDisponible());
        dto.setTipoVehiculo(driver.getTipoVehiculo());
        if (driver.getIdTransports() != null) {
            dto.setIdTransporte(driver.getIdTransports().getIdTransports());
        }
        return dto;
    }
}
