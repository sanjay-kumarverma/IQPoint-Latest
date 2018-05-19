--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.19
-- Dumped by pg_dump version 9.2.19
-- Started on 2017-05-14 17:06:08

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 1 (class 3079 OID 11727)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2053 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 183 (class 1259 OID 16582)
-- Name: answersheet; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE answersheet (
    id bigint NOT NULL,
    userid bigint,
    qpid bigint,
    questionid bigint,
    answer character varying,
    examexecutionid bigint
);


ALTER TABLE public.answersheet OWNER TO postgres;

--
-- TOC entry 2054 (class 0 OID 0)
-- Dependencies: 183
-- Name: COLUMN answersheet.examexecutionid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN answersheet.examexecutionid IS 'exam execution id';


--
-- TOC entry 177 (class 1259 OID 16535)
-- Name: examassignment; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE examassignment (
    id bigint NOT NULL,
    examid bigint,
    userid bigint,
    updatedon timestamp with time zone,
    updatedby bigint,
    isdeleted boolean,
    examexecutionid bigint
);


ALTER TABLE public.examassignment OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 16540)
-- Name: examexecution; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE examexecution (
    id bigint NOT NULL,
    venue character varying,
    timetaken bigint,
    updatedon timestamp with time zone,
    updatedby bigint,
    isdeleted boolean,
    examid bigint,
    qpid bigint,
    userid bigint,
    score real
);


ALTER TABLE public.examexecution OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 16639)
-- Name: examtype; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE examtype (
    id bigint NOT NULL,
    examtype character varying
);


ALTER TABLE public.examtype OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 16450)
-- Name: idgen; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE idgen (
    genname character varying(50) NOT NULL,
    genval bigint
);


ALTER TABLE public.idgen OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 16519)
-- Name: ilsexam; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ilsexam (
    id bigint NOT NULL,
    exam character varying,
    examdate timestamp with time zone,
    examduration bigint,
    qpid bigint,
    updatedby bigint,
    updatedon timestamp with time zone,
    isdeleted boolean,
    examtype bigint,
    cummulativepercent real
);


ALTER TABLE public.ilsexam OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 16495)
-- Name: ilslevel; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ilslevel (
    id bigint NOT NULL,
    level character varying,
    levelname character varying
);


ALTER TABLE public.ilslevel OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 16455)
-- Name: ilsrole; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ilsrole (
    id bigint NOT NULL,
    role character varying
);


ALTER TABLE public.ilsrole OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 16503)
-- Name: ilssubject; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ilssubject (
    id bigint NOT NULL,
    subject character varying,
    levelid bigint
);


ALTER TABLE public.ilssubject OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 16511)
-- Name: ilstopic; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ilstopic (
    id bigint NOT NULL,
    topic character varying,
    subjectid bigint
);


ALTER TABLE public.ilstopic OWNER TO postgres;

--
-- TOC entry 169 (class 1259 OID 16442)
-- Name: ilsuser; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ilsuser (
    id bigint NOT NULL,
    firstname character varying,
    lastname character varying,
    street character varying,
    city character varying,
    state character varying,
    zip character varying,
    email character varying,
    password character varying,
    updatedby bigint,
    isdeleted boolean,
    country character varying,
    roleid bigint,
    updatedon timestamp with time zone,
    profileid bigint,
    comments character varying,
    phone character varying,
    levelid bigint
);


ALTER TABLE public.ilsuser OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 16487)
-- Name: ilsuserprofile; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ilsuserprofile (
    id bigint NOT NULL,
    photourl character varying,
    fathername character varying,
    fatherphone character varying,
    fatheremail character varying,
    mothername character varying,
    motherphone character varying,
    motheremail character varying,
    studiesin character varying,
    studiesinsection character varying,
    admissionnumber character varying,
    employeenumber character varying,
    bloodgroup character varying,
    levelname character varying
);


ALTER TABLE public.ilsuserprofile OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 16556)
-- Name: qpsection; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE qpsection (
    id bigint NOT NULL,
    qpid bigint,
    maxmarks real,
    sectiontype bigint,
    sectionname character varying
);


ALTER TABLE public.qpsection OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 16564)
-- Name: qptopic; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE qptopic (
    qpid bigint,
    topicid bigint
);


ALTER TABLE public.qptopic OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 16574)
-- Name: question; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE question (
    id bigint NOT NULL,
    questiontype bigint,
    question character varying,
    optionfirst character varying,
    optionsecond character varying,
    optionthird character varying,
    optionfourth character varying,
    answer character varying,
    maxmarks real,
    subjectid bigint,
    topicid bigint,
    sectionid bigint,
    updatedby bigint,
    updatedon timestamp with time zone,
    isdeleted boolean
);


ALTER TABLE public.question OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 16548)
-- Name: questionpaper; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE questionpaper (
    id bigint NOT NULL,
    qpname character varying,
    subjectid bigint,
    passpercent real,
    updatedby bigint,
    updatedon timestamp with time zone,
    isdeleted boolean,
    levelid bigint
);


ALTER TABLE public.questionpaper OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 16595)
-- Name: questiontypemaster; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE questiontypemaster (
    id bigint NOT NULL,
    questiontype character varying
);


ALTER TABLE public.questiontypemaster OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 16590)
-- Name: sectionquestion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sectionquestion (
    questionid bigint,
    sectionid bigint
);


ALTER TABLE public.sectionquestion OWNER TO postgres;

