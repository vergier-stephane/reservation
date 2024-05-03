package manageo.reservation.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.persistence.EntityNotFoundException;
import manageo.reservation.web.dto.CustomPageDto;
import manageo.reservation.web.dto.ReservationDto;
import manageo.reservation.web.dto.RestaurantDto;

@Service
public class RestaurantService {

    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    @Value( "${restaurant.api.url}" )
    private String restoApi;

    @Autowired
    RestTemplateBuilder builder;
    
    public RestaurantService(WebClient.Builder webClientBuilder,
        ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.lbFunction = lbFunction;
    }

    public Page<RestaurantDto> listRestaurant(Integer page, Integer size) {

        CustomPageDto<RestaurantDto> restaurants = WebClient.builder()
            .filter(lbFunction)
            .build().get().uri(restoApi).accept( MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<CustomPageDto<RestaurantDto>>() {

            }).block();
        return restaurants;
    }

    public Optional<RestaurantDto> getRestaurantById(Long restaurantId) {

        RestTemplate restTemplate = builder.errorHandler( new RestTemplateResponseErrorHandler() ).build();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(restoApi)
            .pathSegment( "detail" , Long.toString( restaurantId ));
            try {
                RestaurantDto restaurant = restTemplate.getForObject( uriBuilder.toUriString(), RestaurantDto.class );
                return Optional.of( restaurant );
            } catch( EntityNotFoundException e ) {
                return Optional.empty();
            }
    }

    public boolean isAvailable(ReservationDto reservation) {
        
        RestTemplate restTemplate = new RestTemplate();
        
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(restoApi)
            .pathSegment( "availability" );
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ReservationDto> httpEntity = new HttpEntity<ReservationDto>( reservation , headers );

        ResponseEntity<Boolean> available = restTemplate.postForEntity( uriBuilder.toUriString(), 
                        httpEntity , 
                        Boolean.class );
        Boolean avail = available.getBody();
        return avail != null ? avail : false;
    }

}
