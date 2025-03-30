package org.hrd.homework003.service;

import jakarta.validation.Valid;
import org.hrd.homework003.model.dto.request.VenueRequest;
import org.hrd.homework003.model.entity.Venue;

import java.util.List;

public interface VenueService {
    Venue addVenue(@Valid VenueRequest venueRequest);

    List<Venue> getAllVenues(Integer offset, @Valid Integer limit);

    Venue getVenuebyId(Integer venueId);

    Venue deleteVenuebyId(Integer venueId);

    Venue updateVenuebyId(Integer venueId, @Valid VenueRequest venueRequest);
}