--
-- TOC entry 2042 (class 0 OID 16582)
-- Dependencies: 183
-- Data for Name: answersheet; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY answersheet (id, userid, qpid, questionid, answer, examexecutionid) FROM stdin;
153	16	2	2	Mixing sugar to water	48
154	16	2	1	plants leaves synthesis of sunlight	48
155	16	2	3	germs killing germs	48
156	16	2	5	salt dissolves in water	48
157	16	2	4	Carbon Dioxide	48
158	16	2	3	germs eating plants	49
159	16	2	1	plants leaves synthesis of sunlight	49
160	16	2	2	Water turns to ice	49
188	16	2	4	Oxygen	62
163	16	2	2	Mixing salt in water	54
164	16	2	1	plants leaves synthesis of sunlight	54
161	16	2	3	Seeds coming to life when sown	54
162	16	2	5	sugar turn to smoke	54
165	16	2	4	Nitrogen	54
166	16	2	5	sugar turn to smoke	55
167	16	2	4	Oxygen	55
168	16	2	1	plants leaves synthesis of sunlight	55
169	16	2	2	Water turn to gaseous form when heated	55
170	16	2	3	Germs coming out of earth	55
171	16	2	4	Hydrogen	58
172	16	2	5	salt dissolves in water	58
173	16	2	3	Seeds coming to life when sown	58
174	16	2	2	Water turn to gaseous form when heated	58
175	16	2	1	plants leaves synthesis of sunlight	58
176	16	2	3	germs eating plants	59
177	16	2	1	Sun rays falling on stone	59
178	16	2	5	salt dissolves in water	59
179	16	2	1	Sun rays falling on stone	60
189	16	2	4	Nitrogen	70
190	16	2	5	water turn into ice	70
191	16	2	1	plants leaves synthesis of sunlight	70
192	16	2	2	Mixing sugar to water	70
180	16	2	2	Mixing salt in water	60
181	16	2	3	Seeds coming to life when sown	60
182	16	2	5	water turns to gas	60
183	16	2	4	Nitrogen	60
184	16	2	3	Seeds coming to life when sown	62
185	16	2	1	Sun rays falling on stone	62
186	16	2	2	Mixing salt in water	62
187	16	2	5	water turns to gas	62
193	16	2	3	Seeds coming to life when sown	70
194	16	2	2	Mixing sugar to water	71
195	16	2	1	plants leaves synthesis of sunlight	71
196	16	2	3	Germs coming out of earth	71
197	16	2	5	water turn into ice	71
198	16	2	4	Nitrogen	71
199	16	2	2	Water turn to gaseous form when heated	72
200	16	2	1	Sun rays falling on stone	72
201	16	2	5	water turn into ice	72
202	16	2	4	Nitrogen	72
203	16	2	3	germs eating plants	72
204	16	2	4	Nitrogen	73
205	16	2	5	sugar turn to smoke	73
206	16	2	1	plants leaves synthesis of sunlight	73
207	16	2	2	Water turns to ice	73
208	16	2	3	germs eating plants	73
209	16	2	5	water turn into ice	74
210	16	2	4	Nitrogen	74
211	16	2	3	germs eating plants	74
212	16	2	2	Mixing salt in water	74
213	16	2	1	plants leaves synthesis of sunlight	74
214	16	2	1	plants leaves synthesis of sunlight	75
215	16	2	2	Water turns to ice	75
216	16	2	4	Nitrogen	75
217	16	2	5	salt dissolves in water	75
218	16	2	3	germs eating plants	75
219	16	2	2	Water turn to gaseous form when heated	76
220	16	2	1	plants leaves synthesis of sunlight	76
221	16	2	3	Seeds coming to life when sown	76
222	16	2	5	sugar turn to smoke	76
223	16	2	4	Oxygen	76
224	16	2	3	Seeds coming to life when sown	77
225	16	2	5	water turn into ice	77
226	16	2	4	Nitrogen	77
227	16	2	2	Mixing sugar to water	77
228	16	2	1	moon light falling on earth	77
229	16	2	1	plants leaves synthesis of sunlight	78
232	16	2	4	Hydrogen	78
281	16	2	4	Oxygen	91
293	16	2	4	Oxygen	93
308	16	2	4	Oxygen	96
333	16	2	4	Oxygen	101
230	16	2	2	Water turn to gaseous form when heated	78
231	16	2	5	water turn into ice	78
233	16	2	3	germs killing germs	78
234	16	2	3	Seeds coming to life when sown	79
235	16	2	5	water turn into ice	79
236	16	2	4	Nitrogen	79
237	16	2	2	Mixing salt in water	79
238	16	2	1	moon light falling on earth	79
239	16	2	1	plants leaves synthesis of sunlight	80
240	16	2	2	Water turn to gaseous form when heated	80
241	16	2	3	Seeds coming to life when sown	80
242	16	2	5	water turn into ice	80
243	16	2	4	Nitrogen	80
244	16	2	3	Seeds coming to life when sown	81
245	16	2	2	Water turn to gaseous form when heated	81
246	16	2	1	plants leaves synthesis of sunlight	81
247	16	2	5	water turn into ice	81
248	16	2	4	Nitrogen	81
249	16	2	5	water turns to ice	82
250	16	2	4	Nitrogen	82
251	16	2	3	Seeds coming to life when sown	82
252	16	2	1	plants leaves synthesis of sunlight	82
253	16	2	2	Water turn to gaseous form when heated	82
254	16	2	3	Germs coming out of earth	83
255	16	2	5	water turns to ice	83
256	16	2	4	Nitrogen	83
257	16	2	2	Mixing salt in water	83
258	16	2	1	plants leaves synthesis of sunlight	83
259	16	2	1	plants leaves synthesis of sunlight	84
260	16	2	2	Water turns to ice	84
261	16	2	4	Nitrogen	84
262	16	2	5	sugar turn to smoke	84
263	16	2	3	germs killing germs	84
264	16	2	2	Water turn to gaseous form when heated	85
265	16	2	1	plants leaves synthesis of sunlight	85
266	16	2	4	Nitrogen	85
267	16	2	5	salt dissolves in water	85
268	16	2	3	germs eating plants	85
269	16	2	5	water turns to ice	86
270	16	2	4	Nitrogen	86
271	16	2	1	Sun rays falling on stone	86
272	16	2	2	Mixing salt in water	86
273	16	2	3	germs eating plants	86
274	16	2	3	Seeds coming to life when sown	88
275	16	2	2	Water turn to gaseous form when heated	88
276	16	2	1	plants leaves synthesis of sunlight	88
277	16	2	5	water turns to ice	88
278	16	2	4	Nitrogen	88
279	16	2	3	Seeds coming to life when sown	91
280	16	2	5	water turns to ice	91
282	16	2	1	Sun rays falling on stone	91
283	16	2	2	Mixing salt in water	91
284	16	2	5	water turns to ice	92
285	16	2	4	Nitrogen	92
286	16	2	3	germs eating plants	92
287	16	2	2	Mixing salt in water	92
288	16	2	1	moon light falling on earth	92
289	16	2	1	plants leaves synthesis of sunlight	93
290	16	2	2	Water turn to gaseous form when heated	93
291	16	2	3	germs eating plants	93
292	16	2	5	salt dissolves in water	93
294	16	2	5	water turns to ice	94
295	16	2	4	Nitrogen	94
296	16	2	3	Seeds coming to life when sown	94
297	16	2	1	plants leaves synthesis of sunlight	94
298	16	2	2	Mixing sugar to water	94
299	16	2	5	water turns to ice	95
300	16	2	4	Nitrogen	95
301	16	2	1	plants leaves synthesis of sunlight	95
302	16	2	2	Water turn to gaseous form when heated	95
303	16	2	3	Germs coming out of earth	95
304	16	2	3	Seeds coming to life when sown	96
305	16	2	2	Water turn to gaseous form when heated	96
306	16	2	1	plants leaves synthesis of sunlight	96
307	16	2	5	water turns to ice	96
309	16	2	2	Water turn to gaseous form when heated	97
312	16	2	4	Nitrogen	97
317	16	2	4	Oxygen	98
345	16	2	4	Oxygen	104
433	39	25	13	MERCURY	122
310	16	2	1	plants leaves synthesis of sunlight	97
311	16	2	3	Seeds coming to life when sown	97
313	16	2	5	salt dissolves in water	97
314	16	2	3	Seeds coming to life when sown	98
315	16	2	1	plants leaves synthesis of sunlight	98
316	16	2	2	Water turns to ice	98
318	16	2	5	salt dissolves in water	98
319	16	2	1	plants leaves synthesis of sunlight	99
320	16	2	2	Water turn to gaseous form when heated	99
321	16	2	3	Seeds coming to life when sown	99
322	16	2	4	Nitrogen	99
323	16	2	5	salt dissolves in water	99
324	16	2	5	sugar turn to smoke	100
325	16	2	4	Nitrogen	100
326	16	2	3	Seeds coming to life when sown	100
327	16	2	1	moon light falling on earth	100
328	16	2	2	Mixing sugar to water	100
329	16	2	3	Seeds coming to life when sown	101
330	16	2	2	Mixing sugar to water	101
331	16	2	1	plants leaves synthesis of sunlight	101
332	16	2	5	water turns to ice	101
334	16	2	5	water turns to ice	102
335	16	2	4	Nitrogen	102
336	16	2	2	Water turns to ice	102
337	16	2	1	plants leaves synthesis of sunlight	102
338	16	2	3	germs killing germs	102
339	16	2	5	water turns to ice	103
340	16	2	4	Nitrogen	103
341	16	2	3	Seeds coming to life when sown	103
342	16	2	2	Mixing sugar to water	103
343	16	2	1	plants leaves synthesis of sunlight	103
344	16	2	3	Seeds coming to life when sown	104
346	16	2	5	sugar turn to smoke	104
347	16	2	4	Nitrogen	105
348	16	2	5	water turns to ice	105
349	16	2	3	Seeds coming to life when sown	105
350	16	2	2	Mixing salt in water	105
351	16	2	1	moon light falling on earth	105
352	16	2	3	Seeds coming to life when sown	106
353	16	2	4	Nitrogen	106
354	16	2	5	water turns to ice	106
355	16	2	2	Mixing salt in water	106
356	16	2	1	Sun rays falling on stone	106
357	16	2	3	Seeds coming to life when sown	107
358	16	2	4	Nitrogen	107
359	16	2	5	water turns to ice	107
360	16	2	1	moon light falling on earth	107
361	16	2	2	Mixing salt in water	107
362	16	2	5	water turns to ice	108
363	16	2	4	Nitrogen	108
364	16	2	1	plants leaves synthesis of sunlight	108
365	16	2	2	Mixing salt in water	108
366	16	2	3	germs eating plants	108
367	16	2	5	water turns to ice	109
368	16	2	4	Nitrogen	109
369	16	2	1	plants leaves synthesis of sunlight	109
370	16	2	2	Mixing salt in water	109
371	16	2	3	Seeds coming to life when sown	109
372	16	2	5	water turns to ice	110
373	16	2	4	Nitrogen	110
374	16	2	1	plants leaves synthesis of sunlight	110
375	16	2	2	Mixing salt in water	110
376	16	2	3	germs eating plants	110
380	16	2	4	Oxygen	111
377	16	2	1	plants leaves synthesis of sunlight	111
378	16	2	2	Water turns to ice	111
379	16	2	3	Seeds coming to life when sown	111
381	16	2	5	water turns to ice	111
382	16	2	4	Nitrogen	112
383	16	2	5	water turns to ice	112
384	16	2	3	germs eating plants	112
385	16	2	2	Water turn to gaseous form when heated	112
386	16	2	1	Sun rays falling on stone	112
387	16	2	3	Seeds coming to life when sown	113
388	16	2	2	Water turn to gaseous form when heated	113
389	16	2	1	plants leaves synthesis of sunlight	113
390	16	2	4	Carbon Dioxide	113
391	16	2	5	sugar turn to smoke	113
434	39	25	12	COPPER	122
445	16	25	14	4	123
392	16	2	3	Seeds coming to life when sown	114
393	16	2	1	plants leaves synthesis of sunlight	114
394	16	2	2	Water turn to gaseous form when heated	114
395	16	2	4	Carbon Dioxide	114
396	16	2	5	water turns to gas	114
397	16	2	1	plants leaves synthesis of sunlight	115
398	16	2	2	Water turn to gaseous form when heated	115
399	16	2	5	sugar turn to smoke	115
400	16	2	4	Nitrogen	115
401	16	2	3	Seeds coming to life when sown	115
402	16	2	5	sugar turn to smoke	116
403	16	2	4	Nitrogen	116
404	16	2	2	Water turn to gaseous form when heated	116
405	16	2	1	plants leaves synthesis of sunlight	116
406	16	2	3	Seeds coming to life when sown	116
407	16	2	4	Nitrogen	117
408	16	2	5	water turns to ice	117
409	16	2	1	plants leaves synthesis of sunlight	117
410	16	2	2	Water turn to gaseous form when heated	117
411	16	2	3	Seeds coming to life when sown	117
412	16	2	3	Seeds coming to life when sown	118
413	16	2	5	water turns to ice	118
414	16	2	4	Nitrogen	118
415	16	2	1	moon light falling on earth	118
416	16	2	2	Mixing sugar to water	118
417	16	2	5	water turns to ice	119
418	16	2	4	Nitrogen	119
419	16	2	3	Seeds coming to life when sown	119
420	16	2	1	moon light falling on earth	119
421	16	2	2	Mixing salt in water	119
422	16	2	5	water turns to ice	120
423	16	2	4	Nitrogen	120
424	16	2	3	Seeds coming to life when sown	120
425	16	2	1	plants leaves synthesis of sunlight	120
426	16	2	2	Mixing sugar to water	120
427	16	2	5	Water turns into ice	121
428	16	2	2	Water turn to gaseous form when heated	121
429	16	2	3	Germs eating plants	121
430	16	2	1	Sun rays falling on stone	121
431	16	2	4	Nitrogen	121
436	39	25	14	7	122
438	39	25	10	OXYGEN	122
432	39	25	11	GAS THAT DOES NOT REACTS WITH ANYTHING	122
435	39	25	15	Light changes its path when it enters water from air	122
437	39	25	3	Germs coming out of earth	122
439	39	25	2	Water turn to gaseous form when heated	122
440	39	25	1	Plants leaves synthesis of sunlight	122
441	39	25	4	Nitrogen	122
442	39	25	5	Water turns into ice	122
446	16	25	12	WOOD	123
443	16	25	13	MERCURY	123
452	16	25	10	OXYGEN	123
444	16	25	11	GAS THAT DOES NOT REACTS WITH ANYTHING	123
447	16	25	15	Light changes its path when it enters water from air	123
448	16	25	5	Water turns into ice	123
449	16	25	2	Water turn to gaseous form when heated	123
450	16	25	3	Germs coming out of earth	123
451	16	25	4	Nitrogen	123
453	16	25	1	Plants leaves synthesis of sunlight	123
\.


