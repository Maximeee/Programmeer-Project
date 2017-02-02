# Report
Maxime Weekhout
10669744

### Overview
Deze app is bedoeld om recepten te vinden aan de hand van de verschillende ingredienten die je op dat moment al in huis hebt. Zo kan je dus toch heerlijke gerechten maken zonder dat je de deur uit moet voor boodschappen. Ook kunnen er recepten worden gezocht aan de hand van trefwoorden, zoals een normale receptenzoeker. Al deze recepten hebben een eigen gedetailieerde pagina waar de ingredienten en de instructies staan. De recepten kan je ook opslaan in een favorieten lijst.

![Flow Schema](https://github.com/Maximeee/Programmeer-Project/blob/master/doc/HomeScreen.png)

Zodra de app gestart wordt, worden de gebruikers gevraagd om in te loggen/een account te maken voor Firebase. Hier heb ik voor gekozen omdat gebruikers ook recepten kunnen opslaan in een favorieten lijst en zodra ze dus op een andere locatie inloggen ze ook toegang hebben tot deze lijst. Dit is de voornaamste reden waarom in voor een firebase database en authorisatie heb gekozen in plaats van voor opslaan door middel van shared preferences. Ook had ik wel het inloggen met firebase al een keer eerder gedan, maar nog nooit met de database gewerkt, en wel met shared preferences. Hierdoor heb ik zelf ook veel geleerd voor een eventuele volgende app.

Voor de informatie in de app gebruik ik de Food API van Spoonacular. Deze API maakt het mogelijk om te zoeken aan de hand van producten, maar ook om recepten te zoeken en de ingevulde tekst aan te vullen met suggesties. Elk recept heeft zijn eigen unieke ID wat het mogelijk maakt om de instructies en ingredientenlijst er bij te zoeken.
Na het inloggen komt de gebruiker op het homescherm van de app terecht, waar deze kan kiezen hoe die de recepten wilt zoeken. De knop met het huisje staat voor thuisblijven en hier kan de gebruiker in de balk zijn producten invoeren. Zodra er op de zoekknop wordt geklikt, komt er een lijst met recepten in beeld die gesorteerd staan op waar je de meeste producten voor in huis hebt.
Zodra de gebruiker op 1 van de recepten klikt, krijg je een pagina te zien met de naam, de ingredienten en de instructies voor dat specifieke recept. Veel recepten krijgen eigen instructies mee vanuit de API en deze worden ook laten zien, maar er zijn ook recepten die alleen een link naar de instructies bevatten. Als die het geval is, wordt de link getoond en wordt je doorgelinkt naar de internetpagina waar het recept te vinden is.
De gebruiker kan er ook voor kiezen om recepten te zoeken aan de hand van trefwoorden, die in de titel van het recept moeten zitten. Dan komt er een lijst in beeld van maximaal 100 recepten. Ook hier kan ook gelikt worden en komt dezelfde gedetailleerde pagina in beeld.
Vanaf die detailpagina kan worden gekozen om het recept op te slaan. Deze komt dan in de firebase database te staan onder de UserID en zodra er vanuit home op de favorietenknop wordt geklikt, zal er een lijst met favorieten in beeld komen.

![Flow Schema](https://github.com/Maximeee/Programmeer-Project/blob/master/doc/FlowSchema)

EmailPasswordActivity: Deze activity krijgt elke nieuwe en uitgelogde gebruiker standaard te zien. Hier kunnen zij inloggen met Firebase of een account aanmaken. De eisen voor het account aanmaken is dat het wachtwoord 6 tekens moet zijn minimaal. 

GroceryActivity: Dit is het homescherm. Hier zijn 4 knoppen te zien, die van boven naar beneden doorlinken naar: RecipeProductActivity, RecipeFinderActivity, FavoriteActivity, en een signout knop waarbij je uitgelogd de EmailPasswordActivity te zien krijgt. Hier zit verder nog geen functionaliteit die te maken heeft met het zoeken van recepten.

RecipeProductActivity: In deze activity is een EditText te zien waar gebruikers een lijst van ingredienten kunnen invullen gescheiden door een komma. Zodra op zoeken geklikt wordt, wordt deze lijst omgezet naar een String en meegestuurd naar de RecipeActivity. Deze activity wordt vervolgens gestart.

RecipeActivity: In deze activity is een lijst te zien met de recepten die de API gevonden heeft op basis van de meegestuurde query. Deze recepten worden door middel van de RecipeAdapter in de lijst gezet. 

RecipeAdapter:

DetailActivity: In deze activity staat de naam, de ingredienten en de instructies van het specifieke aangeklikte recept. Dit wordt door middel van API requests opgehaald en laten zien in de app. Onderin in het scherm zit een Save button. Zodra hierop wordt geklikt wordt de ID van het recept opgeslagen onder de UserID. Als de huidige user nog geen opgeslagen recepten heeft, wordt de database entry voor die specifieke gebruiker aangemaakt. De opgeslagen recepten worden weergegeven in de FavoriteActivity.

RecipeFinderActivity: In de EditText kan gezocht worden op de naam van een recept. De resultaten hiervan worden automatisch aangevuld. Hier worden maximaal 100 recepten weergegeven. Op het moment dat er een recept aangeklikt wordt, kom je op de detailpagina. 

FavoriteActivity: Hier wordt de database uitgelezen, en met de ID's wordt de naam van het recept opgehaald door middel van de API. Deze lijst wordt vervolgens in een listview gezet. Op het moment dat er op een recept geklikt wordt, kom je op de detailpagina van het recept.

Wat voor mij vooral veel uitzoek werk was, was het werken met JSON objecten en arrays. Dit was toch al een langere tijd geleden. Vooral de API responses voor de instructies heb ik wat moeite mee gehad omdat dat een array in een object in een array was, iets wat ik nog nooit zo gezien had. Uiteindelijk na veel uitzoekwerk en wat hulp is het toch gelukt om de goede informatie op de juiste plek te zetten.

In de RecipeActivity had ik eerst alleen de namen van de recepten weergegeven in de listview, ik was toen nog niet met de detailpagina bezig, en toen ik dus de detailactivity ging maken kwam ik er dus achter dat het weergeven van alleen de naam niet praktisch is. Ik kon wel zoeken op naam, maar er stond natuurlijk een kans dat ik dan niet het juiste recept kreeg. Ik heb toen een eigen Adapter geschreven zodat ik niet alleen de naam van het recept maar ook de ID van het recept opsla in de Listview, maar alleen de naam laat zien. Dit staat als JSONObject in de listview, en op het moment dat er dus op geklikt wordt, wordt niet de naam maar de ID meegestuurd naar de DetailActivity.

Vooral de detailActivity was een hoop werk om de juiste informatie binnen te krijgen. De API heeft veel verschillende functies, dus je kan heel veel verschillende requests doen. Een groot nadeel is dat niet alle informatie in 1 request te vinden is. Zo haal ik de titel, de ingredienten en de instructies op met 3 verschillende requests. Hierdoor is het laden van de activity wel langzamer dan ik zou willen. Het probleem hier was, is dat ik een verschil moest maken tussen de 3 verschillende requests, omdat de responses natuurlijk verschillend zijn. Ik heb toen gekozen om in de API request een voor mij identificerende string mee te sturen, waardoor ik 3 verschillende functies kon schrijven, die dus de 3 responses behandeld. 

Ik heb er voor gekozen om de boodschappenlijst functie achterwegen te laten wegens tijdgebrek. Als ik meer tijd had gehad, had dat er zeker in gezeten. Ook had ik dan een soort checklist systeem in de RecipeFinderActivity willen maken, zodat je kan filteren op keuken, allergieen enz. Een laatste ding wat ik echt zo verbeteren, is het design van de app. Het design is nu redelijk simpel, en aangezien ik niet heel creatief ben is er daar veel ruimte voor verbetering. 
