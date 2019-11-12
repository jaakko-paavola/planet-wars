# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on avaruuslentelypeli, jossa pelaaja voi vallata planeettoja sekä taistella muita avaruusaluksia vastaan.

## Käyttöliittymäluonnos

Pelinäkymä olisi parhaimmillaan (eli jatkokehitysideat mukaan lukien) jotain tämän suuntaista:
<img src="https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/Pelinakyma.jpg">
Oleelliset osat pelinäkymää ovat päänäkymä (jonka keskellä pelaaja on aina itse), pieni karttanäkymä jossain yläkulmassa, sekä paneeli tai paneelit, joissa näkyy pelaajalle erinäistä tilannekuvaa ja hänen pelihahmonsa tietoja.

## Perusversion tarjoama toiminnallisuus
- Pelinäkymä, pelikenttä ja lentofysiikka.
- Toimiva lentonopeuden mittari.
- Yläkulman karttanäkymä.
- Planeettojen kiertoradalle asettuminen, mikä avaa jonkinlaisen planeettanäkymän, jossa tietoa planeetasta (ei kuvattuna käyttöliittymäluonnoksessa).
- Kiertoradalle asettumisen yhteydessä planeetan merkitseminen vallatuksi päänäkymään ja karttanäkymään, sekä pisteiden kertyminen (pisteitä ei käyttöliittymäluonnoksessa kuvattuna).
- Pisteiden kertyminen nostaa pelaajatasoa tietyin välein.
- Kirjautuminen käyttäjätunnuksella ja salasanalla, jotta pelaaja voi seuraavalla pelikerralla jatkaa peliä sillä pelaajatasolla, jonka hän on aiemmin pelatessaan saavuttanut.
- Vain yksinpeli.

## Jatkokehitysideoita

Perusversion jälkeen peliä täydennetään ajan salliessa esim. seuraavilla toiminnallisuuksilla
- Torpedon ampuminen, mikä kuluttaa energiaa.
- "Tyhmät" konepelaajat, jotka esim. lentävät sattumanvaraisesti. 
- Tuki kahdelle ihmispelaajalle. 
- Torpedolla osuminen pelaajaan kuluttaa tämän "elämäpisteitä".
- Kun pelaajan elämäpisteet ehtyvät, pelaaja tuhoutuu, mistä "tappopisteen" saa viimeisenä tähän osunut pelaaja.
- Aloitusvalikko, jossa pelin ja pelikentän asetuksia, kuten ihmispelaajien ja konepelaajien määrän voi asettaa sekä pelikartan generoida haluamillaan asetuksilla.