--
-- TOC entry 2036 (class 0 OID 16535)
-- Dependencies: 177
-- Data for Name: examassignment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY examassignment (id, examid, userid, updatedon, updatedby, isdeleted, examexecutionid) FROM stdin;
2	7	16	2017-02-14 18:39:58.059+05:30	17	f	\N
3	8	16	2017-02-14 18:39:58.059+05:30	17	f	\N
4	9	16	2017-02-14 18:39:58.059+05:30	17	f	\N
5	10	16	2017-02-14 18:39:58.059+05:30	17	f	\N
6	15	16	2017-02-14 18:39:58.059+05:30	17	f	\N
11	19	39	2017-02-14 18:39:58.059+05:30	16	f	\N
\.


--
-- TOC entry 2037 (class 0 OID 16540)
-- Dependencies: 178
-- Data for Name: examexecution; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY examexecution (id, venue, timetaken, updatedon, updatedby, isdeleted, examid, qpid, userid, score) FROM stdin;
77	Default	24203	2017-03-16 17:48:16.947+05:30	10000	f	7	2	16	\N
78	Default	27419	2017-03-16 17:52:44.97+05:30	10000	f	7	2	16	\N
79	Default	44988	2017-03-16 17:57:06.172+05:30	10000	f	7	2	16	\N
80	Default	47892	2017-03-16 18:16:17.55+05:30	10000	f	7	2	16	\N
81	Default	38692	2017-03-16 18:20:54.254+05:30	10000	f	7	2	16	\N
82	Default	26605	2017-03-16 18:24:08.098+05:30	10000	f	7	2	16	\N
83	Default	29279	2017-03-16 18:36:11.298+05:30	10000	f	7	2	16	\N
84	Default	27316	2017-03-16 18:38:52.444+05:30	10000	f	7	2	16	\N
85	Default	27445	2017-03-16 18:43:27.786+05:30	10000	f	7	2	16	\N
86	Default	30448	2017-03-16 18:44:35.441+05:30	10000	f	7	2	16	\N
87	Default	5762	2017-03-16 18:45:09.389+05:30	10000	f	7	2	16	\N
88	Default	24637	2017-03-16 18:46:36.134+05:30	10000	f	7	2	16	\N
89	Default	6778	2017-03-16 18:50:54.509+05:30	10000	f	7	2	16	\N
90	Default	5355	2017-03-16 18:59:12.188+05:30	10000	f	7	2	16	\N
91	Default	23510	2017-03-16 19:16:35.751+05:30	10000	f	7	2	16	\N
92	Default	23086	2017-03-16 19:19:01.553+05:30	10000	f	7	2	16	\N
93	Default	31644	2017-03-16 19:21:57.564+05:30	10000	f	7	2	16	\N
94	Default	25595	2017-03-16 19:22:49.448+05:30	10000	f	7	2	16	\N
95	Default	25211	2017-03-16 19:27:13.792+05:30	10000	f	7	2	16	\N
96	Default	28058	2017-03-16 19:29:59.193+05:30	10000	f	7	2	16	\N
97	Default	38588	2017-03-16 19:33:16.265+05:30	10000	f	7	2	16	\N
98	Default	33523	2017-03-17 14:27:33.419+05:30	10000	f	7	2	16	\N
62	Default	34701	2017-03-16 15:11:30.845+05:30	10000	f	7	2	16	\N
64	Default	6415	2017-03-16 15:15:42.756+05:30	10000	f	7	2	16	\N
65	Default	11213	2017-03-16 15:17:12.874+05:30	10000	f	7	2	16	\N
66	Default	7015	2017-03-16 15:19:22.087+05:30	10000	f	7	2	16	\N
67	Default	5900	2017-03-16 15:20:57.483+05:30	10000	f	7	2	16	\N
68	Default	6487	2017-03-16 15:22:09.828+05:30	10000	f	7	2	16	\N
69	Default	5767	2017-03-16 15:26:47.123+05:30	10000	f	7	2	16	\N
71	Default	37295	2017-03-16 17:18:59.456+05:30	10000	f	7	2	16	\N
72	Default	34605	2017-03-16 17:23:47.884+05:30	10000	f	7	2	16	\N
73	Default	28337	2017-03-16 17:26:27.765+05:30	10000	f	7	2	16	\N
74	Default	22475	2017-03-16 17:29:12.829+05:30	10000	f	7	2	16	\N
75	Default	65095	2017-03-16 17:38:23.371+05:30	10000	f	7	2	16	\N
76	Default	24747	2017-03-16 17:43:40.786+05:30	10000	f	7	2	16	\N
99	Default	27571	2017-03-17 14:30:36.668+05:30	10000	f	7	2	16	\N
100	Default	21254	2017-03-17 14:31:15.591+05:30	10000	f	7	2	16	\N
101	Default	24369	2017-03-17 14:41:12.174+05:30	10000	f	7	2	16	\N
102	Default	22118	2017-03-17 14:42:54.047+05:30	10000	f	7	2	16	\N
103	Default	19335	2017-03-17 14:44:11.648+05:30	10000	f	7	2	16	\N
104	Default	30829	2017-03-17 15:27:44.264+05:30	10000	f	7	2	16	\N
105	Default	31935	2017-03-17 16:03:24.453+05:30	10000	f	7	2	16	\N
106	Default	30570	2017-03-17 16:07:40.31+05:30	10000	f	7	2	16	\N
107	Default	22236	2017-03-17 16:09:02.4+05:30	10000	f	7	2	16	\N
108	Default	25724	2017-03-17 16:15:05.97+05:30	10000	f	7	2	16	0
110	Default	20375	2017-03-17 16:25:16.681+05:30	10000	f	7	2	16	60
112	Default	33482	2017-03-17 16:46:02.203+05:30	10000	f	7	2	16	61
113	Default	33115	2017-03-17 16:55:14.884+05:30	10000	f	7	2	16	61.9099998
114	Default	30304	2017-03-17 17:05:55.1+05:30	10000	f	7	2	16	61.9099998
111	Default	46472	2017-03-17 16:39:45.561+05:30	10000	t	7	2	16	50
115	Default	72178	2017-03-20 12:55:04.097+05:30	10000	f	7	2	16	85.7200012
47	Default	0	2017-03-14 17:57:48.461+05:30	10000	t	7	2	16	\N
109	Default	21893	2017-03-17 16:19:31.412+05:30	10000	t	7	2	16	0
46	Default	0	2017-03-14 17:57:26.398+05:30	10000	t	7	2	16	99.9899979
48	Default	0	2017-03-14 17:58:09.868+05:30	10000	t	7	2	16	\N
49	Default	0	2017-03-14 17:58:56.361+05:30	10000	t	7	2	16	\N
50	Default	0	2017-03-14 18:00:42.292+05:30	10000	t	7	2	16	\N
63	Default	18725	2017-03-16 15:14:35.069+05:30	10000	t	7	2	16	\N
51	Default	0	2017-03-14 18:11:43.29+05:30	10000	t	7	2	16	\N
52	Default	0	2017-03-14 18:13:16.947+05:30	10000	t	7	2	16	\N
53	Default	0	2017-03-14 18:16:27.115+05:30	10000	t	7	2	16	\N
54	Default	0	2017-03-14 18:19:33.322+05:30	10000	t	7	2	16	\N
55	Default	0	2017-03-14 18:30:47.097+05:30	10000	t	7	2	16	\N
56	Default	0	2017-03-14 18:36:22.66+05:30	10000	t	7	2	16	\N
70	Default	35366	2017-03-16 16:49:26.751+05:30	10000	t	7	2	16	\N
57	Default	0	2017-03-14 18:38:33.368+05:30	10000	t	7	2	16	\N
58	Default	0	2017-03-14 18:44:31.561+05:30	10000	t	7	2	16	\N
59	Default	0	2017-03-15 12:21:14.047+05:30	10000	t	7	2	16	\N
60	Default	37496	2017-03-15 12:53:07.553+05:30	10000	t	7	2	16	\N
61	Default	0	2017-03-15 13:08:25.114+05:30	10000	t	7	2	16	\N
116	Default	28416	2017-03-21 17:59:31.259+05:30	10000	f	7	2	16	85.7200012
117	Default	30737	2017-03-21 18:07:52.807+05:30	10000	f	7	2	16	100
118	Default	58546	2017-03-23 19:19:03.813+05:30	10000	f	7	2	16	52.3899994
119	Default	58811	2017-03-24 15:37:40.141+05:30	10000	f	7	2	16	52.3899994
120	Default	40705	2017-03-28 16:28:39.215+05:30	10000	f	7	2	16	76.1999969
121	Default	34412	2017-04-04 17:22:18.511+05:30	10000	f	7	2	16	61.9099998
122	Default	123025	2017-05-01 16:41:38.035+05:30	10000	f	19	25	39	87.2399979
123	Default	165446	2017-05-07 09:21:28.954+05:30	10000	f	7	25	16	65.9599991
\.


