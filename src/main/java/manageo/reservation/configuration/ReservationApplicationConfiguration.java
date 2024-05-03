package manageo.reservation.configuration;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import reactor.core.publisher.Flux;
import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;

@Configuration
public class ReservationApplicationConfiguration {
    
    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
      return new ReservationApplictionInstanceListSuppler("reservation");
    }

    class ReservationApplictionInstanceListSuppler implements ServiceInstanceListSupplier {

        private final String serviceId;
      
        ReservationApplictionInstanceListSuppler(String serviceId) {
          this.serviceId = serviceId;
        }
      
        @Override
        public String getServiceId() {
          return serviceId;
        }
      
        @Override
        public Flux<List<ServiceInstance>> get() {
          return Flux.just(Arrays
              .asList(new DefaultServiceInstance(serviceId + "1", serviceId, "localhost", 8090, false),
                  new DefaultServiceInstance(serviceId + "2", serviceId, "localhost", 9092, false),
                  new DefaultServiceInstance(serviceId + "3", serviceId, "localhost", 9999, false)));
        }
      }

}
