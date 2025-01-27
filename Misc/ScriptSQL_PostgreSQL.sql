-- public.tenpistas definition

-- Drop table

-- DROP TABLE public.tenpistas;

CREATE TABLE public.tenpistas (
	id int4 GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	apellido varchar(100) NOT NULL,
	nombre varchar(100) NOT NULL,
	nro_cuenta int4 NOT NULL
);


-- public.transaction_edits definition

-- Drop table

-- DROP TABLE public.transaction_edits;

CREATE TABLE public.transaction_edits (
	id int4 GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	tenpista varchar(100) NOT NULL,
	trx_fecha timestamp(6) NOT NULL,
	trx_giro_comercio varchar(100) NOT NULL,
	trx_monto int4 NOT NULL,
	trx_original_id int4 NOT NULL,
	trx_tipo varchar(50) NOT NULL,
	CONSTRAINT transaction_edits_pkey null
);


-- public.transaction_refunds definition

-- Drop table

-- DROP TABLE public.transaction_refunds;

CREATE TABLE public.transaction_refunds (
	id int4 GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	tenpista varchar(100) NOT NULL,
	trx_fecha timestamp(6) NOT NULL,
	trx_giro_comercio varchar(100) NOT NULL,
	trx_monto int4 NOT NULL,
	trx_original_id int4 NOT NULL,
	trx_tipo varchar(50) NOT NULL
);


-- public.transactions definition

-- Drop table

-- DROP TABLE public.transactions;

CREATE TABLE public.transactions (
	id serial4 NOT NULL,
	trx_monto int4 NOT NULL,
	trx_giro_comercio varchar(100) NOT NULL,
	tenpista varchar(100) NOT NULL,
	trx_fecha timestamp NOT NULL,
	trx_tipo varchar(50) DEFAULT '""'::character varying NOT NULL
);