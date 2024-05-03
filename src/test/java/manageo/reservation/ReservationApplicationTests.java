package manageo.reservation;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import manageo.reservation.persistence.entities.ReservationEntity;
import manageo.reservation.persistence.repositories.ReservationRepository;

@SpringBootTest
class ReservationApplicationTests {

	@Autowired
	ReservationRepository repo;

	@Test
	void contextLoads() {
		
		ReservationEntity entity = new ReservationEntity();
		entity.setDate(new Date());
		entity.setEmail("mail");
		entity.setRestaurantId(10);

		ReservationEntity saved = repo.save(entity);
		System.out.println( "Saved: " + saved.getId() );

		System.out.println("nb elements in repo: " + repo.count() );

		Page<ReservationEntity> page = repo.findAll( PageRequest.of( 0 , 10 ) );
		System.out.println( "Page: " + page.getNumberOfElements() );
	}

}
