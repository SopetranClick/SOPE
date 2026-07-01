package com.sope.sopetran_click.controller.accomodation;

import com.sope.sopetran_click.dto.accommodation.EstateRequestDTO;
import com.sope.sopetran_click.dto.accommodation.EstateResponseDTO;
import com.sope.sopetran_click.service.accommodation.EstateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Fincas en SopetranClick.
 * Delega el manejo de errores al GlobalExceptionHandler activo en el Canvas.
 */
@RestController
@RequestMapping("/api/estates")
public class EstateController {

    private final EstateService estateService;

    // Inyección de dependencias recomendada por constructor
    public EstateController(EstateService estateService) {
        this.estateService = estateService;
    }

    /**
     * Crea una nueva finca en el sistema.
     * La anotación @Valid activa la validación del DTO y dispara
     * automáticamente la excepción MethodArgumentNotValidException si faltan datos.
     */
    @PostMapping
    public ResponseEntity<EstateResponseDTO> crearFinca(@Valid @RequestBody EstateRequestDTO dto) {
        EstateResponseDTO nuevaFinca = estateService.crearFinca(dto);
        return new ResponseEntity<>(nuevaFinca, HttpStatus.CREATED);
    }

    /**
     * Actualiza una finca existente por su ID.
     * Si el ID de la URL no es de tipo numérico, se disparará MethodArgumentTypeMismatchException.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EstateResponseDTO> actualizarFinca(
            @PathVariable Long id,
            @Valid @RequestBody EstateRequestDTO dto) {
        EstateResponseDTO fincaActualizada = estateService.actualizarFinca(id, dto);
        return ResponseEntity.ok(fincaActualizada);
    }

    /**
     * Obtiene los detalles de una finca por su ID único.
     * Si el ID no existe en la BD, la capa de servicio lanzará un RuntimeException
     * con el mensaje "Finca no encontrada", el cual se traducirá automáticamente a un status 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EstateResponseDTO> buscarPorId(@PathVariable Long id) {
        EstateResponseDTO finca = estateService.buscarPorId(id);
        return ResponseEntity.ok(finca);
    }

    /**
     * Lista todas las fincas registradas en el sistema.
     */
    @GetMapping
    public ResponseEntity<List<EstateResponseDTO>> listarTodas() {
        List<EstateResponseDTO> fincas = estateService.listarTodos();
        return ResponseEntity.ok(fincas);
    }

    /**
     * Elimina una finca por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFinca(@PathVariable Long id) {
        estateService.eliminarFinca(id);
        return ResponseEntity.noContent().build(); // Retorna un status 204 No Content
    }
}