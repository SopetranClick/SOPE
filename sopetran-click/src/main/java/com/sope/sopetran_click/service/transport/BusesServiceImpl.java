package com.sope.sopetran_click.service.transport;

import com.sope.sopetran_click.dto.transport.BusesRequestDTO;
import com.sope.sopetran_click.dto.transport.BusesResponseDTO;
import com.sope.sopetran_click.model.category.transport.Buses;
import com.sope.sopetran_click.model.category.transport.Transports;
import com.sope.sopetran_click.repository.BusesRepository;
import com.sope.sopetran_click.repository.TransportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusesServiceImpl implements BuseService {

    @Autowired
    private BusesRepository busesRepository;

    @Autowired
    private TransportsRepository transportsRepository;

    @Override
    @Transactional
    public BusesResponseDTO crear(BusesRequestDTO dto) {
        Transports transporte = transportsRepository.findById(dto.getIdTransporte())
                .orElseThrow(() -> new RuntimeException("Categoría de transporte base no encontrada."));

        Buses bus = new Buses();
        bus.setName(dto.getEmpresa());
        bus.setRoute(dto.getRuta());
        bus.setSchedule(dto.getHorarios());
        bus.setIdTransports(transporte);
        // Opcional si el DTO tuviera descripción, lo añadirías aquí
        // bus.setDescription("");

        Buses busGuardado = busesRepository.save(bus);

        return convertToResponseDTO(busGuardado);
    }

    @Override
    @Transactional
    public BusesResponseDTO actualizar(Long id, BusesRequestDTO dto) {
        Buses busExistente = busesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus no encontrado"));

        Transports transporte = transportsRepository.findById(dto.getIdTransporte())
                .orElseThrow(() -> new RuntimeException("Categoría de transporte base no encontrada."));

        busExistente.setName(dto.getEmpresa());
        busExistente.setRoute(dto.getRuta());
        busExistente.setSchedule(dto.getHorarios());
        busExistente.setIdTransports(transporte);

        Buses busActualizado = busesRepository.save(busExistente);

        return convertToResponseDTO(busActualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public BusesResponseDTO buscarPorId(Long id) {
        Buses bus = busesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus no encontrado"));
        return convertToResponseDTO(bus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusesResponseDTO> listarTodos() {
        return busesRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!busesRepository.existsById(id)) {
            throw new RuntimeException("Bus no encontrado");
        }
        busesRepository.deleteById(id);
    }

    private BusesResponseDTO convertToResponseDTO(Buses bus) {
        BusesResponseDTO dto = new BusesResponseDTO();
        dto.setIdBus(bus.getIdBuses());
        dto.setEmpresa(bus.getName());
        dto.setRuta(bus.getRoute());
        dto.setHorarios(bus.getSchedule());
        return dto;
    }
}