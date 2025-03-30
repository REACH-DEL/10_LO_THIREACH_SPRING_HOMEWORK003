package org.hrd.homework003.repository;

import org.apache.ibatis.annotations.*;
import org.hrd.homework003.model.dto.request.EventRequest;
import org.hrd.homework003.model.entity.Attendee;
import org.hrd.homework003.model.entity.Event;
import org.hrd.homework003.model.entity.EventAttendee;

import java.util.List;

@Mapper
public interface EventRepository {
    @Result(property = "attendee", column = "attendee_id",
    one = @One(select = "org.hrd.homework003.repository.AttendeeRepository.findAttendeeById"))
    @Select("""
        select * from event_attendee where event_id = #{eventId}
    """)
    List<EventAttendee> findAttendeesByEventId(Integer eventId);

    @Results(id = "eventMapper", value = {
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "eventName", column = "event_name"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "venue", column = "venue_id",
            one = @One(select = "org.hrd.homework003.repository.VenueRepository.findVenueById")),
            @Result(property = "attendees", column = "event_id",
            many = @Many(select = "findAttendeesByEventId"))
    })
    @Select("""
        select * from events offset (#{offset}-1)* #{limit} limit #{limit}
    """)
    List<Event> findAllEvents(Integer offset, Integer limit);


    @Select("""
        insert into events (event_name, event_date, venue_id) values (#{event.eventName}, #{event.eventDate}, #{event.venueId}) returning event_id
    """)
    Integer insertEvent(@Param("event") EventRequest eventRequest);

    @Insert("""
        insert into event_attendee (event_id, attendee_id) values (#{eventId}, #{attendeeId})
    """)
    void insertEventAttendee(Integer eventId, Integer attendeeId);

    @ResultMap("eventMapper")
    @Select("""
        select * from events where event_id = #{eventId}
    """)
    Event findEventById(Integer eventId);

    @Select("""
        delete from events where event_id = #{eventId} returning event_id
    """)
    Integer removeEventById(Integer eventId);

    @Delete("""
        delete from event_attendee where event_id = #{eventId}
    """)
    void removeEventAttendee(Integer deleteEventId);

    @Select("""
        update events set event_name = #{event.eventName}, event_date = #{event.eventDate}, venue_id = #{event.venueId} where event_id = #{eventId} returning *
    """)
    Event modifyEventById(Integer eventId, @Param("event") EventRequest eventRequest);

    @Update("""
        update event_attendee set attendee_id = #{attendeeId} where event_id = #{updateEventId}
    """)
    void modifyEventAttendeeById(Integer updateEventId, Integer attendeeId);
}
