package org.hrd.homework003.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VenueRequest {
    @NotBlank(message = "Venue Name can not be blank!")
    private String venueName;
    @NotBlank(message = "Location can not be blank!")
    private String location;
}
