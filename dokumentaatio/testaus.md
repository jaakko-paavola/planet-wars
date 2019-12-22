# Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla sekä manuaalisesti tehdyin järjestelmätason testein.

## Yksikkö- ja integraatiotestaus


### Sovelluslogiikka

Integraatiotestien ytimen muodostavat sovelluslogiikkaa, eli pakkauksen planetwars.logic luokkia testaavat integraatiotestiluokat LogicLayerTest ja GameEngineTest.

Integraatiotestit käyttävät datan pysyväistallennukseen DAO-rajapintojen keskusmuistitoteutusta PlayerDaoTest).

Yksikkötestausta suoritettiin mm. graafisten objektien testaamisessa sekä testiluokissa GameArenaTest ja GamePlayTest.

### DAO-luokat

DAO-luokan toiminnallisuus on testattu luomalla testeissä testaustiedosto planetwarsTest.db.

### Testauskattavuus

Käyttöliittymäkerrosta lukuunottamatta sovelluksen testauksen rivikattavuus on 75% ja haarautumakattavuus 46%

<img src="https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/Jacoco%20report.png" width="800">


## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

### Asennus ja konfigurointi

Sovellusta on testattu sekä tilanteissa, joissa käyttäjät tallettavat tiedosto (planetwars.db) on ollut olemassa ja joissa sitä ei ole ollut jolloin ohjelma on luonut ne itse.

### Toiminnallisuudet

Kaikki määrittelydokumentin ja käyttöohjeen listaamat toiminnallisuudet on käyty läpi.

## Sovellukseen jääneet laatuongelmat

Sovellus ei anna tällä hetkellä järkeviä virheilmoituksia, seuraavissa tilanteissa
- salasana tai käyttäjätunnus ovat tyhjiä
- salasana tai käyttäjätunnus ovat liian lyhyitä
