package manageo.reservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import manageo.reservation.persistence.entities.ReservationEntity;
import manageo.reservation.persistence.repositories.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository repository;

    public boolean isAvailable( ReservationEntity reservation ) {
        return true;
    }

    public ReservationEntity saveReservation( ReservationEntity reservation ) {
        return repository.save( reservation );
    }

    public Page<ReservationEntity> listReservations( Pageable page ) {
        return repository.findAll( page );
    }

}