--
-- TOC entry 2045 (class 0 OID 16639)
-- Dependencies: 186
-- Data for Name: examtype; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY examtype (id, examtype) FROM stdin;
\.


--
-- TOC entry 2029 (class 0 OID 16450)
-- Dependencies: 170
-- Data for Name: idgen; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY idgen (genname, genval) FROM stdin;
QuestionIds	16
ExamIds	20
ExamAssignmentIds	12
QPTopicIds	1
ExamExecutionIds	124
AnswerSheetIds	454
QPIds	29
QPSectionIds	29
QuestionTypeIds	1
LevelIds	5
UserIds	66
ProfileIds	17
SubjectIds	16
TopicIds	12
\.


--
-- TOC entry 2035 (class 0 OID 16519)
-- Dependencies: 176
-- Data for Name: ilsexam; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ilsexam (id, exam, examdate, examduration, qpid, updatedby, updatedon, isdeleted, examtype, cummulativepercent) FROM stdin;
7	Monthly assesment exam-Science	2017-02-14 18:39:58.059+05:30	60	2	16	2017-02-14 18:39:58.059+05:30	f	\N	\N
8	Monthly assesment exam-Maths	2017-02-14 18:39:58.059+05:30	60	3	16	2017-02-14 18:39:58.059+05:30	f	\N	\N
9	Monthly assesment exam-English	2017-02-14 18:39:58.059+05:30	60	4	16	2017-02-14 18:39:58.059+05:30	f	\N	\N
10	Monthly assesment exam-Geography	2017-02-14 18:39:58.059+05:30	60	5	16	2017-02-14 18:39:58.059+05:30	f	\N	\N
15	SCIENCE PRACTICE TEST	2017-04-28 00:00:00+05:30	60	25	16	2017-04-28 17:05:29.639+05:30	f	1	0
19	PRACTICE TEST	2017-05-01 00:00:00+05:30	30	25	16	2017-05-01 14:13:27.447+05:30	f	1	0
\.


