package com.sope.sopetran_click.service.user;

import com.sope.sopetran_click.dto.user.PqrsRequestDTO;
import com.sope.sopetran_click.dto.user.PqrsResponseDTO;
import com.sope.sopetran_click.model.user.Pqrs;
import com.sope.sopetran_click.repository.PqrsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PqrsServiceImpl implements PqrsService {

    @Autowired
    private PqrsRepository pqrsRepository;

    @Override
    @Transactional
    public PqrsResponseDTO crear(PqrsRequestDTO dto) {
        Pqrs pqrs = new Pqrs();
        pqrs.setTipo(dto.getTipo());
        pqrs.setNombre(dto.getNombre());
        pqrs.setEmail(dto.getEmail());
        pqrs.setTelefono(dto.getTelefono());
        pqrs.setAsunto(dto.getAsunto());
        pqrs.setMensaje(dto.getMensaje());

        Pqrs pqrsGuardada = pqrsRepository.save(pqrs);
        return convertToResponseDTO(pqrsGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PqrsResponseDTO> listar() {
        return pqrsRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private PqrsResponseDTO convertToResponseDTO(Pqrs pqrs) {
        PqrsResponseDTO response = new PqrsResponseDTO();
        response.setIdPqrs(pqrs.getIdPqrs());
        response.setTipo(pqrs.getTipo());
        response.setNombre(pqrs.getNombre());
        response.setAsunto(pqrs.getAsunto());
        response.setEstado(pqrs.getEstado());
        response.setCreadoEn(pqrs.getCreadoEn());
        return response;
    }
}
