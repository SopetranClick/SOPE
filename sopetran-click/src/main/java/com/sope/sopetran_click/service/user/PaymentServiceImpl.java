package com.sope.sopetran_click.service.user;

import com.sope.sopetran_click.dto.users.PaymentsRequestDTO;
import com.sope.sopetran_click.dto.users.PaymentsResponseDTO;
import com.sope.sopetran_click.model.Categorys;
import com.sope.sopetran_click.model.Users;
import com.sope.sopetran_click.model.user.Booking;
import com.sope.sopetran_click.model.user.Payments;
import com.sope.sopetran_click.repository.BookingRepository;
import com.sope.sopetran_click.repository.CategorysRepository;
import com.sope.sopetran_click.repository.PaymentsRepository;
import com.sope.sopetran_click.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentsRepository paymentsRepository;
    private final UserRepository userRepository;
    private final CategorysRepository categoryRepository;
    private final BookingRepository bookingRepository;

    public PaymentServiceImpl(PaymentsRepository paymentsRepository,
                              UserRepository userRepository,
                              CategorysRepository categoryRepository,
                              BookingRepository bookingRepository) {
        this.paymentsRepository = paymentsRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public PaymentsResponseDTO createPayment(PaymentsRequestDTO dto) {
        Users user = userRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getIdUser()));

        Categorys category = categoryRepository.findById(dto.getIdCategory())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getIdCategory()));

        Booking booking = (dto.getIdBooking() != null)
                ? bookingRepository.findById(dto.getIdBooking()).orElse(null)
                : null;

        Payments payment = new Payments();
        payment.setUser(user);
        payment.setIdCategory(category);
        payment.setIdBooking(booking);
        payment.setCost(dto.getCost().doubleValue());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setState(Payments.status.PENDING);

        Payments savedPayment = paymentsRepository.save(payment);
        return convertToResponseDTO(savedPayment);
    }

    @Override
    @Transactional
    public PaymentsResponseDTO updatePaymentStatus(Long id, String status) {
        Payments existing = paymentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));


        existing.setState(Payments.status.valueOf(status));
        Payments updatedPayment = paymentsRepository.save(existing);

        return convertToResponseDTO(updatedPayment);
    }

    @Override
    public PaymentsResponseDTO updatePayment(Long id, PaymentsRequestDTO dto) {
        Payments payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));

        // Actualizamos las relaciones
        Users user = userRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Categorys category = categoryRepository.findById(dto.getIdCategory())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Booking booking = (dto.getIdBooking() != null)
                ? bookingRepository.findById(dto.getIdBooking()).orElse(null) : null;

        payment.setUser(user);
        payment.setIdCategory(category);
        payment.setIdBooking(booking);
        payment.setCost(dto.getCost().doubleValue());
        payment.setPaymentMethod(dto.getPaymentMethod());

        return convertToResponseDTO(paymentsRepository.save(payment));
    }

    @Override
    public PaymentsResponseDTO findById(Long id) {
        Payments payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
        return convertToResponseDTO(payment);
    }

    @Override
    public List<PaymentsResponseDTO> findAllByUserId(Long idUsers) {
        // Asumiendo que tu repositorio tiene un método findByIdUserId o similar
        return paymentsRepository.findByUser_idUsers(idUsers).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePayments(long id) {
        if (!paymentsRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Pago no encontrado con ID: " + id);
        }
        paymentsRepository.deleteById(id);
    }

    private PaymentsResponseDTO convertToResponseDTO(Payments payment) {
        PaymentsResponseDTO response = new PaymentsResponseDTO();
        response.setIdPayment(payment.getIdPayment());
        response.setUserName(payment.getUser().getName());
        response.setCategoryName(payment.getIdCategory().getName());
        response.setIdBooking(payment.getIdBooking() != null ? payment.getIdBooking().getIdBooking() : null);
        response.setCost(java.math.BigDecimal.valueOf(payment.getCost()));
        response.setPaymentDate(payment.getPaymentDate());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setStatus(payment.getState().name());
        return response;
    }
}