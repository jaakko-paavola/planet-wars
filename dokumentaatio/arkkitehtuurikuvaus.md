# Arkkitehtuurikuvaus

## Rakenne
Pääohjelma on luokassa PlanetWarsApplication, joka luo graafiset elementit, pelikentän ja sen objektit sekä huolehtii niiden animoimisesta. Animointi tapahtuu luokassa Animation. Pelikenttä luodaan luokalla GameArena ja peli luokalla Game. Graafiset objektit periytyvät luokasta Shape. Graafisia objekteja ovat kaikki sellaiset asiat, jotka liikkuvat käyttöliittymässä: alukset (luokka Ship), planeetat (luokka Planet), torpedot (luokka Torpedo) sekä karttanäkymän sijainnin osoittaja (luokka MapLocator). Shape-luokassa ja graafisten objektien luokissa on pitkälti sisäänrakennettuna ohjelman sovelluslogiikka.

Sovelluksen luokkakaavio on seuraava:

<img src="https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/class_diagram.jpg" width="160">

## Käyttöliittymä


## Sovelluslogiikka


## Tietojen pysyväistallennus


### Tiedostot


### Päätoiminnallisuudet
#### Käyttäjän kirjautuminen, pelin jatkaminen käyttäjän tasolta sekä tallennus

<img src="https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavio.jpg" width="160">

#### Muut toiminnallisuudet

## Ohjelman rakenteeseen jääneet heikkoudet
