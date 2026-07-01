package com.sope.sopetran_click.service.user;

import com.sope.sopetran_click.dto.users.BookingRequestDTO;
import com.sope.sopetran_click.dto.users.BookingResponseDTO;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CategorysRepository categoryRepository;
    private final PaymentsRepository paymentsRepository;

    // Inyección por constructor (recomendada en lugar de @Autowired en los campos)
    public BookingServiceImpl(BookingRepository bookingRepository,
                              UserRepository userRepository,
                              CategorysRepository categoryRepository,
                              PaymentsRepository paymentsRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.paymentsRepository = paymentsRepository;

    }

    @Override
    @Transactional
    public BookingResponseDTO  createBooking(BookingRequestDTO dto) {
        Users user = userRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getIdUser()));

        Categorys category = categoryRepository.findById(dto.getIdCategory())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getIdCategory()));

        Booking booking = new Booking();
        booking.setTargetId(category.getIdCategory());
        booking.setUser(user);
        booking.setFechaReserva(dto.getFechaEjecucion());
        booking.setDescription(dto.getDescription());
        booking.setCost(dto.getMontoTotal());


        Booking bookingguardado = bookingRepository.save(booking);
        return convertToResponseDTO(bookingguardado);
    }

    @Override
    public BookingResponseDTO updateBooking(Long id, BookingRequestDTO dto) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));

        Users user = userRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getIdUser()));

        Categorys category = categoryRepository.findById(dto.getIdCategory())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getIdCategory()));

        Payments payments = paymentsRepository.findById(dto.getIdPayment())
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + dto.getIdPayment()));

        existing.setTargetId(category.getIdCategory());
        existing.setUser(user);
        existing.setCost(dto.getMontoTotal());
        existing.setTargetType(category.getName());
        existing.setPayment(payments.getIdBooking().getPayment());
        existing.setStatus(dto.getStatus());
        existing.setDescription(dto.getDescription());
        existing.setFechaReserva(dto.getFechaEjecucion());

        Booking bookingUpdate = bookingRepository.save(existing);

        return convertToResponseDTO(bookingUpdate);
    }

    @Override
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada");
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public BookingResponseDTO findById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
        return convertToResponseDTO(booking);
    }

    @Override
    public List<BookingResponseDTO> findAllByUserId(Long idUsers) {
        return bookingRepository.findAllByUser_IdUsers(idUsers).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> findAllByCategoryId(String targetType) {
        return bookingRepository.findBytargetType(targetType).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private BookingResponseDTO convertToResponseDTO(Booking booking) {
        BookingResponseDTO response = new BookingResponseDTO();
        response.setIdBooking(booking.getIdBooking()); // Ajusta según el nombre real de tu ID
        response.setUserName(booking.getUser().getName());
        response.setTargetId(booking.getTargetId()); // Asumiendo que tiene un campo 'name'
        response.setDescription(booking.getDescription());
        response.setFechaReserva(booking.getFechaReserva());
        response.setCost(booking.getCost().doubleValue());
        response.setStatus(booking.getStatus());
        return response;
    }
}