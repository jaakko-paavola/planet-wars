# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on avaruuslentelypeli, jossa pelaaja voi vallata planeettoja sekä taistella muita avaruusaluksia vastaan.

## Käyttöliittymäluonnos

Pelinäkymä olisi parhaimmillaan (eli jatkokehitysideat mukaan lukien) jotain tämän suuntaista:
<img src="https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/Pelinakyma.jpg">
Oleelliset osat pelinäkymää ovat päänäkymä (jonka keskellä pelaaja on aina itse), pieni karttanäkymä jossain yläkulmassa, sekä paneeli tai paneelit, joissa näkyy pelaajalle erinäistä tilannekuvaa ja hänen pelihahmonsa tietoja.

## Perusversion tarjoama toiminnallisuus
- Pelinäkymä, pelikenttä ja lentofysiikka. [Tehty]
- Pelikentän rajojen ulkopuolelle lentäminen päättää pelin. [Tehty]
- Toimiva lentonopeuden mittari. [Tehty]
- Yläkulman karttanäkymä. [Tehty]
- Torpedon ampuminen. [Tehty]
- Planeettojen valtaaminen lentämällä niitä päin ja niiden merkintä vallatuksi päänäkymään ja karttanäkymään, sekä pisteiden kertyminen (ei kuvattuna käyttöliittymäluonnoksessa). [Tehty]
- Planeettojen tuhoaminen ampumalla niitä torpedolla sekä niiden merkintä tuhotuksi päänäkymään ja karttanäkymään. [Tehty]
- Pisteiden kertyminen planeettojen valtaamisesta ja tuhoamisesta. [Tehty]
- Kirjautuminen käyttäjätunnuksella ja salasanalla, jotta pelaaja voi seuraavalla pelikerralla jatkaa peliä sillä pelaajatasolla, jonka hän on aiemmin pelatessaan saavuttanut. [Tehty]
- Kentän satunnaisgenerointi uuden pelin alussa ja kun edellisessä pelikentässä kaikki planeetat tuli pelaajan valtaamiksi tai tuhoamiksi + kenttien vaikeustason kasvu. [Tehty]
- Vain yksinpeli: pelaaja koittaa valloittaa tai tuhota kaikki planeetat sekuntikelloa vastaan. [Tehty]

## Jatkokehitysideoita

Perusversion jälkeen peliä täydennetään ajan salliessa esim. seuraavilla toiminnallisuuksilla
- "Tyhmät" konepelaajat, jotka esim. lentävät sattumanvaraisesti ja osuessaan pelaajaan päättävät pelin; konepelaajan tuhoaminen torpedolla tuo pisteitä.
- Tuki kahdelle ihmispelaajalle, jolloin pelimoodi olisi erilainen, esim.: torpedolla osuminen ihmispelaajaan kuluttaa tämän "elämäpisteitä"; kun pelaajan elämäpisteet ehtyvät, pelaaja tuhoutuu, mistä "tappopisteen" saa viimeisenä tähän osunut pelaaja; välitön "uudelleensyntyminen" sattumanvaraiseen sijaintiin pelikentällä.
- Aloitusvalikko, jossa pelin ja pelikentän asetuksia, kuten ihmispelaajien ja konepelaajien määrän voi asettaa sekä pelikartan generoida haluamillaan asetuksilla.
