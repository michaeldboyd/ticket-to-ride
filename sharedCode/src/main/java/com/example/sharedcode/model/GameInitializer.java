package com.example.sharedcode.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali on 2/24/2018.
 */

public class GameInitializer {

    private static int DEST_DECK_NUM = 30;
    private static int TRAIN_CARD_DECK_NUM = 110;
    private static int NON_LOCOMOTIVE_CARD = 12;
    private static int LOCOMOTIVE_CARD = 14;

    public Game initializeGame(Game game){
        game.setDestinationDeck(initializeDestinationDeck());
        game.setTrainCardDeck(initializeTrainCardDeck());
        game.setFaceUpDeck(initializeFaceUpDeck());
        game.setCities(intitializeCities());
        game.setRoutesClaimed(initializeRoutes());
        return game;
    }

    private DestinationDeck initializeDestinationDeck(){
        DestinationDeck deck = new DestinationDeck();
        deck.add(new DestinationCard("Rosette","Fry Canyon",21));
        deck.add(new DestinationCard("Randlett","Gold Hill",8));
        deck.add(new DestinationCard("Agate", "Cedar City",8));
        deck.add(new DestinationCard("Fry Canyon", "Panguitch",	6));
        deck.add(new DestinationCard("Paradise", "Cedar City",17));
        deck.add(new DestinationCard("Randolph", "Mexican Hat",20	));
        deck.add(new DestinationCard("Randlett", "Garrison",10));
        deck.add(new DestinationCard("Ticaboo", "St. George	",10));
        deck.add(new DestinationCard("Paradise", "Gandy	",11));
        deck.add(new DestinationCard("Black Rock", "Fry Canyon",11));
        deck.add(new DestinationCard("Manila", "Salt Lake City",7	));
        deck.add(new DestinationCard("Manila", "Gandy",13	));
        deck.add(new DestinationCard("Rosette", "St. George	",20));
        deck.add(new DestinationCard("Rainbow", "Koosharem",11));
        deck.add(new DestinationCard("Aragonite", "Panguitch",17));
        deck.add(new DestinationCard("Provo", "Gold Hill",5));
        deck.add(new DestinationCard("Rosette", "Whipup",16));
        deck.add(new DestinationCard("Park City", "Rodham",11));
        deck.add(new DestinationCard("Whipup", "Dugway",9));
        deck.add(new DestinationCard("Randolph", "Whipup",13));
        deck.add(new DestinationCard("Moab", "St. George",12));
        deck.add(new DestinationCard("Whipup", "Shivwits",7));
        deck.add(new DestinationCard("Mexican Hat", "Panguitch",9));
        deck.add(new DestinationCard("Bonanza",	"Fry Canyon",22));
        deck.add(new DestinationCard("Park City", "Garrison",4));
        deck.add(new DestinationCard("Elmo", "Rosette",	8));
        deck.add(new DestinationCard("Rainbow", "Gold Hill",12));
        deck.add(new DestinationCard("Mexican Hat", "Shivwits",	13	));
        deck.add(new DestinationCard("Agate", "Enterprise",9));
        return deck;
    }