--
-- TOC entry 2032 (class 0 OID 16495)
-- Dependencies: 173
-- Data for Name: ilslevel; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ilslevel (id, level, levelname) FROM stdin;
3	PRIMARY	CLASS-III
4	PRIMARY	CLASS-IV
5	SECONDARY	CLASS-XI
6	SECONDARY	CLASS-X
1	PRIMARY	Class-I
2	PRIMARY	Class-II
\.


--
-- TOC entry 2030 (class 0 OID 16455)
-- Dependencies: 171
-- Data for Name: ilsrole; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ilsrole (id, role) FROM stdin;
2	ROLE_TEACHER
3	ROLE_STUDENT
4	ROLE_ORGANIZATION
1	ROLE_SUPERUSER
\.


--
-- TOC entry 2033 (class 0 OID 16503)
-- Dependencies: 174
-- Data for Name: ilssubject; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ilssubject (id, subject, levelid) FROM stdin;
1	English  (VI)	1
3	Maths (VI)	1
4	Geography (VI)	1
5	SST (VI)	1
13	ENGLISH	2
14	SCIENCE	2
15	MATHS	2
2	Science	1
16	NOT CREATED	0
\.


--
-- TOC entry 2034 (class 0 OID 16511)
-- Dependencies: 175
-- Data for Name: ilstopic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ilstopic (id, topic, subjectid) FROM stdin;
1	Integers	3
2	Fractions	3
3	Algebra	3
4	Geometry	3
5	Elements in nature	2
6	Laws of motion	2
7	Energy	2
10	TRIGONOMETRY	15
11	GEOMETRY	15
\.


