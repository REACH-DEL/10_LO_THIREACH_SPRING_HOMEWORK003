package org.hrd.homework003.controller;

import jakarta.validation.Valid;
import org.hrd.homework003.model.dto.request.VenueRequest;
import org.hrd.homework003.model.dto.response.ApiReponse;
import org.hrd.homework003.model.entity.Venue;
import org.hrd.homework003.service.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    public ResponseEntity<ApiReponse<Venue>> addVenue(@RequestBody @Valid VenueRequest venueRequest){
        Venue venue = venueService.addVenue(venueRequest);
        ApiReponse<Venue> response = ApiReponse.<Venue>builder()
                .message("The venue has been successfully added.")
                .status(HttpStatus.CREATED)
                .payload(venue)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiReponse<List<Venue>>> getVenues(@RequestParam(value = "offset", defaultValue = "1") Integer offset, @RequestParam(value = "limit", defaultValue = "10") @Valid Integer limit){
        List<Venue> venues = venueService.getAllVenues(offset, limit);
        ApiReponse<List<Venue>> response = ApiReponse.<List<Venue>>builder()
                .message("The venues have been successfully fetched.")
                .status(HttpStatus.OK)
                .payload(venues)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{venue-id}")
    public ResponseEntity<ApiReponse<Venue>> getVenueById(@PathVariable("venue-id") Integer venueId) {
        Venue venue = venueService.getVenuebyId(venueId);
        ApiReponse<Venue> response = ApiReponse.<Venue>builder()
                .message("The venue has been successfully fetched.")
                .status(HttpStatus.OK)
                .payload(venue)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{venue-id}")
    public ResponseEntity<ApiReponse<Venue>> deleteVenueById(@PathVariable("venue-id") Integer venueId) {
        Venue venue = venueService.deleteVenuebyId(venueId);
        ApiReponse<Venue> response = ApiReponse.<Venue>builder()
                .message("The venue has been successfully deleted.")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{venue-id}")
    public ResponseEntity<ApiReponse<Venue>> updateVenueById(@PathVariable("venue-id") Integer venueId, @RequestBody @Valid VenueRequest venueRequest) {
        Venue venue = venueService.updateVenuebyId(venueId, venueRequest);
        ApiReponse<Venue> response = ApiReponse.<Venue>builder()
                .message("The venue has been successfully updated.")
                .status(HttpStatus.OK)
                .payload(venue)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
