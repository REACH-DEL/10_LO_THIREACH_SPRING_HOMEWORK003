package org.hrd.homework003.service;

import jakarta.validation.Valid;
import org.hrd.homework003.model.dto.request.EventRequest;
import org.hrd.homework003.model.entity.Event;

import java.util.List;

public interface EventService {
    Event addEvent(@Valid EventRequest eventRequest);

    List<Event> getAllEvents(Integer offset, @Valid Integer limit);

    Event getEventbyId(Integer eventId);

    Event deleteEventbyId(Integer eventId);

    Event updateEventbyId(Integer eventId, @Valid EventRequest eventRequest);
}
