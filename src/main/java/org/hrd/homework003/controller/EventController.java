package org.hrd.homework003.controller;

import jakarta.validation.Valid;
import org.hrd.homework003.model.dto.request.EventRequest;
import org.hrd.homework003.model.dto.response.ApiReponse;
import org.hrd.homework003.model.entity.Event;
import org.hrd.homework003.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<ApiReponse<Event>> addEvent(@RequestBody @Valid EventRequest eventRequest){
        Event event = eventService.addEvent(eventRequest);
        ApiReponse<Event> response = ApiReponse.<Event>builder()
                .message("The event has been successfully added.")
                .status(HttpStatus.CREATED)
                .payload(event)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiReponse<List<Event>>> getEvents(@RequestParam(value = "offset", defaultValue = "1") Integer offset, @RequestParam(value = "limit", defaultValue = "10") @Valid Integer limit){
        List<Event> events = eventService.getAllEvents(offset, limit);
        ApiReponse<List<Event>> response = ApiReponse.<List<Event>>builder()
                .message("The events have been successfully fetched.")
                .status(HttpStatus.OK)
                .payload(events)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{event-id}")
    public ResponseEntity<ApiReponse<Event>> getEventById(@PathVariable("event-id") Integer eventId) {
        Event event = eventService.getEventbyId(eventId);
        ApiReponse<Event> response = ApiReponse.<Event>builder()
                .message("The event has been successfully fetched.")
                .status(HttpStatus.OK)
                .payload(event)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{event-id}")
    public ResponseEntity<ApiReponse<Event>> deleteEventById(@PathVariable("event-id") Integer eventId) {
        Event event = eventService.deleteEventbyId(eventId);
        ApiReponse<Event> response = ApiReponse.<Event>builder()
                .message("The event has been successfully deleted.")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{event-id}")
    public ResponseEntity<ApiReponse<Event>> updateEventById(@PathVariable("event-id") Integer eventId, @RequestBody @Valid EventRequest eventRequest) {
        Event event = eventService.updateEventbyId(eventId, eventRequest);
        ApiReponse<Event> response = ApiReponse.<Event>builder()
                .message("The event has been successfully updated.")
                .status(HttpStatus.OK)
                .payload(event)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
