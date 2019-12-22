# Arkkitehtuurikuvaus

## Rakenne
Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria, jossa käyttöliittymä, sovelluslogiikka ja pysyväistalletus on eriytetty toisistaan. Ainoastaan ylempi kerros voi kutsua alempaa, eli käyttöliittymäkerros sovelluslogiikkaa ja sovelluslogiikka pysyväistalletuskerrosta. Kapsuloinnin nimissä rakenteessa on myös pyritty siihen, että kerrosten välillä tietyt luokat toimivat rajapintoina, joten ylempi kerros ei näe noiden luokkien "ohi" kerroksen sisäistä toiminnallisuutta.

Sovelluksen luokka/-pakkauskaavio on seuraavanlainen:

<img src="https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/class_diagram_new.jpg" width="160">

## Käyttöliittymä
Käyttöliittymä sisältää kaksi erillistä näkymää
-Kirjautumisnäkymä
-Pelinäkymä

Nämä ovat toteutettu omina Scene-olioinaan, jotka ovat yksi kerrallaan näkyvänä. Lisäksi pelinäkymä luodaan uudestaan aina kun pelikenttä luodaan uudestaan.

Käyttöliittymäpakkaukseen planetwars.ui kuuluvat luokat PlanetWarsApplication, Animation, GameScene ja LoginScene. Pääohjelma on luokassa PlanetWarsApplication, joka kutsuu luokkaa LoginScene luomaan kirjautumisnäkymän, joka koostuu käyttäjänimen ja salasanan tekstikenttiinsä ottavasta kirjautumisikkunasta sekä luokkaa GameScene luomaan pelinäkymän, joka koostuu peli-ikkunasta, karttaikkunasta ja "mittaripaneeli-ikkunasta". Nämä palauttavat pääohjelmalle luomansa näkymät, ja pääohjelma laittaa näkymät näkyville. Luokka Animation huolehtii animoinnista, eli ikkunoissa tapahtuvista, jatkuvasti muuttuvista tulostuksista, mutta kutsuu kuitenkin aina sovelluslogiikkakerrosta saadakseen uudet arvot tulostuksia varten, eli kaikki sovelluslogiikka on eriytetty käyttöliittymästä.

## Sovelluslogiikka
Sovelluslogiikan rajapintoina käyttöliittymään päin toimivat luokat a) GameEngine, joka huolehtii liikenteestä pysyväistalletuskerrokseen päin ja takaisin sekä välittää pelaajaan ja tämän peliin liittyviä tietoja käyttöliittymäkerrokselle, sekä b) LogicLayer, joka laskee ja tarjoilee käyttöliittymäkerrokselle ja etenkin sen Animation-luokalle pelissä tapahtuvia, event-tyyppisiä jatkuvia tapahtumia. Näiden kahden luokan "takana" käyttöliittymästä päin katsottuna ovat luokat Player, joka pitää sisällään pelaajan eli käyttäjän oleelliset tiedot; GamePlay, joka sisältää kunkin hetkiseen peliin liittyviä staattisia (kuten peliasetuksia) ja muuttuvia (kuten pelissä kertyneet pisteet) tietoja, sekä GameArena, joka sisältää kaikki pelikentän graafiset objektit. Nämä viisi luokkaa kuuluvat sovelluslogiikkapakkauksen juuritasolle planetwars.logic.

Graafiset objektit periytyvät luokasta Shape. Graafisia objekteja ovat kaikki sellaiset asiat, jotka liikkuvat käyttöliittymässä: alukset (luokka Ship), planeetat (luokka Planet), torpedot (luokka Torpedo), karttanäkymän sijainnin osoittaja (luokka MapLocator) sekä pelikentän rajat (luokka BoundaryRectangle). Shape-luokassa ja graafisten objektien luokissa on pitkälti sisäänrakennettuna ohjelman sovelluslogiikka. Nämä kaikki luokat kuuluvat sovelluslogiikkapakkaukseen planetwars.logic.graphicobjects.

## Tietojen pysyväistallennus
Database-luokka luo yhteyden tietokantaan ja PlayerDao-luokka hoitaa pelaajan tietojen tallentamisen ja noutamisen tietokannasta. Nämä kaksi luokkaa kuuluvat tietokantapakkaukseen planetwars.database. PlayerDao noudattaa Data Access Object -suunnittelumallia ja se on tarvittaessa mahdollista korvata tai sitä voidaan täydentää uusilla toteutuksilla, jos sovelluksen datan talletustapaa päätetään vaihtaa.

### Tiedostot
Sovellus käyttää tietokantatiedostoa planetwars.db oikean pelidatan tallentamiseen sekä tietokantatiedostoa planetwarsTest.db testeissä tehtävien tiedokantaoperaatioiden datan tallentamiseen.

### Päätoiminnallisuudet
#### Käyttäjän kirjautuminen, pelin jatkaminen käyttäjän tasolta sekä tallennus
<img src="https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavio.jpg" width="160">

#### Muut toiminnallisuudet

## Ohjelman rakenteeseen jääneet heikkoudet
