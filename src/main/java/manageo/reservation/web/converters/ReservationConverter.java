package manageo.reservation.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import manageo.reservation.persistence.entities.ReservationEntity;
import manageo.reservation.web.dto.ReservationDto;

public class ReservationConverter {
    
    public ReservationDto convert( ReservationEntity entity ) {
        ReservationDto dto = new ReservationDto();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setEmail(entity.getEmail());
        dto.setRestaurantId(entity.getRestaurantId());
        return dto;
    }

    public ReservationEntity convert( ReservationDto dto ) {
        ReservationEntity entity = new ReservationEntity();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setEmail(dto.getEmail());
        entity.setRestaurantId(dto.getRestaurantId());
        return entity;
    }

    public Page<ReservationDto> convert( Page<ReservationEntity> entities ) {
        List<ReservationDto> dtos = new ArrayList<ReservationDto>();
        entities.forEach( entity -> {
            dtos.add( this.convert(entity) );
        });
        Page<ReservationDto> page = new PageImpl<ReservationDto>( dtos , PageRequest.of( entities.getNumber() , entities.getSize() ) , entities.getTotalElements() );
        return page;
    }

}
