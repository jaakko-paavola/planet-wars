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
- Toimiva lentonopeuden mittari. [Tehty osittain]
- Yläkulman karttanäkymä. [Tehty]
- Torpedon ampuminen. [Tehty]
- Planeettojen kiertoradalle asettuminen, mikä avaa jonkinlaisen planeettanäkymän, jossa tietoa planeetasta (ei kuvattuna käyttöliittymäluonnoksessa).
- Kiertoradalle asettumisen yhteydessä planeetan merkitseminen vallatuksi päänäkymään ja karttanäkymään, sekä pisteiden kertyminen (ei kuvattuna käyttöliittymäluonnoksessa).
- Pisteiden kertyminen nostaa pelaajatasoa tietyin välein. Pelaajataso vaikuttaa aluksen kiihtymisnopeuteen.
- Kirjautuminen käyttäjätunnuksella ja salasanalla, jotta pelaaja voi seuraavalla pelikerralla jatkaa peliä sillä pelaajatasolla, jonka hän on aiemmin pelatessaan saavuttanut.
- Kartan satunnaisgenerointi aina uuden pelin alussa ja kun edellisessä pelikentässä kaikki planeetat tuli pelaajan valtaamiksi.
- Vain yksinpeli: pelaaja koittaa valloittaa kaikki planeetat sekuntikelloa vastaan.

## Jatkokehitysideoita

Perusversion jälkeen peliä täydennetään ajan salliessa esim. seuraavilla toiminnallisuuksilla
- "Tyhmät" konepelaajat, jotka esim. lentävät sattumanvaraisesti ja osuessaan pelaajaan päättävät pelin; konepelaajan tuhoaminen torpedolla tuo pisteitä.
- Tuki kahdelle ihmispelaajalle, jolloin pelimoodi olisi erilainen, esim.: torpedolla osuminen ihmispelaajaan kuluttaa tämän "elämäpisteitä"; kun pelaajan elämäpisteet ehtyvät, pelaaja tuhoutuu, mistä "tappopisteen" saa viimeisenä tähän osunut pelaaja; välitön "uudelleensyntyminen" sattumanvaraiseen sijaintiin pelikentällä.
- Aloitusvalikko, jossa pelin ja pelikentän asetuksia, kuten ihmispelaajien ja konepelaajien määrän voi asettaa sekä pelikartan generoida haluamillaan asetuksilla.
