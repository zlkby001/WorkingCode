

CREATE SEQUENCE usbmeasureid_sequence INCREMENT BY 1  START WITH 1 NOMAXVALUE NOCYCLE NOCACHE;

create table usbmeasure(
id int not null with default next value for usbmeasureid_sequence, 
manufacture varchar(255) not null,
sn varchar(255) not null , 
version varchar(255),
producer varchar(255),
terminalid int,
date varchar(255),
constraint usbmeasure_primary primary key (manufacture,sn));



CREATE SEQUENCE usblocalid_sequence INCREMENT BY 1  START WITH 1 NOMAXVALUE NOCYCLE NOCACHE;

create table usblocal(
id int not null with default next value for usblocalid_sequence, 
terminalid int not null,
manufacture varchar(255) not null , 
sn varchar(255) not null,
constraint usbmeasure_primary primary key (terminalid,manufacture,sn))