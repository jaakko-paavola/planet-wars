# Helsingin yliopiston ohjelmistotekniikan kurssin harjoitustyö

## Planet Wars
Avaruuslentelypeli, jossa vallataan planeettoja ja taistellaan muita avaruusaluksia vastaan.

### Harjoitustyön dokumentaatio
[Määrittelydokumentti](https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuurikuvaus.md)

### Peliohje
Ohjaa avaruusalusta nuolinäppäimillä. Huomaa, että alaspäin-nuoli jarruttaa.

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
