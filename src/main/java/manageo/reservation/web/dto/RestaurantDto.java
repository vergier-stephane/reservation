package manageo.reservation.web.dto;

public class RestaurantDto {
    
    long id;
    int places;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

}