--
-- TOC entry 2028 (class 0 OID 16442)
-- Dependencies: 169
-- Data for Name: ilsuser; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ilsuser (id, firstname, lastname, street, city, state, zip, email, password, updatedby, isdeleted, country, roleid, updatedon, profileid, comments, phone, levelid) FROM stdin;
40	shalini	verma	Not available	Not available	Not available	Not available	shalini@gmail.com	shaliniverma	16	f	Not available	2	2017-01-31 14:01:09.333+05:30	\N	\N	\N	1
16	Sanjay	verma	Ashoka-1, sector-34	Faridabad	Haryana	121003	sanjay@gmail.com	vermask	10000	f	India	1	2017-05-12 15:45:00.443+05:30	4	Plays piano, casio, banjo and drums	4444444444	2
53	Sanjay	verma	Ashoka-1, sector-35	Faridabad	Haryana	121003	sanjayverma@gmail.com	sanjay	10000	f	India	1	2017-02-13 12:19:57.328+05:30	\N	plays piano, plays banjo, plays guitar and sings	6666677777	\N
54	Sanjay	verma	Ashoka-1, sector-34	Faridabad	Haryana	121003	san@gmail.com	sanjay	10000	f	India	1	2017-02-02 18:20:20.234+05:30	\N	\N	\N	\N
17	Rakesh	Sharma	Ashoka-1, sector-34	Faridabad	Haryana	121003	rakesh@gmail.com	sharma	10000	f	India	3	2016-11-25 19:37:20.265+05:30	5	\N	\N	1
64	test	user	138, Ashoka-1, sector 34	Faridabad	Haryana	121003	test@user5.com	testuser	10000	f	Not available	2	2017-05-12 17:49:29.014+05:30	15	plays casio, football	999999999	5
38	aditya	verma	Not available	Not available	Not available	Not available	aditya@gmail.com	adityaverma	16	f	Not available	3	2017-01-29 14:33:41.139+05:30	\N	\N	\N	1
39	shreya	verma	Not available	Not available	Not available	Not available	shreya@gmail.com	shreyaverma	16	f	Not available	3	2017-01-30 20:18:33.75+05:30	\N	\N	\N	1
65	Satish	Verma	6 Sant nager, Dayalbagh	Agra	UP	560012	satish@gmail.com	satishverma	10000	f	Not available	3	2017-05-12 14:27:41.088+05:30	16	Plays cricket	8888888888	1
43	lokesh	bhai	Not available	Not available	Not available	Not available	lokesh@gmail.com	lokeshbhai	10000	f	Not available	3	2017-01-31 14:30:46.445+05:30	\N	\N	\N	\N
44	lalit	kohli	Not available	Not available	Not available	Not available	lalit@gmail.com	lalitkohli	10000	f	Not available	3	2017-01-31 14:33:27.345+05:30	\N	\N	\N	\N
45	arun	singh	Not available	Not available	Not available	Not available	arun@gmail.com	arunsingh	10000	f	Not available	2	2017-01-31 14:35:16.662+05:30	\N	\N	\N	\N
46	ramesh	verma	Not available	Not available	Not available	Not available	ramesh@gmail.com	rameshverma	10000	f	Not available	2	2017-01-31 14:39:14.151+05:30	\N	\N	\N	\N
41	manish	singh	Not available	Not available	Not available	Not available	manish@gmail.com	manishsingh	10000	f	Not available	2	2017-01-31 14:24:17.804+05:30	\N	\N	\N	\N
42	vinay	sood	Not available	Not available	Not available	Not available	vinay@gmail.com	vinaysood	10000	f	Not available	2	2017-01-31 14:25:38.62+05:30	\N	\N	\N	\N
\.


