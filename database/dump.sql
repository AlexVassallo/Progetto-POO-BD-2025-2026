--
-- PostgreSQL database dump
--

\restrict IglYfeB2avgW5DPfjJ7d5VQDqMz7T4VFU6x5sbQvqJGiYjqxF6AgFuYQWAQact5

-- Dumped from database version 18.6
-- Dumped by pg_dump version 18.6

-- Started on 2026-11-21 13:31:55

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 247 (class 1255 OID 17006)
-- Name: fn_logga_spostamento_medico(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.fn_logga_spostamento_medico() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
IF TG_TABLE_NAME = 'medico' THEN
	IF OLD.codice_sala_ricovero IS DISTINCT FROM NEW.codice_sala_ricovero THEN
	
		IF NEW.codice_sala_ricovero IS NULL THEN
			INSERT INTO evn_spostamenti_medici (id_medico, id_sala_attuale, tipo_spostamento, id_sala_operatoria_arrivo,
			id_sala_ricovero_arrivo, timestamp_spostamento, motivo_spostamento)
			VALUES(NEW.identificativo_medico, OLD.codice_sala_ricovero, 'USCITA SALA RICOVERO', NULL,
			NEW.codice_sala_ricovero, CURRENT_TIMESTAMP, 	NULL);
		ELSE
			INSERT INTO evn_spostamenti_medici (id_medico, id_sala_attuale, tipo_spostamento, id_sala_operatoria_arrivo,
			id_sala_ricovero_arrivo, timestamp_spostamento, motivo_spostamento)
			VALUES(NEW.identificativo_medico, OLD.codice_sala_ricovero, 'ENTRATA SALA RICOVERO', NULL,
			NEW.codice_sala_ricovero, CURRENT_TIMESTAMP, NULL);
		END IF;
	END IF;
	
	RETURN NEW;
	
ELSIF TG_TABLE_NAME = 'sala_operatoria_medico' THEN

	IF TG_OP = 'INSERT' THEN
		INSERT INTO evn_spostamenti_medici  (id_medico, id_sala_attuale, tipo_spostamento, id_sala_operatoria_arrivo,
		id_sala_ricovero_arrivo, timestamp_spostamento, motivo_spostamento)
		VALUES(NEW.identificativo_medico, NULL, 'ENTRATA SALA OPERATORIA', NEW.codice_sala,
		NULL, CURRENT_TIMESTAMP, 'probabilmente dovra effettuare un operazione');
			
		RETURN NEW;
	
	ELSE
		IF TG_OP = 'DELETE' THEN
			INSERT INTO evn_spostamenti_medici  (id_medico, id_sala_attuale, tipo_spostamento, id_sala_operatoria_arrivo,
			id_sala_ricovero_arrivo, timestamp_spostamento, motivo_spostamento)
			VALUES(OLD.identificativo_medico, OLD.codice_sala, 'USCITA SALA OPERATORIA', NULL,
			NULL, CURRENT_TIMESTAMP, NULL);
		
			RETURN OLD;
		END IF;
		
	END IF;

END IF;
RETURN NULL;

END;
$$;


--
-- TOC entry 235 (class 1255 OID 17009)
-- Name: fn_logga_spostamento_paziente(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.fn_logga_spostamento_paziente() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
IF TG_TABLE_NAME = 'paziente' THEN
	IF OLD.codice_sala_ricovero IS DISTINCT FROM NEW.codice_sala_ricovero THEN
	
		IF NEW.codice_sala_ricovero IS NULL THEN
			INSERT INTO evn_spostamenti_pazienti (id_paziente, id_sala_attuale, tipo_spostamento, id_sala_operatoria_arrivo,
			id_sala_ricovero_arrivo, timestamp_spostamento, motivo_spostamento)
			VALUES(NEW.identificativo_paziente, OLD.codice_sala_ricovero, 'USCITA SALA RICOVERO', NULL,
			NEW.codice_sala_ricovero, CURRENT_TIMESTAMP, 	NULL);
			
		ELSE
			INSERT INTO evn_spostamenti_pazienti (id_paziente, id_sala_attuale, tipo_spostamento, id_sala_operatoria_arrivo,
			id_sala_ricovero_arrivo, timestamp_spostamento, motivo_spostamento)
			VALUES(NEW.identificativo_paziente, OLD.codice_sala_ricovero, 'ENTRATA SALA RICOVERO', NULL,
			NEW.codice_sala_ricovero, CURRENT_TIMESTAMP, NULL);
		END IF;
	END IF;
	
	RETURN NEW;
	
ELSIF TG_TABLE_NAME = 'sala_operatoria' THEN

		IF OLD.identificativo_paziente IS DISTINCT FROM NEW.identificativo_paziente THEN
		
			IF NEW.identificativo_paziente IS NULL THEN
				INSERT INTO evn_spostamenti_pazienti(id_paziente, id_sala_attuale, tipo_spostamento, id_sala_operatoria_arrivo,
				id_sala_ricovero_arrivo, timestamp_spostamento, motivo_spostamento)
				VALUES(OLD.identificativo_paziente, OLD.codice_sala, 'USCITA SALA OPERATORIA', NULL,
				NULL, CURRENT_TIMESTAMP, NULL);

			ELSE
				INSERT INTO evn_spostamenti_pazienti(id_paziente, id_sala_attuale, tipo_spostamento, id_sala_operatoria_arrivo,
				id_sala_ricovero_arrivo, timestamp_spostamento, motivo_spostamento)
				VALUES(NEW.identificativo_paziente, NULL, 'ENTRATA SALA OPERATORIA', NEW.codice_sala,
				NULL, CURRENT_TIMESTAMP, 'probabilmente dovra ricevere un operazione');

			END IF;
			
		END IF;
		
		RETURN NEW;

	END IF;	
	
	RETURN NULL;

END;
$$;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 234 (class 1259 OID 16904)
-- Name: evn_spostamenti_medici; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.evn_spostamenti_medici (
    id_evn integer NOT NULL,
    id_medico character varying(50) NOT NULL,
    id_sala_attuale character varying(100),
    tipo_spostamento character varying(100),
    id_sala_operatoria_arrivo character varying(50),
    id_sala_ricovero_arrivo character varying(50),
    timestamp_spostamento timestamp without time zone,
    motivo_spostamento character varying(100)
);


--
-- TOC entry 233 (class 1259 OID 16903)
-- Name: evn_spostamenti_medici_id_evn_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.evn_spostamenti_medici ALTER COLUMN id_evn ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.evn_spostamenti_medici_id_evn_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 232 (class 1259 OID 16855)
-- Name: evn_spostamenti_pazienti; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.evn_spostamenti_pazienti (
    id_evn integer NOT NULL,
    id_paziente character varying(50) NOT NULL,
    id_sala_attuale character varying(100),
    tipo_spostamento character varying(100),
    id_sala_operatoria_arrivo character varying(50),
    id_sala_ricovero_arrivo character varying(50),
    timestamp_spostamento timestamp without time zone,
    motivo_spostamento character varying(100)
);


--
-- TOC entry 231 (class 1259 OID 16854)
-- Name: evn_spostamenti_pazienti_id_evn_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.evn_spostamenti_pazienti ALTER COLUMN id_evn ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.evn_spostamenti_pazienti_id_evn_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 230 (class 1259 OID 16818)
-- Name: log_user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.log_user (
    id_log integer NOT NULL,
    identificativo_medico character varying(50) NOT NULL,
    esito_log character varying(50),
    remark character varying(200),
    date_log timestamp without time zone
);


--
-- TOC entry 229 (class 1259 OID 16817)
-- Name: log_user_id_log_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.log_user ALTER COLUMN id_log ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.log_user_id_log_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 223 (class 1259 OID 16586)
-- Name: medico; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.medico (
    identificativo_medico character varying(50) NOT NULL,
    codice_fiscale character varying(16) NOT NULL,
    tipo_medico character varying(50),
    rango character varying(50),
    data_anno_assunzione timestamp without time zone,
    is_amministratore boolean DEFAULT false NOT NULL,
    password character varying(255) NOT NULL,
    codice_sala_ricovero character varying(50)
);


--
-- TOC entry 226 (class 1259 OID 16675)
-- Name: operazione; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.operazione (
    id_operazione character varying(20) NOT NULL,
    id_paziente character varying(20) NOT NULL,
    codice_sala_operatoria character varying(20) NOT NULL,
    tipo_operazione character varying(100) NOT NULL,
    data_ora_inizio timestamp without time zone NOT NULL,
    data_ora_fine timestamp without time zone,
    esito character varying(100)
);


--
-- TOC entry 227 (class 1259 OID 16695)
-- Name: operazione_medico; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.operazione_medico (
    id_operazione character varying(20) NOT NULL,
    id_medico character varying(20) NOT NULL
);


--
-- TOC entry 219 (class 1259 OID 16537)
-- Name: ospedale; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ospedale (
    identificativo_ospedale character varying(50) NOT NULL,
    nome_ospedale character varying(100) NOT NULL
);


--
-- TOC entry 222 (class 1259 OID 16567)
-- Name: paziente; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.paziente (
    identificativo_paziente character varying(50) NOT NULL,
    codice_fiscale character varying(16) NOT NULL,
    triage_paziente character varying(20),
    codice_sala_ricovero character varying(50)
);


--
-- TOC entry 220 (class 1259 OID 16544)
-- Name: persona; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.persona (
    codice_fiscale character varying(16) NOT NULL,
    nome_persona character varying(50) NOT NULL,
    cognome_persona character varying(50) NOT NULL,
    data_di_nascita date NOT NULL,
    luogo_di_nascita character varying(50),
    indirizzo character varying(100)
);


--
-- TOC entry 228 (class 1259 OID 16712)
-- Name: referto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.referto (
    id_referto character varying(20) NOT NULL,
    id_operazione character varying(20) NOT NULL,
    diagnosi text,
    data_emissione timestamp without time zone NOT NULL,
    trattamento_effettuato text,
    note text,
    prescrizioni_terapeutiche text,
    esito_finale character varying(100)
);


--
-- TOC entry 224 (class 1259 OID 16608)
-- Name: sala_operatoria; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sala_operatoria (
    codice_sala character varying(50) CONSTRAINT salaoperatoria_codice_sala_not_null NOT NULL,
    identificativo_ospedale character varying(50) CONSTRAINT salaoperatoria_identificativo_ospedale_not_null NOT NULL,
    is_disponibile boolean DEFAULT true CONSTRAINT salaoperatoria_is_disponibile_not_null NOT NULL,
    identificativo_paziente character varying(50)
);


--
-- TOC entry 225 (class 1259 OID 16629)
-- Name: sala_operatoria_medico; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sala_operatoria_medico (
    codice_sala character varying(50) CONSTRAINT salaoperatoria_medico_codice_sala_not_null NOT NULL,
    identificativo_medico character varying(50) CONSTRAINT salaoperatoria_medico_identificativo_medico_not_null NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 16553)
-- Name: sala_ricovero; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.sala_ricovero (
    codice_sala character varying(50) CONSTRAINT salaricovero_codice_sala_not_null NOT NULL,
    identificativo_ospedale character varying(50) CONSTRAINT salaricovero_identificativo_ospedale_not_null NOT NULL,
    tipo_sala character varying(50),
    numero_letti integer DEFAULT 0,
    letti_liberi integer DEFAULT 0
);


--
-- TOC entry 5037 (class 0 OID 16904)
-- Dependencies: 234
-- Data for Name: evn_spostamenti_medici; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.evn_spostamenti_medici (id_evn, id_medico, id_sala_attuale, tipo_spostamento, id_sala_operatoria_arrivo, id_sala_ricovero_arrivo, timestamp_spostamento, motivo_spostamento) FROM stdin;
1	m006	\N	ENTRATA SALA OPERATORIA	so001	\N	2026-11-14 14:41:37.818223	probabilmente dovra effettuare un operazione
3	m006	so001	USCITA SALA OPERATORIA	\N	\N	2026-11-14 14:55:12.866378	\N
4	m006	\N	ENTRATA SALA OPERATORIA	so001	\N	2026-11-14 14:55:31.017337	probabilmente dovra effettuare un operazione
5	m006	so001	USCITA SALA OPERATORIA	\N	\N	2026-11-14 14:55:36.382891	\N
6	m006	\N	ENTRATA SALA RICOVERO	\N	sr001	2026-11-14 14:56:01.430861	\N
7	m006	sr001	USCITA SALA RICOVERO	\N	\N	2026-11-14 14:56:16.192505	\N
\.


--
-- TOC entry 5035 (class 0 OID 16855)
-- Dependencies: 232
-- Data for Name: evn_spostamenti_pazienti; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.evn_spostamenti_pazienti (id_evn, id_paziente, id_sala_attuale, tipo_spostamento, id_sala_operatoria_arrivo, id_sala_ricovero_arrivo, timestamp_spostamento, motivo_spostamento) FROM stdin;
1	p006	\N	ENTRATA SALA OPERATORIA	so002	\N	2026-11-14 15:34:16.34675	probabilmente dovra ricevere un operazione
2	p006	so002	USCITA SALA OPERATORIA	\N	\N	2026-11-14 15:34:25.966055	\N
3	p006	\N	ENTRATA SALA RICOVERO	\N	sr001	2026-11-14 15:35:12.391616	\N
4	p006	sr001	USCITA SALA RICOVERO	\N	\N	2026-11-14 15:35:18.100177	\N
5	p006	\N	ENTRATA SALA OPERATORIA	so002	\N	2026-11-14 15:40:29.536679	probabilmente dovra ricevere un operazione
\.


--
-- TOC entry 5033 (class 0 OID 16818)
-- Dependencies: 230
-- Data for Name: log_user; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.log_user (id_log, identificativo_medico, esito_log, remark, date_log) FROM stdin;
\.


--
-- TOC entry 5026 (class 0 OID 16586)
-- Dependencies: 223
-- Data for Name: medico; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.medico (identificativo_medico, codice_fiscale, tipo_medico, rango, data_anno_assunzione, is_amministratore, password, codice_sala_ricovero) FROM stdin;
m002	avvifg89n78f321n	Chirurgo	Capo Reparto	2026-07-12 00:00:00	f	aaaaaaaaa	\N
m001	rcclss05t27n980f	chirurgo	direttore	2026-07-12 00:00:00	t	aaaaaaaaa	\N
m003	adfgth78j89n890n	anestetista	medico di base	2026-06-02 00:00:00	t	aaaaaaaaa	\N
m005	dsannh78j89k098n	cardiologo	capo sala	2026-08-30 00:00:00	t	aaaaaaaaa	\N
m004	evfthg78j90n234f	chirurgo	medico specialista	2025-07-12 00:00:00	f	aaaaaaaaa	\N
m006	huygfd32h45h789n	infermiere	base	2026-09-12 00:00:00	f	aaaaaaaaa	\N
\.


--
-- TOC entry 5029 (class 0 OID 16675)
-- Dependencies: 226
-- Data for Name: operazione; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.operazione (id_operazione, id_paziente, codice_sala_operatoria, tipo_operazione, data_ora_inizio, data_ora_fine, esito) FROM stdin;
op001	p001	so001	neurochirurgia	2026-08-29 18:35:21.587019	2026-08-30 18:19:11.124833	nessun problema durante l'operazione
op002	p001	so001	chirurgia	2026-08-31 16:30:11.100357	2026-08-31 16:34:07.548478	positivo
o003	p004	so004	chirurgia	2026-09-15 11:59:53.712419	2026-09-15 12:00:52.82578	positivo
op004	p009	so003	chirurgia	2026-09-16 12:19:54.393635	\N	\N
\.


--
-- TOC entry 5030 (class 0 OID 16695)
-- Dependencies: 227
-- Data for Name: operazione_medico; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.operazione_medico (id_operazione, id_medico) FROM stdin;
op001	m002
op001	m001
op001	m003
op002	m002
op002	m003
o003	m005
o003	m004
op004	m001
op004	m004
\.


--
-- TOC entry 5022 (class 0 OID 16537)
-- Dependencies: 219
-- Data for Name: ospedale; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.ospedale (identificativo_ospedale, nome_ospedale) FROM stdin;
i001	schiana
o001	artiago
o002	ospedale riccio
o003	prova
o006	schiana
\.


--
-- TOC entry 5025 (class 0 OID 16567)
-- Dependencies: 222
-- Data for Name: paziente; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.paziente (identificativo_paziente, codice_fiscale, triage_paziente, codice_sala_ricovero) FROM stdin;
p002	rvvdss09t78n0e2f	rosso	sr001
p003	emetds00n98h789k	Verde	sr001
p005	rccwft67n78h890n	rosso	sr003
p001	rcclss05t27n823f	giallo	\N
p007	rcclss05t27n093t	rosso	sr004
p004	crrdfg54j89n908l	Verde	\N
p008	rcclst60j98k098m	verde	sr005
p009	avvgtt78f46n678j	rosso	\N
p006	rvvgtt78n78j890n	rosso	\N
\.


--
-- TOC entry 5023 (class 0 OID 16544)
-- Dependencies: 220
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.persona (codice_fiscale, nome_persona, cognome_persona, data_di_nascita, luogo_di_nascita, indirizzo) FROM stdin;
rcclss05t27n980f	Alessio	Riccio	2005-12-27	napoli	via alfonso gatto 35
rcclss05t27n823f	Alessio	Ronaldi	2004-01-27	Napoli	Via umberto saba 3
rvvdss09t78n0e2f	Roberto	Rienzo	2001-12-27	Roma	via Claudio 790
avvifg89n78f321n	Alessandro	Vassallo	2004-01-11	Pozzuoli	via Bernando Breve 45
emvttf45n54h879j	Emanuele	Todisco	2006-11-11	Napoli	Via alberto 67
rcclss05t67n678n	salvatore	riccio	2003-12-27	napoli	via canarino 70
emetds00n98h789k	Emanuele	Todisco	2004-11-11	Napoli	via Alberto 54
crrdfg54j89n908l	Cristian	Della monica	2007-03-23	Pozzuoli	via alfonso gatto 1
rccwft67n78h890n	Salvatore	Testa	2003-12-12	Licola mare	Via sasso 32
avvfgg67n78n890k	Alessia	Ferricchio	2003-03-11	Varcaturo	 via Ripuaria 45
rvvftg89m09m987h	Roberta	Cerlino	2003-07-11	Napoli	via claudio
rvvgtt78n78j890n	Davide	Lucignano	2003-01-12	Napoli	Via alberto 32
adfgth78j89n890n	cristian	monte	2001-06-12	pozzuoli	via anfiteatro flavio 23
evfthg78j90n234f	anna	verdi	2004-02-12	napoli	via roma 21
dsannh78j89k098n	cristian	della monica	2009-12-27	napoli	via alfonso gatto 21
rcclss05t27n093t	alessio	Lucignano	2003-07-12	roma	via claudio 45
huygfd32h45h789n	alessio	fasano	2001-09-12	roma	via claudio 12
rcclst60j98k098m	roberto	covone	2000-12-11	napoli	via salvatore 03
avvgtt78f46n678j	alessia	nasti	2006-12-27	cagliari	via rosso 45
\.


--
-- TOC entry 5031 (class 0 OID 16712)
-- Dependencies: 228
-- Data for Name: referto; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.referto (id_referto, id_operazione, diagnosi, data_emissione, trattamento_effettuato, note, prescrizioni_terapeutiche, esito_finale) FROM stdin;
r001	op001	lesione traumatica	2026-08-30 18:19:56.401735	craniotomia	\tCraniotomia fronto-parietale destra con asportazione di massa intracerebrale	Levetiracetam 500 mg cpr\t1 cpr × 2/die (mattina e sera)\t14 giorni, poi rivalutazione\tProfilassi antiepilettica. Sospensione se assenza di crisi al controllo. 	esito positivo
r002	op002	pallottola trovata all'interno del bicibite	2026-08-31 16:37:18.904391	rimozione pallottola	rimozione pallottola dentro l'avambraccio con una piccola trasfusione	Penicillina G 5 MU IV (o ceftriaxone 2 g EV ogni 12 h) per 7–10 giorni, per prevenire sepsi e osteomielite	l'operazione è andata con successo senza complicazioni
r003	o003	rottura femore	2026-09-15 12:02:05.932109	chirurgia al femore	operazione andata con successo nonostante qualche piccola difficolta	3 pillole al giorno mattina pomeriggio e sera	positivo
\.


--
-- TOC entry 5027 (class 0 OID 16608)
-- Dependencies: 224
-- Data for Name: sala_operatoria; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.sala_operatoria (codice_sala, identificativo_ospedale, is_disponibile, identificativo_paziente) FROM stdin;
so001	i001	t	p001
so004	o003	t	\N
so003	o003	f	p009
so007	o003	t	\N
so002	o001	t	p006
\.


--
-- TOC entry 5028 (class 0 OID 16629)
-- Dependencies: 225
-- Data for Name: sala_operatoria_medico; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.sala_operatoria_medico (codice_sala, identificativo_medico) FROM stdin;
so001	m002
so001	m003
so003	m001
so003	m004
\.


--
-- TOC entry 5024 (class 0 OID 16553)
-- Dependencies: 221
-- Data for Name: sala_ricovero; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.sala_ricovero (codice_sala, identificativo_ospedale, tipo_sala, numero_letti, letti_liberi) FROM stdin;
sr003	i001	day hospital	4	3
sr004	o001	day hospital	3	2
sr002	i001	day hospital	3	3
sr006	o003	prova	3	3
sr005	o002	day hospital	1	1
sr001	i001	ricovero ordinario	3	1
\.


--
-- TOC entry 5043 (class 0 OID 0)
-- Dependencies: 233
-- Name: evn_spostamenti_medici_id_evn_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.evn_spostamenti_medici_id_evn_seq', 7, true);


--
-- TOC entry 5044 (class 0 OID 0)
-- Dependencies: 231
-- Name: evn_spostamenti_pazienti_id_evn_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.evn_spostamenti_pazienti_id_evn_seq', 5, true);


--
-- TOC entry 5045 (class 0 OID 0)
-- Dependencies: 229
-- Name: log_user_id_log_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.log_user_id_log_seq', 1, false);


--
-- TOC entry 4849 (class 2606 OID 16910)
-- Name: evn_spostamenti_medici evn_spostamenti_medici_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.evn_spostamenti_medici
    ADD CONSTRAINT evn_spostamenti_medici_pkey PRIMARY KEY (id_evn);


--
-- TOC entry 4847 (class 2606 OID 16861)
-- Name: evn_spostamenti_pazienti evn_spostamenti_pazienti_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.evn_spostamenti_pazienti
    ADD CONSTRAINT evn_spostamenti_pazienti_pkey PRIMARY KEY (id_evn);


--
-- TOC entry 4845 (class 2606 OID 16824)
-- Name: log_user log_user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.log_user
    ADD CONSTRAINT log_user_pkey PRIMARY KEY (id_log);


--
-- TOC entry 4826 (class 2606 OID 16597)
-- Name: medico medico_codice_fiscale_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.medico
    ADD CONSTRAINT medico_codice_fiscale_key UNIQUE (codice_fiscale);


--
-- TOC entry 4828 (class 2606 OID 16595)
-- Name: medico medico_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.medico
    ADD CONSTRAINT medico_pkey PRIMARY KEY (identificativo_medico);


--
-- TOC entry 4839 (class 2606 OID 16701)
-- Name: operazione_medico operazione_medico_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operazione_medico
    ADD CONSTRAINT operazione_medico_pkey PRIMARY KEY (id_operazione, id_medico);


--
-- TOC entry 4837 (class 2606 OID 16684)
-- Name: operazione operazione_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operazione
    ADD CONSTRAINT operazione_pkey PRIMARY KEY (id_operazione);


--
-- TOC entry 4812 (class 2606 OID 16543)
-- Name: ospedale ospedale_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ospedale
    ADD CONSTRAINT ospedale_pkey PRIMARY KEY (identificativo_ospedale);


--
-- TOC entry 4820 (class 2606 OID 16575)
-- Name: paziente paziente_codice_fiscale_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.paziente
    ADD CONSTRAINT paziente_codice_fiscale_key UNIQUE (codice_fiscale);


--
-- TOC entry 4822 (class 2606 OID 16573)
-- Name: paziente paziente_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.paziente
    ADD CONSTRAINT paziente_pkey PRIMARY KEY (identificativo_paziente);


--
-- TOC entry 4814 (class 2606 OID 16552)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (codice_fiscale);


--
-- TOC entry 4841 (class 2606 OID 16723)
-- Name: referto referto_id_operazione_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.referto
    ADD CONSTRAINT referto_id_operazione_key UNIQUE (id_operazione);


--
-- TOC entry 4843 (class 2606 OID 16721)
-- Name: referto referto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.referto
    ADD CONSTRAINT referto_pkey PRIMARY KEY (id_referto);


--
-- TOC entry 4831 (class 2606 OID 16618)
-- Name: sala_operatoria salaoperatoria_identificativo_paziente_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sala_operatoria
    ADD CONSTRAINT salaoperatoria_identificativo_paziente_key UNIQUE (identificativo_paziente);


--
-- TOC entry 4835 (class 2606 OID 16635)
-- Name: sala_operatoria_medico salaoperatoria_medico_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sala_operatoria_medico
    ADD CONSTRAINT salaoperatoria_medico_pkey PRIMARY KEY (codice_sala, identificativo_medico);


--
-- TOC entry 4833 (class 2606 OID 16616)
-- Name: sala_operatoria salaoperatoria_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sala_operatoria
    ADD CONSTRAINT salaoperatoria_pkey PRIMARY KEY (codice_sala);


--
-- TOC entry 4816 (class 2606 OID 16561)
-- Name: sala_ricovero salaricovero_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sala_ricovero
    ADD CONSTRAINT salaricovero_pkey PRIMARY KEY (codice_sala);


--
-- TOC entry 4823 (class 1259 OID 16669)
-- Name: idx_medico_cf; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_medico_cf ON public.medico USING btree (codice_fiscale);


--
-- TOC entry 4824 (class 1259 OID 16671)
-- Name: idx_medico_sala; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_medico_sala ON public.medico USING btree (codice_sala_ricovero);


--
-- TOC entry 4817 (class 1259 OID 16668)
-- Name: idx_paziente_cf; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_paziente_cf ON public.paziente USING btree (codice_fiscale);


--
-- TOC entry 4818 (class 1259 OID 16670)
-- Name: idx_paziente_sala; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_paziente_sala ON public.paziente USING btree (codice_sala_ricovero);


--
-- TOC entry 4829 (class 1259 OID 16672)
-- Name: idx_so_paziente; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_so_paziente ON public.sala_operatoria USING btree (identificativo_paziente);


--
-- TOC entry 4872 (class 2620 OID 17008)
-- Name: medico trg_spostamento_medico_sala_op; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER trg_spostamento_medico_sala_op AFTER UPDATE ON public.medico FOR EACH ROW EXECUTE FUNCTION public.fn_logga_spostamento_medico();


--
-- TOC entry 4874 (class 2620 OID 17007)
-- Name: sala_operatoria_medico trg_spostamento_medico_sala_op; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER trg_spostamento_medico_sala_op AFTER INSERT OR DELETE ON public.sala_operatoria_medico FOR EACH ROW EXECUTE FUNCTION public.fn_logga_spostamento_medico();


--
-- TOC entry 4871 (class 2620 OID 17010)
-- Name: paziente trg_spostamento_sala_paziente; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER trg_spostamento_sala_paziente AFTER UPDATE ON public.paziente FOR EACH ROW EXECUTE FUNCTION public.fn_logga_spostamento_paziente();


--
-- TOC entry 4873 (class 2620 OID 17011)
-- Name: sala_operatoria trg_spostamento_sala_paziente_2; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER trg_spostamento_sala_paziente_2 AFTER UPDATE ON public.sala_operatoria FOR EACH ROW EXECUTE FUNCTION public.fn_logga_spostamento_paziente();


--
-- TOC entry 4868 (class 2606 OID 16911)
-- Name: evn_spostamenti_medici evn_spostamenti_medici_id_medico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.evn_spostamenti_medici
    ADD CONSTRAINT evn_spostamenti_medici_id_medico_fkey FOREIGN KEY (id_medico) REFERENCES public.medico(identificativo_medico) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4869 (class 2606 OID 16916)
-- Name: evn_spostamenti_medici evn_spostamenti_medici_id_sala_operatoria_arrivo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.evn_spostamenti_medici
    ADD CONSTRAINT evn_spostamenti_medici_id_sala_operatoria_arrivo_fkey FOREIGN KEY (id_sala_operatoria_arrivo) REFERENCES public.sala_operatoria(codice_sala) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4870 (class 2606 OID 16921)
-- Name: evn_spostamenti_medici evn_spostamenti_medici_id_sala_ricovero_arrivo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.evn_spostamenti_medici
    ADD CONSTRAINT evn_spostamenti_medici_id_sala_ricovero_arrivo_fkey FOREIGN KEY (id_sala_ricovero_arrivo) REFERENCES public.sala_ricovero(codice_sala) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4865 (class 2606 OID 16862)
-- Name: evn_spostamenti_pazienti evn_spostamenti_pazienti_id_paziente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.evn_spostamenti_pazienti
    ADD CONSTRAINT evn_spostamenti_pazienti_id_paziente_fkey FOREIGN KEY (id_paziente) REFERENCES public.paziente(identificativo_paziente) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4866 (class 2606 OID 16867)
-- Name: evn_spostamenti_pazienti evn_spostamenti_pazienti_id_sala_operatoria_arrivo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.evn_spostamenti_pazienti
    ADD CONSTRAINT evn_spostamenti_pazienti_id_sala_operatoria_arrivo_fkey FOREIGN KEY (id_sala_operatoria_arrivo) REFERENCES public.sala_operatoria(codice_sala) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4867 (class 2606 OID 16872)
-- Name: evn_spostamenti_pazienti evn_spostamenti_pazienti_id_sala_ricovero_arrivo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.evn_spostamenti_pazienti
    ADD CONSTRAINT evn_spostamenti_pazienti_id_sala_ricovero_arrivo_fkey FOREIGN KEY (id_sala_ricovero_arrivo) REFERENCES public.sala_ricovero(codice_sala) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4853 (class 2606 OID 16598)
-- Name: medico fk_medico_persona; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.medico
    ADD CONSTRAINT fk_medico_persona FOREIGN KEY (codice_fiscale) REFERENCES public.persona(codice_fiscale) ON DELETE CASCADE;


--
-- TOC entry 4854 (class 2606 OID 16603)
-- Name: medico fk_medico_sala_ricovero; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.medico
    ADD CONSTRAINT fk_medico_sala_ricovero FOREIGN KEY (codice_sala_ricovero) REFERENCES public.sala_ricovero(codice_sala) ON DELETE SET NULL;


--
-- TOC entry 4851 (class 2606 OID 16576)
-- Name: paziente fk_paziente_persona; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.paziente
    ADD CONSTRAINT fk_paziente_persona FOREIGN KEY (codice_fiscale) REFERENCES public.persona(codice_fiscale) ON DELETE CASCADE;


--
-- TOC entry 4852 (class 2606 OID 16581)
-- Name: paziente fk_paziente_sala_ricovero; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.paziente
    ADD CONSTRAINT fk_paziente_sala_ricovero FOREIGN KEY (codice_sala_ricovero) REFERENCES public.sala_ricovero(codice_sala) ON DELETE SET NULL;


--
-- TOC entry 4855 (class 2606 OID 16619)
-- Name: sala_operatoria fk_so_ospedale; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sala_operatoria
    ADD CONSTRAINT fk_so_ospedale FOREIGN KEY (identificativo_ospedale) REFERENCES public.ospedale(identificativo_ospedale) ON DELETE CASCADE;


--
-- TOC entry 4856 (class 2606 OID 16624)
-- Name: sala_operatoria fk_so_paziente; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sala_operatoria
    ADD CONSTRAINT fk_so_paziente FOREIGN KEY (identificativo_paziente) REFERENCES public.paziente(identificativo_paziente) ON DELETE RESTRICT;


--
-- TOC entry 4857 (class 2606 OID 16641)
-- Name: sala_operatoria_medico fk_som_medico; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sala_operatoria_medico
    ADD CONSTRAINT fk_som_medico FOREIGN KEY (identificativo_medico) REFERENCES public.medico(identificativo_medico) ON DELETE RESTRICT;


--
-- TOC entry 4858 (class 2606 OID 16636)
-- Name: sala_operatoria_medico fk_som_sala; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sala_operatoria_medico
    ADD CONSTRAINT fk_som_sala FOREIGN KEY (codice_sala) REFERENCES public.sala_operatoria(codice_sala) ON DELETE CASCADE;


--
-- TOC entry 4850 (class 2606 OID 16562)
-- Name: sala_ricovero fk_sr_ospedale; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sala_ricovero
    ADD CONSTRAINT fk_sr_ospedale FOREIGN KEY (identificativo_ospedale) REFERENCES public.ospedale(identificativo_ospedale) ON DELETE CASCADE;


--
-- TOC entry 4864 (class 2606 OID 16825)
-- Name: log_user log_user_identificativo_medico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.log_user
    ADD CONSTRAINT log_user_identificativo_medico_fkey FOREIGN KEY (identificativo_medico) REFERENCES public.medico(identificativo_medico) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4859 (class 2606 OID 16936)
-- Name: operazione operazione_codice_sala_operatoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operazione
    ADD CONSTRAINT operazione_codice_sala_operatoria_fkey FOREIGN KEY (codice_sala_operatoria) REFERENCES public.sala_operatoria(codice_sala) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4860 (class 2606 OID 16991)
-- Name: operazione operazione_id_paziente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operazione
    ADD CONSTRAINT operazione_id_paziente_fkey FOREIGN KEY (id_paziente) REFERENCES public.paziente(identificativo_paziente) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4861 (class 2606 OID 16996)
-- Name: operazione_medico operazione_medico_id_medico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operazione_medico
    ADD CONSTRAINT operazione_medico_id_medico_fkey FOREIGN KEY (id_medico) REFERENCES public.medico(identificativo_medico) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4862 (class 2606 OID 17001)
-- Name: operazione_medico operazione_medico_id_operazione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operazione_medico
    ADD CONSTRAINT operazione_medico_id_operazione_fkey FOREIGN KEY (id_operazione) REFERENCES public.operazione(id_operazione) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4863 (class 2606 OID 16926)
-- Name: referto referto_id_operazione_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.referto
    ADD CONSTRAINT referto_id_operazione_fkey FOREIGN KEY (id_operazione) REFERENCES public.operazione(id_operazione) ON UPDATE RESTRICT ON DELETE RESTRICT;


-- Completed on 2026-11-21 13:31:55

--
-- PostgreSQL database dump complete
--

\unrestrict IglYfeB2avgW5DPfjJ7d5VQDqMz7T4VFU6x5sbQvqJGiYjqxF6AgFuYQWAQact5

