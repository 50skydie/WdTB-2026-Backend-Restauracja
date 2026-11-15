package pl.wsb.restauracja.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.restauracja.booking.entity.Reservation;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomer_LastName(String lastName);
}