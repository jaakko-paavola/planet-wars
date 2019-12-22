# Arkkitehtuurikuvaus

## Rakenne
Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria, jossa käyttöliittymä, sovelluslogiikka ja pysyväistalletus on eriytetty toisistaan.

Sovelluksen luokka/-pakkauskaavio on seuraava:

<img src="https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/class_diagram.jpg" width="160">

## Käyttöliittymä
Käyttöliittymä sisältää kaksi erillistä näkymää
-Kirjautumisnäkymä
-Pelinäkymä

Nämä ovat toteutettu omina Scene-olioinaan, jotka ovat yksi kerrallaan näkyvänä. Lisäksi pelinäkymä luodaan uudestaan aina kun pelikenttä luodaan uudestaan.

Pääohjelma on luokassa PlanetWarsApplication, joka luo näkymät, eli kirjautumisnäkymän sekä pelinäkymän, joka koostuu peli-ikkunasta, karttaikkunasta ja "mittaripaneeli-ikkunasta". Luokka Animation huolehtii animoinnista, eli ikkunoissa tapahtuvista, jatkuvasti muuttuvista tulostuksista. Luokat PlanetWarsApplication ja Animation kuuluvat käyttöliittymäpakkaukseen planetwars.ui.

## Sovelluslogiikka
Sovelluslogiikka on sisäänrakennettuna graafisissa objekteissa, sekä pelikenttää ja peliä esittävissä objekteissa.

Graafiset objektit periytyvät luokasta Shape. Graafisia objekteja ovat kaikki sellaiset asiat, jotka liikkuvat käyttöliittymässä: alukset (luokka Ship), planeetat (luokka Planet), torpedot (luokka Torpedo), karttanäkymän sijainnin osoittaja (luokka MapLocator) sekä pelikentän rajat (luokka BoundaryRectangle). Shape-luokassa ja graafisten objektien luokissa on pitkälti sisäänrakennettuna ohjelman sovelluslogiikka. Nämä kaikki luokat kuuluvat sovelluslogiikkapakkaukseen planetwars.logic.graphicobjects.

Pelikenttä luodaan luokalla GameArena ja peli luokalla Game. GameArenan ilmentymä pitää sisällään planeettojen sijainnit, koot ja värit sekä pelikentän rajoja esittävän suorakulmion. Gamen ilmentymä pitää sisällään pelaajan aluksen, torpedot, jäljellä olevan peliajan sekä pelissä kertyneet pisteet. GameArena ja Game kuuluvat sovelluslogiikkapakkaukseen planetwars.logics.

## Tietojen pysyväistallennus

Player-luokka sisältää pelaajan relevantteja tietoja, kuten käyttäjänimeä ja salasanaa, sekä pelaajan saavuttamia asioita, kuten pisteitä ja tasoa. PlayerDao-luokka hoitaa pelaajan tietojen tallentamisen ja noutamisen tietokannasta. Database-luokka luo yhteyden tietokantaan. Nämä kolme luokkaa kuuluvat tietokantapakkaukseen planetwars.database.

Luokat noudattavat Data Access Object -suunnittelumallia ja ne on tarvittaessa mahdollista korvata uusilla toteutuksilla, jos sovelluksen datan talletustapaa päätetään vaihtaa.

### Tiedostot

Sovellus käyttää tietokantatiedosta planetwars.db.

### Päätoiminnallisuudet
#### Käyttäjän kirjautuminen, pelin jatkaminen käyttäjän tasolta sekä tallennus

<img src="https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavio.jpg" width="160">

#### Muut toiminnallisuudet

## Ohjelman rakenteeseen jääneet heikkoudet
