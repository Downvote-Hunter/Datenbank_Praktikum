CREATE TABLE Person ( 
pid integer PRIMARY KEY,
name varchar(50) NOT NULL,
geb_datum DATE,
geschlecht char(1) NOT NULL);


create table Cast (
  mid integer,
  pid integer,
  role varchar(50),
  primary key (mid, pid, role)
)

INSERT INTO	Person (pid, name, geb_datum, geschlecht) VALUES (1, 'Tom', '11.08.1998', 'M');
INSERT INTO	Person (pid, name, geb_datum, geschlecht) VALUES (2, 'Peter', '05.09.1798', 'F');
INSERT INTO	Person (pid, name, geb_datum, geschlecht) VALUES (3, 'Hans', '21.02.1999', 'M');
INSERT INTO	Person (pid, name, geb_datum, geschlecht) VALUES (4, 'Guenther', '12.08.1998', 'F');
INSERT INTO	Person (pid, name, geb_datum, geschlecht) VALUES (5, 'Paul', '10.08.1998', 'M');

INSERT INTO	Cast (pid, mid, role) VALUES (1, 1, 'Verkaeufer');
INSERT INTO	Cast (pid, mid, role) VALUES (1, 2, 'Einbrecher');
INSERT INTO	Cast (pid, mid, role) VALUES (2, 2, 'Polizei');
INSERT INTO	Cast (pid, mid, role) VALUES (2, 3, 'Entfuehrer');
INSERT INTO	Cast (pid, mid, role) VALUES (3, 3, 'Bank');
INSERT INTO	Cast (pid, mid, role) VALUES (3, 4, 'Sniper');
INSERT INTO	Cast (pid, mid, role) VALUES (4, 4, 'Haendler');
INSERT INTO	Cast (pid, mid, role) VALUES (4, 5, 'Programmierer');
INSERT INTO	Cast (pid, mid, role) VALUES (5, 5, 'Hacker');
INSERT INTO	Cast (pid, mid, role) VALUES (5, 1, 'Kaeufer');