package org.hrd.homework003.service.implement;

import org.hrd.homework003.exception.BiggerThanZeroException;
import org.hrd.homework003.exception.NotFoundException;
import org.hrd.homework003.model.dto.request.EventRequest;
import org.hrd.homework003.model.entity.Event;
import org.hrd.homework003.model.entity.Venue;
import org.hrd.homework003.repository.EventRepository;
import org.hrd.homework003.service.AttendeeService;
import org.hrd.homework003.service.EventService;
import org.hrd.homework003.service.VenueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventServiceImp implements EventService {

    private final EventRepository eventRepository;
    private final VenueService venueService;
    private final AttendeeService attendeeService;

    public EventServiceImp(EventRepository eventRepository, VenueService venueService, AttendeeService attendeeService) {
        this.eventRepository = eventRepository;
        this.venueService = venueService;
        this.attendeeService = attendeeService;
    }

    @Override
    @Transactional
    public Event addEvent(EventRequest eventRequest) {
        Venue venue = venueService.getVenuebyId(eventRequest.getVenueId());
        if (venue == null) {
            throw new NotFoundException("Venue Id " + eventRequest.getVenueId() + " not found");
        } else if (eventRequest.getVenueId() < 1){
            throw new BiggerThanZeroException(Map.of("venueId", "VenueId must be greater than 0"));
        }
        for(Integer attendeeId : eventRequest.getAttendeeId()) {
            if (attendeeService.getAttendeebyId(attendeeId) == null) {
                throw new NotFoundException("Attendee Id " + attendeeId + " not found");
            } else if (attendeeId < 1) {
                throw new BiggerThanZeroException(Map.of("attendeeId", "AttendeeId must be greater than 0"));
            }
        }
        Integer eventId = eventRepository.insertEvent(eventRequest);
        for(Integer attendeeId : eventRequest.getAttendeeId()) {
            eventRepository.insertEventAttendee(eventId, attendeeId);
        }

        return eventRepository.findEventById(eventId);
    }

    @Override
    public List<Event> getAllEvents(Integer offset, Integer limit) {
        Map<String, String> errors = new HashMap<>();
        if (offset < 1) {
            errors.put("offset", "Offset must be greater than 0");
        }
        if (limit < 1) {
            errors.put("limit", "Limit must be greater than 0");
        }
        if (!errors.isEmpty()) {
            throw new BiggerThanZeroException(errors);
        }
        return eventRepository.findAllEvents(offset, limit);
    }

    @Override
    public Event getEventbyId(Integer eventId) {
        Event event = eventRepository.findEventById(eventId);
        if (eventId < 1){
            throw new BiggerThanZeroException(Map.of("eventId","Event Id must be greater than 0"));
        }else if (event == null){
            throw new NotFoundException("Event Id " + eventId + " not found");
        }
        return event;
    }

    @Override
    @Transactional
    public Event deleteEventbyId(Integer eventId) {
        Event event = eventRepository.findEventById(eventId);
        if (eventId < 1){
            throw new BiggerThanZeroException(Map.of("eventId","Event Id must be greater than 0"));
        }else if (event == null){
            throw new NotFoundException("Event Id " + eventId + " not found");
        }
        Integer deleteEventId = eventRepository.removeEventById(eventId);
        eventRepository.removeEventAttendee(deleteEventId);
        return event;
    }

    @Override
    @Transactional
    public Event updateEventbyId(Integer eventId, EventRequest eventRequest) {
        if (venueService.getVenuebyId(eventRequest.getVenueId()) == null) {
            throw new NotFoundException("Venue Id " + eventRequest.getVenueId() + " not found");
        } else if (eventRequest.getVenueId() < 1){
            throw new BiggerThanZeroException(Map.of("venueId", "VenueId must be greater than 0"));
        }
        for(Integer attendeeId : eventRequest.getAttendeeId()) {
            if (attendeeService.getAttendeebyId(attendeeId) == null) {
                throw new NotFoundException("Attendee Id " + attendeeId + " not found");
            } else if (attendeeId < 1) {
                throw new BiggerThanZeroException(Map.of("attendeeId", "AttendeeId must be greater than 0"));
            }
        }
        Event event = eventRepository.findEventById(eventId);
        if (eventId < 1){
            throw new BiggerThanZeroException(Map.of("eventId","Event Id must be greater than 0"));
        }else if (event == null){
            throw new NotFoundException("Event Id " + eventId + " not found");
        }
        eventRepository.modifyEventById(eventId, eventRequest);
        eventRepository.removeEventAttendee(eventId);
        for(Integer attendeeId : eventRequest.getAttendeeId()) {
            eventRepository.insertEventAttendee(eventId, attendeeId);
        }
        return eventRepository.findEventById(eventId);
    }
}
