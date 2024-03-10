# Planet Wars
Avaruuslentelypeli, jossa vallataan planeettoja ja taistellaan muita avaruusaluksia vastaan.

## Helsingin yliopiston ohjelmistotekniikan kurssin harjoitustyö 2019
A course project in the course Software Development Methods at the University of Helsinki in 2019

### Harjoitustyön dokumentaatio

[Käyttöohje](https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Määrittelydokumentti](https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuurikuvaus.md)

[Testausdokumentti](https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/testaus.md)

### Releases
[Week 5](https://github.com/Jakoviz/ot-harjoitustyo/releases/tag/0.2)

[Week 6](https://github.com/Jakoviz/ot-harjoitustyo/releases/tag/0.8)

[Final release](https://github.com/Jakoviz/ot-harjoitustyo/releases/tag/1.0)

### Peliohje
Ohjaa avaruusalusta nuolinäppäimillä. Huomaa, että alaspäin-nuoli jarruttaa ja space ampuu torpedon.

### Komentorivitoiminnot

#### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

#### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/Jakoviz/ot-harjoitustyo/blob/master/PlanetWars/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_

#### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _PlanetWars-1.0-SNAPSHOT.jar_

#### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_
