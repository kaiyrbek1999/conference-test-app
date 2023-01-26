
insert into ROLES (ID, NAME)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_CONFERENCE_OWNER');
insert into USERS (ID, EMAIL, FIRST_NAME, LAST_NAME, SECOND_NAME,PASSWORD, ROLE_ID)
values (1,'admin@gmail.com','admin','admin','admin','$2a$10$iUMLF7DziET1U7ifmtHDduOJbFQcFdjn.fq56YW5FFrXxP1vt86E.',1),
       (2,'user@gmail.com','user','user','user','$2a$10$rJu86pKloTYQ./n32LJz5egFWuPVBR4Dwgq1PSEXXSGl0Tg6ZEpx6',2),
       (3,'conference_owner@gmail.com','conference_owne','conference_owne','conference_owne','$2a$10$/Bb.4sEV6n5fwyB4fKGHJewuxh5MJOBmkwSdrUC2uN7F5mhK/ysx.',3);

insert into CONFERENCE (ID, BEGIN_TIME, END_TIME, IS_CANCELLED, MAX_SEATS, NAME, OWNER_ID)
values (1,'2023-01-27 12:57','2023-01-27 14:57',FALSE,3,'conf',1),
      (2,'2023-01-28 12:57','2023-01-28 14:57',FALSE,2,'conf',1);

insert into CONFERENCE_PARTICIPANTS (CONFERENCE_ID, PARTICIPANT_ID)
values
    (1,2),
    (1,3);