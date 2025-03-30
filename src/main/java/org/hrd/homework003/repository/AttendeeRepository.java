package org.hrd.homework003.repository;

import org.apache.ibatis.annotations.*;
import org.hrd.homework003.model.dto.request.AttendeeRequest;
import org.hrd.homework003.model.entity.Attendee;

import java.util.List;

@Mapper
public interface AttendeeRepository {
    @Results(id = "attendeeMapper", value = {
            @Result(property = "attendeeId", column = "attendee_id"),
            @Result(property = "attendeeName", column = "attendee_name"),
            @Result(property = "email", column = "email")
    })
    @Select("""
        Insert into attendees(attendee_name, email) values (#{attendee.attendeeName}, #{attendee.email}) returning *
    """)
   Attendee insertAttendee(@Param("attendee") AttendeeRequest attendeeRequest);

    @ResultMap("attendeeMapper")
    @Select("""
        Select * from attendees offset (#{offset}-1)* #{limit} limit #{limit}
    """)
    List<Attendee> findAllAttendees(Integer offset, Integer limit);

    @ResultMap("attendeeMapper")
    @Select("""
        select * from attendees where attendee_id = #{attendeeId}
    """)
    Attendee findAttendeeById(Integer attendeeId);

    @ResultMap("attendeeMapper")
    @Select("""
        delete from attendees where attendee_id = #{attendeeId} returning *
    """)
    Attendee removeAttendeeById(Integer attendeeId);

    @ResultMap("attendeeMapper")
    @Select("""
        update attendees set attendee_name = #{attendee.attendeeName}, email = #{attendee.email} where attendee_id = #{attendeeId} returning *
    """)
    Attendee modifyAttendeeById(Integer attendeeId, @Param("attendee") AttendeeRequest attendeeRequest);
}
