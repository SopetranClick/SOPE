package com.sope.sopetran_click.service.user;

import com.sope.sopetran_click.dto.users.PaymentsRequestDTO;
import com.sope.sopetran_click.dto.users.PaymentsResponseDTO;
import java.util.List;

public interface PaymentService {

    /**
     * Crea un nuevo registro de pago.
     */
    PaymentsResponseDTO createPayment(PaymentsRequestDTO dto);

    /**
     * Actualiza el estado de un pago existente.
     */
    PaymentsResponseDTO updatePaymentStatus(Long id, String status);

    PaymentsResponseDTO updatePayment(Long id, PaymentsRequestDTO dto);

    /**
     * Obtiene un pago por su ID.
     */
    PaymentsResponseDTO findById(Long id);

    /**
     * Lista todos los pagos de un usuario específico.
     */
    List<PaymentsResponseDTO> findAllByUserId(Long userId);

    void deletePayments(long id);
}