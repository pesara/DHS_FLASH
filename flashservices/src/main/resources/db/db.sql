CREATE TABLE public.user (
   id SERIAL PRIMARY KEY,
   username   character varying(25),
   password   character varying(100),
   createdate   timestamp without time zone,
   updatedate   timestamp without time zone,
   etldate   timestamp without time zone
)
/
CREATE TABLE public.kudos (
   id   SERIAL PRIMARY KEY,
   from_eid   numeric,
   to_eid   numeric,
   count numeric,
   createdate   timestamp without time zone,
   updatedate   timestamp without time zone,
   etldate   timestamp without time zone
)
/
CREATE TABLE public.employee (
   id SERIAL PRIMARY KEY,
   userid   numeric,
   firstname   character varying(70),
   lastname   character varying(30),
   departmentid   numeric,
   email   character varying(100),
   createdate   timestamp without time zone,
   updatedate   timestamp without time zone,
   etldate   timestamp without time zone,
   activeind char
)
/
CREATE TABLE public.department (
   id   SERIAL PRIMARY KEY,
   userid   numeric,
   departmentname   character varying(70),
   createdate   timestamp without time zone,
   updatedate   timestamp without time zone,
   etldate   timestamp without time zone
)
