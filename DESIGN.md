# Design document

De app gebruikt Firebase om gebruikers te laten in te loggen. Hiervoor zal email/password, facebook, 
en google authenticatie worden geimplementeerd als login methodes. De recepten die als favoriet worden opgeslagen
wil ik gaan opslaan in een SQL-lite database, zodat deze gekoppeld worden aan het gebruiker (Hier moet ik nog verder over nadenken):

Naam recept, id 

De api die ik wil gebruiken is de food api van spoonacular. Hiervoor heb ik academische toegang aangevraagd, maar 
dit moet nog goedgekeurd worden, anders moet ik kijken naar of een andere api, of dat ik zo min mogelijk aanvragen 
kan doen zodat deze api gratis gebruikt kan worden. 
Als ik de supermarktzoeker ga implementeren, zal ik hiervoor de google maps api gebruiken.

De classes:
Chooser class: Hier kiest de gebruiker de gewenste inlog manier, en wordt doorgestuurd naar het bijbehorende inlogscherm
EmailPassword class: Hier kan de gebruiker met gebruik van email en wachtwoord inloggen
Google class: Hier kan de gebruiker met google inloggen
Facebook class: Hier kan de gebruiker met facebook inloggen
Wel/geen boodschappen class: Hier kan de gebruiker kiezen of deze boodschappen wilt doen en gewoon een lekker recept wilt zoeken
							 of dat een gebruiker geen boodschappen wilt doen en dus aan de hand van beschikbare producten een recept kiezen
Receptenzoeker class: Hier kan de gebruiker zoeken naar recepten aan de hand van trefwoorden en eventueel nog andere eisen
Receptenzoeker 2 class: Hier kan de gebruiker producten invoeren zodat er recepten gevonden kunnen worden die de gebruiker vervolgens kan maken.
Detailpagina class: Hier komen alle details van de recepten te staan, dus ingredienten, bereidingwijze, voedingswaarde enz.
Favorieten class: Hier worden de opgeslagen recepten weer gegeven.
Boodschappenlijst class: Hier staat een overzicht van de recepten, als je doorklikt krijg je het specifieke bodschappenlijstje te zien
Boodschappenlijst 2 class: Hier staat de daadwerkelijke boodschappen lijst

