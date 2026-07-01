package com.sope.sopetran_click.service.trade;

import com.sope.sopetran_click.dto.trade.LocalRequestDTO;
import com.sope.sopetran_click.dto.trade.LocalResponseDTO;
import com.sope.sopetran_click.model.category.trade.Local;
import com.sope.sopetran_click.model.category.trade.Trades;
import com.sope.sopetran_click.repository.LocalRepository;
import com.sope.sopetran_click.repository.TradesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
@Service
public class LocalServiceImp implements LocalService{

    @Autowired
    private TradesRepository tradesRepository;
    @Autowired
    private LocalRepository localRepository;

    @Override
    public LocalResponseDTO crearLocal(LocalRequestDTO dto) {

        Trades trade = tradesRepository.findById(dto.getIdTrade())
                .orElseThrow(() -> new RuntimeException("Categoría de trade base no encontrada."));

        Local local = new Local();
        local.setName(dto.getNombre());
        local.setDescription(dto.getDescription());
        local.setContact(dto.getContacto());
        local.setType_local(dto.getTipoLocal());
        local.setAddress(dto.getDireccion());
        local.setIdTrades(trade);

        Local localGuardado = localRepository.save(local);

        return convertToResponseDTO(localGuardado);
    }



    @Override
    public LocalResponseDTO actualizarLocal(Long id, LocalRequestDTO dto) {
        Trades trade = tradesRepository.findById(dto.getIdTrade())
                .orElseThrow(() -> new RuntimeException("Categoría de trade base no encontrada."));

        Local localexiste = localRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: Local no encontrado por ID"));

        localexiste.setName(dto.getNombre());
        localexiste.setDescription(dto.getDescription());
        localexiste.setContact(dto.getContacto());
        localexiste.setIdTrades(trade);

        Local localActualizado = localRepository.save(localexiste);
        return convertToResponseDTO(localActualizado);
    }

    @Override
    public LocalResponseDTO buscarPorId(Long id) {
        Local local = localRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No se encontro local con este id"));
        return convertToResponseDTO(local);
    }

    @Override
    public List<LocalResponseDTO> listarTodos() {
        return localRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarLocal(Long id) {
        if (!localRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: local no encontrado por ID");

        }
        localRepository.deleteById(id);
        }

    private LocalResponseDTO convertToResponseDTO(Local local) {
        LocalResponseDTO response = new LocalResponseDTO();
        response.setIdLocal(local.getIdLocal());
        response.setNombre(local.getName());
        response.setDescription(local.getDescription());
        response.setContacto(local.getContact());

        // Obtenemos de forma segura la información de la categoría padre
        if (local.getIdTrades() != null) {
            response.setNombre(local.getIdTrades().getDescription());
        }
        return response;
    }
}
