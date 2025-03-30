package org.hrd.homework003.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hrd.homework003.model.entity.Attendee;
import org.hrd.homework003.model.entity.Venue;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventRequest {
    @NotBlank(message = "Event Name can not be blank!")
    private String eventName;
    @NotNull(message = "Event Date can not be Null!")
    private LocalDateTime eventDate;
    @NotNull(message = "Venue ID can not be Null!")
    private Integer venueId;
    @NotEmpty(message = "List Attendee ID can not be empty!")
    private List<Integer> attendeeId;
}
