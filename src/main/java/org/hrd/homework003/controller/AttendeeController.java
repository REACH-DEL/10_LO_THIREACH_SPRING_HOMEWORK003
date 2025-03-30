package org.hrd.homework003.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Delete;
import org.hrd.homework003.model.dto.request.AttendeeRequest;
import org.hrd.homework003.model.dto.response.ApiReponse;
import org.hrd.homework003.model.entity.Attendee;
import org.hrd.homework003.service.AttendeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/attendees")
public class AttendeeController {

    private final AttendeeService attendeeService;

    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @PostMapping
    public ResponseEntity<ApiReponse<Attendee>> addAttendee(@RequestBody @Valid AttendeeRequest attendeeRequest){
        Attendee attendee = attendeeService.addAttendee(attendeeRequest);
        ApiReponse<Attendee> response = ApiReponse.<Attendee>builder()
                .message("The attendee has been successfully added.")
                .status(HttpStatus.CREATED)
                .payload(attendee)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiReponse<List<Attendee>>> getAttendees(@RequestParam(value = "offset", defaultValue = "1") Integer offset, @RequestParam(value = "limit", defaultValue = "10") @Valid Integer limit){
        List<Attendee> attendees = attendeeService.getAllAttendees(offset, limit);
        ApiReponse<List<Attendee>> response = ApiReponse.<List<Attendee>>builder()
                .message("The attendee has been successfully fetched.")
                .status(HttpStatus.OK)
                .payload(attendees)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{attendee-id}")
    public ResponseEntity<ApiReponse<Attendee>> getAttendeeById(@PathVariable("attendee-id") Integer attendeeId) {
        Attendee attendee = attendeeService.getAttendeebyId(attendeeId);
        ApiReponse<Attendee> response = ApiReponse.<Attendee>builder()
                .message("The attendee has been successfully fetched.")
                .status(HttpStatus.OK)
                .payload(attendee)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{attendee-id}")
    public ResponseEntity<ApiReponse<Attendee>> deleteAttendeeById(@PathVariable("attendee-id") Integer attendeeId) {
        Attendee attendee = attendeeService.deleteAttendeebyId(attendeeId);
        ApiReponse<Attendee> response = ApiReponse.<Attendee>builder()
                .message("The attendee has been successfully deleted.")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{attendee-id}")
    public ResponseEntity<ApiReponse<Attendee>> updateAttendeeById(@PathVariable("attendee-id") Integer attendeeId, @RequestBody @Valid AttendeeRequest attendeeRequest) {
        Attendee attendee = attendeeService.updateAttendeebyId(attendeeId, attendeeRequest);
        ApiReponse<Attendee> response = ApiReponse.<Attendee>builder()
                .message("The attendee has been successfully updated.")
                .status(HttpStatus.OK)
                .payload(attendee)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
