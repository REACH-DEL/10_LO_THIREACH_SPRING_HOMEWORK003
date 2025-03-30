package org.hrd.homework003.service.implement;

import org.hrd.homework003.exception.BiggerThanZeroException;
import org.hrd.homework003.exception.NotFoundException;
import org.hrd.homework003.model.dto.request.AttendeeRequest;
import org.hrd.homework003.model.entity.Attendee;
import org.hrd.homework003.repository.AttendeeRepository;
import org.hrd.homework003.service.AttendeeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendeeServiceImp implements AttendeeService {

    private final AttendeeRepository attendeeRepository;

    public AttendeeServiceImp(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    @Override
    public Attendee addAttendee(AttendeeRequest attendeeRequest) {
        return attendeeRepository.insertAttendee(attendeeRequest);
    }

    @Override
    public List<Attendee> getAllAttendees(Integer offset, Integer limit) {
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
        return attendeeRepository.findAllAttendees(offset, limit);
    }

    @Override
    public Attendee getAttendeebyId(Integer attendeeId) {
        if (attendeeId < 1){
            throw new BiggerThanZeroException(Map.of("attendeeId", "AttendeeId must be greater than 0"));
        }
        Attendee attendee = attendeeRepository.findAttendeeById(attendeeId);
        if (attendee == null) {
            throw new NotFoundException("Attendee Id " + attendeeId + " not found");
        }
        return attendee;
    }

    @Override
    public Attendee deleteAttendeebyId(Integer attendeeId) {
        if (attendeeId < 1){
            throw new BiggerThanZeroException(Map.of("attendeeId", "AttendeeId must be greater than 0"));
        }
        Attendee attendee = attendeeRepository.removeAttendeeById(attendeeId);
        if (attendee == null) {
            throw new NotFoundException("Delete fail! Attendee Id " + attendeeId + " not found");
        }
        return attendee;
    }

    @Override
    public Attendee updateAttendeebyId(Integer attendeeId, AttendeeRequest attendeeRequest) {
        if (attendeeId < 1){
            throw new BiggerThanZeroException(Map.of("attendeeId", "AttendeeId must be greater than 0"));
        }
        Attendee attendee = attendeeRepository.modifyAttendeeById(attendeeId, attendeeRequest);
        if (attendee == null) {
            throw new NotFoundException("Update fail! Attendee Id " + attendeeId + " not found");
        }
        return attendee;
    }
}
