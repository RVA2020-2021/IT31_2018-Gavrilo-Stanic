
--Test Data
--Fakultet
INSERT INTO "fakultet"("id","naziv","sediste")
VALUES(nextval('fakultet_seq'),'Fakultet tehnickih nauka', 'Novi Sad');

INSERT INTO "fakultet"("id","naziv","sediste")
VALUES(nextval('fakultet_seq'),'ETF', 'Beograd');

INSERT INTO "fakultet"("id","naziv","sediste")
VALUES(nextval('fakultet_seq'),'PMF', 'Novi Sad');

INSERT INTO "fakultet"("id","naziv","sediste")
VALUES(nextval('fakultet_seq'),'Poljoprivredni', 'Novi Sad');

INSERT INTO "fakultet"("id","naziv","sediste")
VALUES(nextval('fakultet_seq'),'Filozovski fakultet', 'Novi Sad');

INSERT INTO "fakultet"("id","naziv","sediste")
VALUES(nextval('fakultet_seq'),'Pravni fakultet', 'Novi Sad');


INSERT INTO "fakultet"("id","naziv","sediste")
VALUES(-100,'Test fakultet', 'Test Grad');




--statusi

INSERT INTO "status"("id","naziv","oznaka")
VALUES(nextval('status_seq'),'Budzet','B');
INSERT INTO "status"("id","naziv","oznaka")
VALUES(nextval('status_seq'),'Samofinansiranje','S');
INSERT INTO "status"("id","naziv","oznaka")
VALUES(nextval('status_seq'),'Pauziran','P');
INSERT INTO "status"("id","naziv","oznaka")
VALUES(nextval('status_seq'),'Apsolvent','A');

INSERT INTO "status"("id","naziv","oznaka")
VALUES(-100,'Test Status','Test');

--departmani

INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Departman za elektroniku, energetiku i telekomunikaciju','EET',1);
INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Departman za industrijkso inzenjerstvo i menadzment','IIM',1);
INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Departman za racunarstvo i automatiku','RA',1);

INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Departman za fiziku','F',3);
INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Departman za biologiju i ekologiju','BE',3);
INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Departman za matematiku i informatiku','MI',3);

INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Departman za ratarstvo i povrtarstvo','RP',4);
INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Departman za stocarstvo','S',4);
INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Departman za veterinarski medicinu','VM',4);

INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Odsek za anglistiku','A',5);
INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Odsek za romanistiku','R',5);

INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Katedra za istorijiju drzave i prava','IDP',6);
INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Katedra za krivicno pravo','KP',6);

INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Katedra za elektroniku','E',2);
INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(nextval('departman_seq'),'Katedra za primenjenu matematiku','PM',2);

INSERT INTO "departman"("id","naziv","oznaka","fakultet")
VALUES(-100,'Test Departman','Test',-100);

--studenti

INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Marko','Markovic','it23-2018',1,2);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Marko','Markov','ff23-2018',2,3);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Marko','Stefanovic','ff24-2018',1,3);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Filip','Markovic','ss23-2015',4,1);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Todor','Markovic','ss1-2016',4,1);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Maksim','Markovic','ss100-2015',4,1);

INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Marko','Mark','sa44-2019',2,4);

INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Maks','Markovic','ss101-2015',4,5);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Maksim','Staka','ss16-2015',2,5);

INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Stefan','Filipovic','mm23-2019',1,6);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Sasa','Filipovic','mm25-2019',1,6);

INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Filip','Filipovic','vv23-2019',3,7);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Stefan','Stanic','ma23-2016',2,8);

INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Milica','Radic','aa2-2019',1,10);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Aleksa','Radic','ab2-2019',1,10);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Marija','Filipovic','cs56-2019',2,11);

INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Jelena','Rodic','mr-2017',1,12);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Marina','Lalic','lk3-2017',3,13);    

INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Radovan','Malic','ll76-2019',1,14);
INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(nextval('student_seq'),'Emilija','Todorovic','ut23-2016',4,15);

INSERT INTO "student"("id","ime","prezime","broj_indeksa","status","departman")
values(-100,'TestIme','TestPrz','TE100-100',-100,-100);