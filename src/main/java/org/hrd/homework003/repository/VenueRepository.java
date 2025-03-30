package org.hrd.homework003.repository;

import org.apache.ibatis.annotations.*;
import org.hrd.homework003.model.dto.request.VenueRequest;
import org.hrd.homework003.model.entity.Venue;

import java.util.List;

@Mapper
public interface VenueRepository {
    @Results(id = "venueMapper", value = {
            @Result(property = "venueId", column = "venue_id"),
            @Result(property = "venueName", column = "venue_name"),
            @Result(property = "location", column = "location")
    })
    @Select("""
        insert into venues(venue_name, location) values (#{venue.venueName}, #{venue.location}) returning *
    """)
    Venue insertVenue(@Param("venue") VenueRequest venueRequest);

    @ResultMap("venueMapper")
    @Select("""
        select * from venues offset (#{offset}-1)* #{limit} limit #{limit}
    """)
    List<Venue> findAllVenues(Integer offset, Integer limit);

    @ResultMap("venueMapper")
    @Select("""
        select * from venues where venue_id = #{venueId}
    """)
    Venue findVenueById(Integer venueId);

    @ResultMap("venueMapper")
    @Select("""
        delete from venues where venue_id = #{venueId} returning *
    """)
    Venue removeVenueById(Integer venueId);

    @ResultMap("venueMapper")
    @Select("""
        update venues set venue_name = #{venue.venueName}, location = #{venue.location} where venue_id = #{venueId} returning *
    """)
    Venue updateVenueById(Integer venueId, @Param("venue") VenueRequest venueRequest);
}
