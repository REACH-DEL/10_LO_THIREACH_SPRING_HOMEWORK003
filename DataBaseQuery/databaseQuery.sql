create database spring_homework03_event_db;

create table attendees(
    attendee_id serial primary key,
    attendee_name varchar(50) not null,
    email varchar(50) not null
);

create table venues(
    venue_id serial primary key ,
    venue_name varchar(50) not null ,
    location varchar(50) not null
);

create table events(
    event_id serial primary key,
    event_name varchar(50) not null,
    event_date timestamp not null,
    venue_id int not null,
    foreign key (venue_id) references venues(venue_id) on delete cascade
);

create table event_attendee(
    id serial primary key,
    event_id int not null,
    attendee_id int not null,
    foreign key (event_id) references events(event_id) on delete cascade,
    foreign key (attendee_id) references attendees(attendee_id) on delete cascade
);

insert into attendees (attendee_name, email) values
    ('alice johnson', 'alice.johnson@example.com'),
    ('bob smith', 'bob.smith@example.com'),
    ('charlie brown', 'charlie.brown@example.com'),
    ('diana prince', 'diana.prince@example.com'),
    ('edward stark', 'edward.stark@example.com');

insert into venues (venue_name, location) values
    ('grand hall', 'downtown city'),
    ('skyline conference center', 'uptown district'),
    ('oceanview pavilion', 'coastal bay'),
    ('tech hub auditorium', 'silicon valley'),
    ('royal banquet hall', 'central plaza');

insert into events (event_name, event_date, venue_id) values
    ('tech conference 2025', '2025-06-15 10:00:00', 1),
    ('music festival', '2025-07-20 18:00:00', 2),
    ('startup pitch night', '2025-05-10 17:30:00', 3),
    ('ai & robotics summit', '2025-08-25 09:00:00', 4),
    ('charity gala', '2025-09-12 19:00:00', 5);

insert into event_attendee (event_id, attendee_id) values
    (1, 1), (1, 2), (1, 3),
    (2, 2), (2, 4), (2, 5),
    (3, 1), (3, 5),
    (4, 3), (4, 4),
    (5, 1), (5, 2), (5, 3), (5, 4), (5, 5);
