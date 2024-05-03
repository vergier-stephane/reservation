package manageo.reservation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import manageo.reservation.web.dto.ReservationDto;
import manageo.reservation.web.dto.RestaurantDto;

@SpringBootTest
public class RestaurantServiceTests {
    
    @Autowired
    RestaurantService service;

    @Test
    void listAllRestaurants() {

        Page<RestaurantDto> page = service.listRestaurant(0, 10);

        System.out.println( "Page " + page.getNumberOfElements() );

        assertTrue( page.getNumberOfElements() == 5 );
    }

    @Test
    void getRestaurant() {

        Optional<RestaurantDto> restaurant = service.getRestaurantById( 1l );

        System.out.println( "Restaurant " + restaurant );

        assertTrue( restaurant.isPresent() );
        assertEquals( restaurant.get().getId() , 1 );
    }

    @Test
    void isRestaurantAvailable() {

        ReservationDto reservation = new ReservationDto();
        reservation.setDate( new Date() );
        reservation.setEmail("mail@test.com");
        reservation.setRestaurantId(1);

        boolean available = service.isAvailable( reservation );

        if( available ) {
            System.out.println( "Restaurant is available");
        } else {
            System.out.println( "Restaurant is NOT available");
        }


        assertTrue( available );

    }

}
