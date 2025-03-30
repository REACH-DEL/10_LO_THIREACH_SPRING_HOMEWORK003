package org.hrd.homework003.service.implement;

import org.hrd.homework003.exception.BiggerThanZeroException;
import org.hrd.homework003.exception.NotFoundException;
import org.hrd.homework003.model.dto.request.VenueRequest;
import org.hrd.homework003.model.entity.Venue;
import org.hrd.homework003.repository.VenueRepository;
import org.hrd.homework003.service.VenueService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VenueServiceImp implements VenueService {

    private final VenueRepository venueRepository;

    public VenueServiceImp(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Venue addVenue(VenueRequest venueRequest) {
        return venueRepository.insertVenue(venueRequest);
    }

    @Override
    public List<Venue> getAllVenues(Integer offset, Integer limit) {
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
        return venueRepository.findAllVenues(offset, limit);
    }

    @Override
    public Venue getVenuebyId(Integer venueId) {
        if (venueId < 1){
            throw new BiggerThanZeroException(Map.of("venueId", "VenueId must be greater than 0"));
        }
        Venue venue = venueRepository.findVenueById(venueId);
        if (venue == null) {
            throw new NotFoundException("Venue Id " + venueId + " not found");
        }
        return venue;
    }

    @Override
    public Venue deleteVenuebyId(Integer venueId) {
        if (venueId < 1){
            throw new BiggerThanZeroException(Map.of("venueId", "VenueId must be greater than 0"));
        }
        Venue venue = venueRepository.removeVenueById(venueId);
        if (venue == null) {
            throw new NotFoundException("Delete fail! Venue Id " + venueId + " not found");
        }
        return venue;
    }

    @Override
    public Venue updateVenuebyId(Integer venueId, VenueRequest venueRequest) {
        if (venueId < 1){
            throw new BiggerThanZeroException(Map.of("venueId", "VenueId must be greater than 0"));
        }
        Venue venue = venueRepository.updateVenueById(venueId, venueRequest);
        if (venue == null) {
            throw new NotFoundException("Update fail! Venue Id " + venueId + " not found");
        }
        return venue;
    }
}
