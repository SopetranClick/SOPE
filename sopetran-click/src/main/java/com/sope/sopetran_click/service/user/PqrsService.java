package com.sope.sopetran_click.service.user;

import com.sope.sopetran_click.dto.user.PqrsRequestDTO;
import com.sope.sopetran_click.dto.user.PqrsResponseDTO;

import java.util.List;

public interface PqrsService {

    PqrsResponseDTO crear(PqrsRequestDTO dto);
    List<PqrsResponseDTO> listar();
}
