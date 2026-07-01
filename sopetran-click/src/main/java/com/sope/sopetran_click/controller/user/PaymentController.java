package com.sope.sopetran_click.controller.user;

import com.sope.sopetran_click.dto.users.PaymentsRequestDTO;
import com.sope.sopetran_click.dto.users.PaymentsResponseDTO;
import com.sope.sopetran_click.service.user.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentsResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentsResponseDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<PaymentsResponseDTO> createPayment(
            @Valid @RequestBody PaymentsRequestDTO dto) {
        return new ResponseEntity<>(paymentService.createPayment(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentsResponseDTO> updatePayment(
            @PathVariable Long id, @Valid @RequestBody PaymentsRequestDTO dto) {
        return ResponseEntity.ok(paymentService.updatePayment(id, dto));
    }

    /** PATCH /api/payments/{id}/status?status=PAY → Cambia solo el estado */
    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentsResponseDTO> updateStatus(
            @PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayments(id);
        return ResponseEntity.noContent().build();
    }
}