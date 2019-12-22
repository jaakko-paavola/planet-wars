# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on avaruuslentelypeli, jossa pelaaja valtaa ja tuhoaa planeettoja sekuntikelloa vastaan.

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
- Planeettojen valtaaminen lentämällä niitä päin ja niiden merkintä vallatuksi päänäkymään ja karttanäkymään. [Tehty]
- Planeettojen tuhoaminen ampumalla niitä torpedolla sekä niiden merkintä tuhotuksi päänäkymään ja karttanäkymään. [Tehty]
- Pisteiden kertyminen planeettojen valtaamisesta ja tuhoamisesta (pisteitä ei kuvattuna käyttöliittymäluonnoksessa). [Tehty]
- Läpäistystä kentästä kertyy pisteitä sitä enemmän, mitä enemmän sekuntikellossa oli aikaa jäljellä. [Tehty]
- Lentäminen ulos pelialueelta päättää pelin. [Tehty]
- Ajan loppuminen päättää pelin. [Tehty]
- Kaikkien planeettojen valtaaminen tai tuhoaminen päättää pelin. [Tehty]
- Kentän satunnaisgenerointi: a) kun pelikentän kaikki planeetat tulivat pelaajan valtaamiksi tai tuhoamiksi, jolloin generoidaan uusi kenttä yhtä askelta vaikeammalla vaikeustasolla; b) kun pelaaja epäonnistuu pelikentässä (eli lentää yli kentän rajojen tai aika loppuu), jolloin generoidaan uusi kenttä samalla vaikeustasolla. [Tehty]
- Pelaaja kohoaa arvoasteikolla (rank) ylöspäin saavuttamiensa pisteiden perusteella. [Tehty]
- Kirjautuminen käyttäjätunnuksella ja salasanalla, jotta pelaaja voi seuraavalla pelikerralla jatkaa peliä sillä pelaajatasolla, jonka hän on aiemmin pelatessaan saavuttanut. [Tehty]

## Jatkokehitysideoita

- "Tyhmät" konepelaajat, jotka esim. lentävät ja ampuvat sattumanvaraisesti ja osuessaan pelaajaan päättävät pelin; konepelaajan tuhoaminen torpedolla tuo lisäpisteitä.
- Arvoasteikko vaikuttaa pelaajan aluksen nopeuteen sekä torpedon ampumisnopeuteen.