    private TrainCardDeck initializeTrainCardDeck(){
        TrainCardDeck deck = new TrainCardDeck();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < NON_LOCOMOTIVE_CARD; j++){
                deck.add(i);
            }
        }
        for(int i = 0; i < LOCOMOTIVE_CARD; i++){
            deck.add(TrainType.LOCOMOTIVE);
        }
        deck.shuffleDeck();
        return deck;
    }

    private Map<String, City> intitializeCities() {
        Map<String, City> cities = new HashMap<>();
        cities.put("Rosette", new City("Rosette", 41.81994, -113.41735));
        cities.put("Fry Canyon", new City("Fry Canyon", 37.63598, -110.15441));
        cities.put("Randlett", new City("Randlett", 40.23655, -109.81109));
        cities.put("Gold Hill", new City("Gold Hill", 40.17047, -113.82934));
        cities.put("Agate", new City("Agate", 39.01918, -109.24667));
        cities.put("Cedar City", new City("Cedar City", 37.67512, -113.07128));
        cities.put("Panguitch", new City("Panguitch", 37.82714, -112.43957));
        cities.put("Paradise", new City("Paradise", 41.56768, -111.83944));
        cities.put("Randolph", new City("Randolph", 41.65444, -111.18713));
        cities.put("Mexican Hat", new City("Mexican Hat", 37.15593, -109.86877));
        cities.put("Garrison", new City("Garrison", 38.93804, -114.03259));
        cities.put("Ticaboo", new City("Ticaboo", 37.67947, -110.69824));
        cities.put("St. George", new City("St. George", 37.099, -113.58215));
        cities.put("Gandy", new City("Gandy", 39.44467, -113.98315));
        cities.put("Manila", new City("Manila", 40.98611, -109.72457));
        cities.put("Salt Lake City", new City("Salt Lake City", 40.75974, -111.89575));
        cities.put("Rainbow", new City("Rainbow", 39.8465, -109.18762));
        cities.put("Koosharem", new City("Koosharem", 38.50949, -111.88476));
        cities.put("Aragonite", new City("Aragonite", 40.73477, -113.01635));
        cities.put("Provo", new City("Provo", 40.2376, -111.65405));
        cities.put("Park City", new City("Park City", 40.63896, -111.45355));
        cities.put("Rodham", new City("Rodham", 38.6855, -110.52246));
        cities.put("Whipup", new City("Whipup", 38.68979, -111.39587));
        cities.put("Dugway", new City("Dugway", 40.22502, -112.75268));
        cities.put("Moab", new City("Moab", 38.57393, -109.55566));
        cities.put("Bonanza", new City("Bonanza", 40.61395, -109.17663));
        cities.put("Elmo", new City("Elmo", 39.39057, -110.81634));
        cities.put("Enterprise", new City("Enterprise", 37.5707, -113.71948));
        cities.put("Black Rock", new City("Black Rock", 38.71551, -112.97241));
        cities.put("Shivwits", new City("Shivwits", 37.1811, -113.76205));
        cities.put("Kanab", new City("Kanab", 37.0464, -112.52746));
        cities.put("Fruita", new City("Fruita", 38.29424, -111.2503));
        cities.put("Escalante", new City("Escalante", 37.75985, -111.61285));
        cities.put("Tabiona", new City("Tabiona", 40.3591, -110.71472));
        cities.put("Lucin", new City("Lucin", 41.35619, -113.90899));
        cities.put("Scipio", new City("Scipio", 39.24501, -112.10998));
        return cities;
    }

    private Map<Route, Player> initializeRoutes(){
        Map<Route, Player> routes = new HashMap<>();
        routes.put(new Route("Rosette", "Lucin", 2, TrainType.BOX), null);
        routes.put(new Route("Rosette", "Aragonite", 2, TrainType.PASSENGER), null);
        routes.put(new Route("Rosette", "Salt Lake City", 6, TrainType.TANKER, true), null);
        routes.put(new Route("Salt Lake City", "Rosette", 6, TrainType.REEFER, true), null);
        routes.put(new Route("Rosette", "Paradise", 4, TrainType.FREIGHT), null);
        routes.put(new Route("Rosette", "Randolph", 6, TrainType.HOPPER), null);
        routes.put(new Route("Lucin", "Gold Hill", 5, TrainType.COAL), null);
        routes.put(new Route("Aragonite", "Gold Hill", 3, TrainType.CABOOSE), null);
        routes.put(new Route("Aragonite", "Dugway", 2, TrainType.BOX), null);
        routes.put(new Route("Aragonite", "Salt Lake City", 3, TrainType.PASSENGER), null);
        routes.put(new Route("Salt Lake City", "Dugway", 2, TrainType.TANKER), null);
        routes.put(new Route("Salt Lake City", "Provo", 2, TrainType.REEFER), null);
        routes.put(new Route("Salt Lake City", "Park City", 1, TrainType.FREIGHT), null);
        routes.put(new Route("Salt Lake City", "Paradise", 4, TrainType.HOPPER), null);
        routes.put(new Route("Paradise", "Randolph", 1, TrainType.COAL), null);
        routes.put(new Route("Randolph", "Tabiona", 5, TrainType.CABOOSE), null);
        routes.put(new Route("Randolph", "Manila", 5, TrainType.BOX), null);
        routes.put(new Route("Gold Hill", "Gandy", 3, TrainType.PASSENGER), null);
        routes.put(new Route("Gold Hill", "Black Rock", 6, TrainType.TANKER, true), null);
        routes.put(new Route("Black Rock", "Gold Hill", 6, TrainType.COAL, true), null);
        routes.put(new Route("Gold Hill", "Dugway", 3, TrainType.REEFER), null);
        routes.put(new Route("Dugway", "Black Rock", 6, TrainType.FREIGHT), null);
        routes.put(new Route("Dugway", "Scipio", 4, TrainType.HOPPER), null);
        routes.put(new Route("Dugway", "Provo", 2, TrainType.COAL), null);
        routes.put(new Route("Provo", "Scipio", 4, TrainType.CABOOSE), null);
        routes.put(new Route("Provo", "Elmo", 4, TrainType.BOX), null);
        routes.put(new Route("Provo", "Tabiona", 2, TrainType.PASSENGER), null);
        routes.put(new Route("Provo", "Park City", 1, TrainType.TANKER), null);
        routes.put(new Route("Park City", "Tabiona", 2, TrainType.REEFER), null);
        routes.put(new Route("Tabiona", "Randlett", 3, TrainType.FREIGHT), null);
        routes.put(new Route("Tabiona", "Manila", 4, TrainType.HOPPER), null);
        routes.put(new Route("Manila", "Randlett", 3, TrainType.COAL), null);
        routes.put(new Route("Manila", "Bonanza", 2, TrainType.CABOOSE), null);
        routes.put(new Route("Gandy", "Garrison", 2, TrainType.BOX), null);
        routes.put(new Route("Black Rock", "Garrison", 3, TrainType.PASSENGER), null);
        routes.put(new Route("Black Rock", "Cedar City", 5, TrainType.TANKER), null);
        routes.put(new Route("Black Rock", "Scipio", 4, TrainType.REEFER), null);
        routes.put(new Route("Scipio", "Black Rock", 4, TrainType.FREIGHT), null);
        routes.put(new Route("Scipio", "Cedar City", 6, TrainType.HOPPER), null);
        routes.put(new Route("Scipio", "Koosharem", 3, TrainType.COAL), null);
        routes.put(new Route("Scipio", "Whipup", 3, TrainType.CABOOSE), null);
        routes.put(new Route("Scipio", "Elmo", 3, TrainType.BOX), null);
        routes.put(new Route("Elmo", "Whipup", 3, TrainType.PASSENGER), null);
        routes.put(new Route("Elmo", "Agate", 5, TrainType.TANKER, true), null);
        routes.put(new Route("Agate", "Elmo", 5, TrainType.REEFER, true), null);
        routes.put(new Route("Elmo", "Randlett", 4, TrainType.FREIGHT), null);
        routes.put(new Route("Randlett", "Agate", 3, TrainType.HOPPER), null);
        routes.put(new Route("Randlett", "Rainbow", 5, TrainType.COAL), null);
        routes.put(new Route("Bonanza", "Rainbow", 3, TrainType.CABOOSE), null);
        routes.put(new Route("Garrison", "Enterprise", 6, TrainType.BOX), null);
        routes.put(new Route("Cedar City", "Enterprise", 2, TrainType.PASSENGER), null);
        routes.put(new Route("Cedar City", "St. George", 2, TrainType.TANKER), null);
        routes.put(new Route("Cedar City", "Kanab", 2, TrainType.REEFER), null);
        routes.put(new Route("Cedar City", "Panguitch", 2, TrainType.FREIGHT), null);
        routes.put(new Route("Koosharem", "Panguitch", 4, TrainType.HOPPER), null);
        routes.put(new Route("Koosharem", "Fruita", 2, TrainType.COAL), null);
        routes.put(new Route("Whipup", "Fruita", 1, TrainType.CABOOSE), null);
        routes.put(new Route("Whipup", "Rodham", 2, TrainType.BOX), null);
        routes.put(new Route("Agate", "Rodham", 4, TrainType.PASSENGER), null);
        routes.put(new Route("Agate", "Moab", 1, TrainType.TANKER), null);
        routes.put(new Route("Agate", "Rainbow", 3, TrainType.REEFER), null);
        routes.put(new Route("Enterprise", "Shivwits", 1, TrainType.FREIGHT), null);
        routes.put(new Route("St. George", "Shivwits", 1, TrainType.HOPPER), null);
        routes.put(new Route("St. George", "Kanab", 3, TrainType.COAL), null);
        routes.put(new Route("Kanab", "Mexican Hat", 6, TrainType.CABOOSE), null);
        routes.put(new Route("Kanab", "Escalante", 4, TrainType.BOX), null);
        routes.put(new Route("Panguitch", "Escalante", 2, TrainType.PASSENGER), null);
        routes.put(new Route("Fruita", "Escalante", 2, TrainType.TANKER), null);
        routes.put(new Route("Fruita", "Fry Canyon", 4, TrainType.REEFER), null);
        routes.put(new Route("Rodham", "Fry Canyon", 4, TrainType.FREIGHT), null);
        routes.put(new Route("Moab", "Fry Canyon", 4, TrainType.HOPPER), null);
        routes.put(new Route("Escalante", "Ticaboo", 2, TrainType.COAL), null);
        routes.put(new Route("Mexican Hat", "Ticaboo", 2, TrainType.CABOOSE), null);
        routes.put(new Route("Mexican Hat", "Fry Canyon", 2, TrainType.BOX), null);
        routes.put(new Route("Fry Canyon", "Ticaboo", 1, TrainType.PASSENGER), null);
        return routes;
    }

    private FaceUpDeck initializeFaceUpDeck(){
       FaceUpDeck deck = new FaceUpDeck();
       return deck;
    }

}
