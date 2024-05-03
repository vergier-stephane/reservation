package manageo.reservation.web.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import manageo.reservation.persistence.entities.ReservationEntity;
import manageo.reservation.web.dto.ReservationDto;

public class ReservationConverterTests {
    
    ReservationConverter converter = new ReservationConverter();

    void checkDtoAndEntityAreEqual( ReservationDto dto , ReservationEntity entity ) {
        assertEquals( dto.getId() , entity.getId() );
        assertEquals( dto.getDate() , entity.getDate() );
        assertEquals( dto.getEmail() , entity.getEmail() );
        assertEquals( dto.getRestaurantId() , entity.getRestaurantId() );
    }

    @Test
    void testConvertDtoToEntity() {
        ReservationDto dto = new ReservationDto();
        dto.setId(1);
        dto.setEmail("test@mail.com");
        dto.setDate( new Date() );
        dto.setRestaurantId( 12 );
        ReservationEntity entity = converter.convert(dto);
        this.checkDtoAndEntityAreEqual(dto, entity);
    }

    @Test
    void testConvertEntityToDto() {
        ReservationEntity entity = new ReservationEntity();
        entity.setId(1);
        entity.setEmail("test@mail.com");
        entity.setDate( new Date() );
        entity.setRestaurantId( 12 );
        ReservationDto dto = converter.convert(entity);
        this.checkDtoAndEntityAreEqual(dto, entity);
    }

    @Test
    void testConvertPageEntitytoPageDto() {
        List<ReservationEntity> list = new ArrayList<>();
        ReservationEntity entity = new ReservationEntity();
        entity.setId(1);
        entity.setEmail("test@mail.com");
        entity.setDate( new Date() );
        entity.setRestaurantId( 12 );
        list.add(entity);
        Page<ReservationEntity> page = new PageImpl<>( list , PageRequest.of( 0, 10 ), 100 );
        Page<ReservationDto> result = converter.convert(page);
        assertEquals( page.getSize() , result.getSize() );
        assertEquals( page.getTotalPages(), page.getTotalPages());
        assertEquals( page.getTotalElements(), page.getTotalElements());
    }

}
