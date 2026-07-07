package com.sope.sopetran_click.service.transport;

import com.sope.sopetran_click.dto.transport.MotorbikeRequestDTO;
import com.sope.sopetran_click.dto.transport.MotorbikeResponseDTO;
import com.sope.sopetran_click.model.category.transport.Motorbike;
import com.sope.sopetran_click.model.category.transport.Transports;
import com.sope.sopetran_click.repository.MotorbikeRepository;
import com.sope.sopetran_click.repository.TransportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotorbikeServiceImpl implements MotorbikeService{

    @Autowired
    private MotorbikeRepository motorbikeRepository;

    @Autowired
    private TransportsRepository transportsRepository;

    @Override
    @Transactional
    public MotorbikeResponseDTO crear(MotorbikeRequestDTO dto) {
        Transports transporte = transportsRepository.findById(dto.getIdTransporte())
                .orElseThrow(() -> new RuntimeException("Transporte padre no encontrado"));

        Motorbike moto = new Motorbike();
        mapearDtoAEntidad(dto, moto, transporte);
        return mapearEntidadADto(motorbikeRepository.save(moto));
    }

    @Override
    @Transactional
    public MotorbikeResponseDTO actualizar(Long id, MotorbikeRequestDTO dto) {
        Motorbike moto = motorbikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mototaxi no encontrado"));
        Transports transporte = transportsRepository.findById(dto.getIdTransporte())
                .orElseThrow(() -> new RuntimeException("Transporte padre no encontrado"));

        mapearDtoAEntidad(dto, moto, transporte);
        return mapearEntidadADto(motorbikeRepository.save(moto));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!motorbikeRepository.existsById(id)) throw new RuntimeException("Mototaxi no encontrado");
        motorbikeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MotorbikeResponseDTO buscarPorId(Long id) {
        return motorbikeRepository.findById(id).map(this::mapearEntidadADto)
                .orElseThrow(() -> new RuntimeException("Mototaxi no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MotorbikeResponseDTO> listarTodos() {
        return motorbikeRepository.findAll().stream().map(this::mapearEntidadADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MotorbikeResponseDTO> buscarPorNombreAsociacion(String nombre) {
        return motorbikeRepository.findByNameContainingIgnoreCase(nombre).stream()
                .map(this::mapearEntidadADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MotorbikeResponseDTO> buscarPorIdTransporte(Long idTransporte) {
        return motorbikeRepository.findByIdTransports_IdTransports(idTransporte).stream()
                .map(this::mapearEntidadADto).collect(Collectors.toList());
    }

    private void mapearDtoAEntidad(MotorbikeRequestDTO dto, Motorbike moto, Transports transporte) {
        moto.setName(dto.getAsociacionNombre());
        moto.setZonaCobertura(dto.getZonaCobertura());
        moto.setContact(dto.getContacto());
        moto.setIdTransports(transporte);
    }

    private MotorbikeResponseDTO mapearEntidadADto(Motorbike moto) {
        MotorbikeResponseDTO dto = new MotorbikeResponseDTO();
        dto.setIdMoto(moto.getIdMotorbike());
        dto.setAsociacionNombre(moto.getName());
        dto.setContacto(moto.getContact());
        dto.setZonaCobertura(moto.getZonaCobertura());
        return dto;
    }
}