--
-- TOC entry 2031 (class 0 OID 16487)
-- Dependencies: 172
-- Data for Name: ilsuserprofile; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ilsuserprofile (id, photourl, fathername, fatherphone, fatheremail, mothername, motherphone, motheremail, studiesin, studiesinsection, admissionnumber, employeenumber, bloodgroup, levelname) FROM stdin;
5	\N	Sanjay Verma	9999999999	skvvks@gmail.com	\N	\N	\N	\N	\N	5338	\N	B+	\N
6	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
9	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
11	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
12	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
13	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
14	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
16	\N	Ramesh verma	78787878	ramesh@gmail.com	Sita verma	67676767	sita@gmail.com	1	C	5467	\N	O+	PRIMARY
4	\N	Sanjay Verma	9999999999	skvvks@gmail.com	Shalini Verma	4444455555	sv@gmail.com	2	C	5338	\N	Bbb+	PRIMARY
15	\N	sanjay	5555555555	sanjay@gmail.com	shalini	77777777777	shalini@gmail.com	5	C	5338	\N	B+	SECONDARY
\.


--
-- TOC entry 2039 (class 0 OID 16556)
-- Dependencies: 180
-- Data for Name: qpsection; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY qpsection (id, qpid, maxmarks, sectiontype, sectionname) FROM stdin;
1	2	\N	\N	\N
2	2	\N	\N	\N
3	2	\N	\N	\N
18	\N	50	1	Section A
19	24	50	1	Section A
20	24	50	2	Section B
21	25	50	1	Section A
22	25	50	2	Section B
23	26	50	1	Section A
24	26	50	2	Section B
25	27	50	1	Section A
26	27	50	2	Section B
27	28	50	1	Section A
28	28	50	1	Section B
\.


--
-- TOC entry 2040 (class 0 OID 16564)
-- Dependencies: 181
-- Data for Name: qptopic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY qptopic (qpid, topicid) FROM stdin;
24	5
25	5
26	5
27	5
28	5
\.


--
-- TOC entry 2041 (class 0 OID 16574)
-- Dependencies: 182
-- Data for Name: question; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY question (id, questiontype, question, optionfirst, optionsecond, optionthird, optionfourth, answer, maxmarks, subjectid, topicid, sectionid, updatedby, updatedon, isdeleted) FROM stdin;
5	1	What is crystalization ?	Water turns into ice	Sugar turn to smoke	Salt dissolves in water	Water turns to gas	Water turns into ice	3	2	5	3	16	2017-02-14 18:39:58.059+05:30	f
1	1	What is Photosynthesis ?	Sun rays falling on water	Sun rays falling on stone	Plants leaves synthesis of sunlight	Moon light falling on earth	Plants leaves synthesis of sunlight	5	2	5	1	16	2017-02-14 18:39:58.059+05:30	f
3	1	What is Germination ?	Germs coming out of earth	Seeds coming to life when sown	Germs eating plants	Germs killing germs	Seeds coming to life when sown	3	2	5	2	16	2017-02-14 18:39:58.059+05:30	f
10	1	WHAT GAS IS RELEASED DURING PHOTOSYNTHESIS ?	NITROGEN	CARBON DIOXIDE	OXYGEN	SULPHUR	OXYGEN	5	2	5	\N	10000	2017-04-01 19:24:51.368+05:30	f
11	1	WHAT IS INERT GAS ?	GAS THAT REACTS WITH WATER	GAS THAT REACTS WITH OIL	GAS THAT REACTS WITH OTHER GAS	GAS THAT DOES NOT REACTS WITH ANYTHING	GAS THAT DOES NOT REACTS WITH ANYTHING	5	2	5	\N	16	2017-04-01 20:00:37.32+05:30	f
12	1	WHAT IS BEST CONDUCTOR OF ELECTRICITY ?	PLASTIC	WOOD	COPPER	RUBBER	COPPER	5	2	5	\N	16	2017-04-01 20:06:46.629+05:30	f
13	1	WHAT METAL IS USED IN THERMOMETER ?	SILVER	COPPER	BRONZE	MERCURY	MERCURY	3	2	5	\N	16	2017-04-01 20:12:55.606+05:30	f
2	1	What is Evaporation ?	Water turns to ice	Mixing sugar to water	Mixing salt in water	Water turn to gaseous form when heated	Water turn to gaseous form when heated	5	2	5	1	16	2017-02-14 18:39:58.059+05:30	f
14	1	SUN LIGHT IS COMPOSED OF HOW MANY COLOURS ?	4	8	2	7	7	5	2	5	\N	16	2017-04-03 13:55:02.749+05:30	f
4	1	What gas is produced during ligtining ?	Carbon Dioxide	Nitrogen	Oxygen	Hydrogen	Nitrogen	5	2	5	3	16	2017-02-14 18:39:58.059+05:30	f
15	1	What is refraction ?	Change of color of light	Light enter water from air	Light changes its path when it enters water from air	Light moves in straight direction	Light moves in straight direction	3	2	5	\N	16	2017-04-03 14:26:58.385+05:30	f
\.


