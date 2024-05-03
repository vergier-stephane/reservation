package manageo.reservation.web.dto;

import java.util.Date;

public class ReservationDto {
    
    long id;
    String email;
    Date date;
    long restaurantId;

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getEmail() {
        return email;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

}
