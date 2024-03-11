# Käyttöohje

Lataa tiedosto [PlanetWars-1.0.jar](https://github.com/jaakko-paavola/planet-wars/releases/tag/1.0)
## Konfigurointi

Ohjelma olettaa, että sqlite-tietokanta toimii käyttäjän ympäristössä. Ohjelma vaikuttaa toimivan ainoastaan Java 8/1.8:lla. Esim. Java 11:llä jar:n käynnistysyritys antaa virheilmoituksen: "Error: JavaFX runtime components are missing, and are required to run this application"

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

```
java -jar PlanetWars-1.0.jar
```

## Kirjautuminen

Sovellus käynnistyy kirjautumisnäkymään:

<img src="https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/Screenshot%20from%202019-12-10%2001-12-58.png" width="400">

Kirjautuminen onnistuu kirjoittamalla olemassaoleva käyttäjätunnus ja oikea salasana syötekenttään ja painamalla _sign in_.

Jos kirjautuminen onnistuu, avautuu seuraavaksi pelinäkymä.

## Uuden käyttäjän luominen

Uusi käyttäjä luodaan syöttämällä käyttäjänimi ja salasana syötekenttiin ja painamalla _sign up_

Jos käyttäjän luominen onnistuu, avautuu seuraavaksi pelinäkymä.

## Pelaaminen

Alusta ohjataan nuolinäppäimillä ja välilyönnillä ammutaan torpedo. Pelissä on tarkoitus lentää päin tai ampua jokaista pelikentän planeettaa ennen kuin oikeassa yläkulmassa juoksevasta sekuntikellosta loppuu aika. Jos pelaaja onnistuu läpäisemään kentän, hän etenee seuraavalle, astetta vaikeammalle tasolle.
