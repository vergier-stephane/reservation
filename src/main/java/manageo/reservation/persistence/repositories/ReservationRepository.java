package manageo.reservation.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import manageo.reservation.persistence.entities.ReservationEntity;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {
    
}
