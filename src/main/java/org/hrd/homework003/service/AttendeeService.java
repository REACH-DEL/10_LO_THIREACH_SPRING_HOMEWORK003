package org.hrd.homework003.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.hrd.homework003.model.dto.request.AttendeeRequest;
import org.hrd.homework003.model.entity.Attendee;

import java.util.List;

public interface AttendeeService {
    Attendee addAttendee(AttendeeRequest dattendeeRequest);

    List<Attendee> getAllAttendees(Integer offset, Integer limit);

    Attendee getAttendeebyId(Integer attendeeId);

    Attendee deleteAttendeebyId(Integer attendeeId);

    Attendee updateAttendeebyId(Integer attendeeId, AttendeeRequest attendeeRequest);
}