--
-- TOC entry 2038 (class 0 OID 16548)
-- Dependencies: 179
-- Data for Name: questionpaper; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY questionpaper (id, qpname, subjectid, passpercent, updatedby, updatedon, isdeleted, levelid) FROM stdin;
2	Science monthly test- Nature around us	2	60	16	2017-02-14 18:39:58.059+05:30	f	1
3	Science monthly test- Types of motion	2	60	16	2017-02-14 18:39:58.059+05:30	f	1
4	Science monthly test- Magnetism	2	60	16	2017-02-14 18:39:58.059+05:30	f	1
5	Science monthly test- Kinetic and Potential energy	2	60	16	2017-02-14 18:39:58.059+05:30	f	1
23	Science Test	2	80	10000	2017-04-17 17:27:37.063+05:30	f	1
24	Science Class Test	2	100	10000	2017-04-17 18:40:43.494+05:30	f	1
25	Another Test Paper	2	95	16	2017-04-17 19:10:47.085+05:30	f	1
26	Test Paper	2	90	16	2017-04-17 19:33:16.392+05:30	f	1
27	Test Paper	2	100	16	2017-04-18 15:36:15.525+05:30	f	1
28	Demo Test Paper	2	90	16	2017-05-07 09:27:55.876+05:30	f	1
\.


--
-- TOC entry 2044 (class 0 OID 16595)
-- Dependencies: 185
-- Data for Name: questiontypemaster; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY questiontypemaster (id, questiontype) FROM stdin;
1	MultipleChoiceSingleSelect
2	MultipleChoiceMultipleeSelect
3	Subjective
4	FillInBlanks
\.


--
-- TOC entry 2043 (class 0 OID 16590)
-- Dependencies: 184
-- Data for Name: sectionquestion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY sectionquestion (questionid, sectionid) FROM stdin;
1	1
2	1
3	1
4	1
5	1
12	18
13	18
14	18
15	18
15	19
14	19
13	19
12	19
2	20
3	20
4	20
10	20
11	20
13	21
15	21
14	21
11	21
12	21
4	22
2	22
3	22
5	22
1	22
10	22
15	23
12	23
11	23
14	23
13	23
5	24
4	24
3	24
2	24
1	24
14	25
15	25
11	26
10	26
14	27
11	27
13	27
10	27
4	28
1	28
2	28
3	28
\.


--
-- TOC entry 1897 (class 2606 OID 16494)
-- Name: Pk_UserProfile; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ilsuserprofile
    ADD CONSTRAINT "Pk_UserProfile" PRIMARY KEY (id);


--
-- TOC entry 1917 (class 2606 OID 16589)
-- Name: pk_answersheet; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY answersheet
    ADD CONSTRAINT pk_answersheet PRIMARY KEY (id);


--
-- TOC entry 1905 (class 2606 OID 16526)
-- Name: pk_exam; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ilsexam
    ADD CONSTRAINT pk_exam PRIMARY KEY (id);


--
-- TOC entry 1909 (class 2606 OID 16547)
-- Name: pk_examexecution; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY examexecution
    ADD CONSTRAINT pk_examexecution PRIMARY KEY (id);


--
-- TOC entry 1907 (class 2606 OID 16539)
-- Name: pk_examssignment; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY examassignment
    ADD CONSTRAINT pk_examssignment PRIMARY KEY (id);


--
-- TOC entry 1921 (class 2606 OID 16646)
-- Name: pk_examtype; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY examtype
    ADD CONSTRAINT pk_examtype PRIMARY KEY (id);


--
-- TOC entry 1893 (class 2606 OID 16454)
-- Name: pk_idgen; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY idgen
    ADD CONSTRAINT pk_idgen PRIMARY KEY (genname);


--
-- TOC entry 1899 (class 2606 OID 16502)
-- Name: pk_level; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ilslevel
    ADD CONSTRAINT pk_level PRIMARY KEY (id);


--
-- TOC entry 1913 (class 2606 OID 16563)
-- Name: pk_qpsections; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY qpsection
    ADD CONSTRAINT pk_qpsections PRIMARY KEY (id);


--
-- TOC entry 1915 (class 2606 OID 16581)
-- Name: pk_question; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY question
    ADD CONSTRAINT pk_question PRIMARY KEY (id);


--
-- TOC entry 1911 (class 2606 OID 16555)
-- Name: pk_questionpaper; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questionpaper
    ADD CONSTRAINT pk_questionpaper PRIMARY KEY (id);


--
-- TOC entry 1919 (class 2606 OID 16602)
-- Name: pk_questiontypemaster; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY questiontypemaster
    ADD CONSTRAINT pk_questiontypemaster PRIMARY KEY (id);


--
-- TOC entry 1901 (class 2606 OID 16510)
-- Name: pk_subject; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ilssubject
    ADD CONSTRAINT pk_subject PRIMARY KEY (id);


--
-- TOC entry 1903 (class 2606 OID 16518)
-- Name: pk_topic; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ilstopic
    ADD CONSTRAINT pk_topic PRIMARY KEY (id);


--
-- TOC entry 1891 (class 2606 OID 16449)
-- Name: pk_user; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ilsuser
    ADD CONSTRAINT pk_user PRIMARY KEY (id);


--
-- TOC entry 1895 (class 2606 OID 16462)
-- Name: role_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ilsrole
    ADD CONSTRAINT role_pk PRIMARY KEY (id);


--
-- TOC entry 2052 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- TOC entry 2055 (class 0 OID 0)
-- Dependencies: 171
-- Name: ilsrole; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE ilsrole FROM PUBLIC;
REVOKE ALL ON TABLE ilsrole FROM postgres;
GRANT ALL ON TABLE ilsrole TO postgres;
GRANT ALL ON TABLE ilsrole TO PUBLIC;


--
-- TOC entry 2056 (class 0 OID 0)
-- Dependencies: 169
-- Name: ilsuser; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE ilsuser FROM PUBLIC;
REVOKE ALL ON TABLE ilsuser FROM postgres;
GRANT ALL ON TABLE ilsuser TO postgres;
GRANT ALL ON TABLE ilsuser TO PUBLIC;


-- Completed on 2017-05-14 17:06:08

--
-- PostgreSQL database dump complete
--

