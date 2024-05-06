package manageo.reservation.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
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

        CustomPageDto<RestaurantDto> restaurants = WebClient
            .builder()
            .filter(lbFunction)
            .build()
            .get()
            .uri(restoApi)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<CustomPageDto<RestaurantDto>>() {

            }).block();
        return restaurants;
    }

    public Optional<RestaurantDto> getRestaurantById(Long restaurantId) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(restoApi)
            .pathSegment( "detail" , Long.toString( restaurantId ));
        try {
            RestaurantDto restaurant = WebClient
                .builder()
                .filter(lbFunction)
                .build()
                .get()
                .uri( uriBuilder.toUriString() )
                .accept( MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(RestaurantDto.class)
                .block();
            return Optional.of( restaurant );
        } catch( EntityNotFoundException e ) {
            return Optional.empty();
        }
    }

    public boolean isAvailable(ReservationDto reservation) {
        
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(restoApi)
            .pathSegment( "availability" );

        Boolean available = WebClient
            .builder()
            .filter(lbFunction)
            .build()
            .post()
            .uri( uriBuilder.toUriString() )
            .headers(h -> h.setContentType(MediaType.APPLICATION_JSON))
            .accept( MediaType.APPLICATION_JSON)
            .bodyValue(reservation)
            .retrieve()
            .bodyToMono(Boolean.class)
            .block();
                    
        return available != null ? available : false;
    }

}
