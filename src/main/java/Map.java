import org.json.JSONObject;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

public class Map {

    private Area[][] map;
    private Coordinate entryPoint;
    private int mapSize;

    Map () {
        initMapFile();
//        mapSize = 0;
//        entryPoint = new Coordinate(0 ,0); // maybe read from file? JSON?
    }

    private void initMapFile() {
        // read resource file and create Java JSON object
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream mapInputStream = null;
        mapInputStream = classloader.getResourceAsStream("map.json");
        Scanner mapInputScanner = new Scanner(mapInputStream);
        StringBuffer mapStringBuffer = new StringBuffer();
        while(mapInputScanner.hasNext()){
            mapStringBuffer.append(mapInputScanner.nextLine().trim());
        }
        String mapContents = mapStringBuffer.toString();
        JSONObject jsonObject = new JSONObject(mapContents);

        // read entryPoint
        entryPoint = readCoordinateFromString(jsonObject.get("entryPoint").toString());

        // read mapSize
        Scanner mapSizeScanner = new Scanner(jsonObject.get("mapSize").toString()).useDelimiter(",");
        mapSize = mapSizeScanner.nextInt();

        // read map
        readMapJson(jsonObject.getJSONObject("map"));
    }

    private void readMapJson (JSONObject mapJson) {
        // iterate over areas
        Iterator<String> areaIterator  =  mapJson.keys();
        map = new Area[mapSize][mapSize];
        try{
            while(areaIterator.hasNext()){
                String areaCoordinateString = areaIterator.next();
                Coordinate areaCoordinate = readCoordinateFromString(areaCoordinateString);
                JSONObject areaContent = new JSONObject(mapJson.get(areaCoordinateString).toString());
                map[areaCoordinate.x()][areaCoordinate.y()] = new Area(
                        areaContent.get("name").toString(),
                        areaContent.get("description").toString()
                );
            }
        }
        catch (Exception ex){
            System.out.println("Map reading error: " + ex);
        }
    }

    private Coordinate readCoordinateFromString (String input) {
        Scanner coordinateScanner = new Scanner(input).useDelimiter(",");
        return new Coordinate(coordinateScanner.nextInt(), coordinateScanner.nextInt());
    }

    //This simply makes a map to test movement.
    private void initDemoMap() {
        entryPoint = new Coordinate(2, 1);
        mapSize = 3;
        map = new Area[mapSize][mapSize];
//        |  N |    N      |  Eating Area      |
//        |  N | LOBBY     |  Cafeteria        |
//        |  N | Entrance  |        N          |
        map[2][1] = new Area("Entrance", "It is a quiet Friday morning and you find yourself directly infront of the " +
                "of the VU main building. For some reason, you can not leave this area, and are forced to move north of where you are now.");
        map[1][1] = new Area("Lobby", "Everything is quieter than usual in the Lobby. The lights are off and it is, thus, darker than usual. You do, however, see a light to your East.");
        map[1][2] = new Area("Cafeteria", "The cafeteria is, like the lobby, empty. You do, however, hear some noise... you just can't tell from where :O");
        map[0][2] = new Area("Eating area", "You found the noise! It is coming from some rats eating a rotten sandwich. Also worth noting that, if your made it here, it is the end of the DEMO map :p");
//       TODO:  Make the ITEM CLASS!!

//        map[0][2].addItem(new Item());
    }

    public String getDescription(Coordinate c) {
        int x = c.x();
        int y = c.y();
        String intro = "You are currently in the " + map[x][y].getName() + " \n";
        return intro + map[x][y].getDescription();
    }

    public Coordinate getEntryPoint() {return entryPoint;} //makes a copy since coordinate is immutable.

    public final boolean isValidMove(Coordinate c) {
        if (c.x() >= 0 && c.y() >= 0 && c.x() < mapSize && c.y() < mapSize) {
            return (map[c.x()][c.y()] != null);
        }
        return false;
    }

}
