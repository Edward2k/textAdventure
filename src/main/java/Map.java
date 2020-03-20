import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Map {

    private Area[][] map;
    private Coordinate entryPoint;
    private int mapSize;

    Map () {
        initMapFile();
        initItemsFile();
    }

    private void initMapFile() {
        // read resource file and create Java JSON object
        InputStream mapInputStream = getClass().getClassLoader().getResourceAsStream("map.json");
        assert mapInputStream != null;
        Scanner mapInputScanner = new Scanner(mapInputStream);
        StringBuilder mapStringBuilder = new StringBuilder();
        while(mapInputScanner.hasNext()){
            mapStringBuilder.append(mapInputScanner.nextLine().trim());
        }
        String mapContents = mapStringBuilder.toString();
        JSONObject jsonObject = new JSONObject(mapContents);
//
//        // read entryPoint
        entryPoint = readCoordinateFromString(jsonObject.get("entryPoint").toString());
//
//        // read mapSize
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

    private void initItemsFile() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream itemInputStream = null;
        itemInputStream = classloader.getResourceAsStream("items.json");
        Scanner itemInputScanner = new Scanner(itemInputStream);
        StringBuffer itemStringBuffer = new StringBuffer();
        while(itemInputScanner.hasNext()){
            itemStringBuffer.append(itemInputScanner.nextLine().trim());
        }

        String itemContents = itemStringBuffer.toString();
        JSONObject jsonObject = new JSONObject(itemContents);

        // read basic items
        readBasicItems(jsonObject.getJSONObject("basicItems"));

        // read containers
        readContainer(jsonObject.getJSONObject("containers"));

    }

    private List<BasicItem> readBasicItems(JSONObject basicItems) {
        List<BasicItem> items =  new ArrayList<BasicItem>();
        Iterator<String> itemIterator  =  basicItems.keys();
        try{
            while(itemIterator.hasNext()){
                String itemString = itemIterator.next(); //the ID
                JSONObject itemContent = new JSONObject(basicItems.get(itemString).toString()); //contains name and coordinate
                String itemAreaString = itemContent.get("coordinate").toString();
                String itemName = itemContent.get("name").toString();
                Coordinate itemArea = readCoordinateFromString(itemAreaString); //the coordinate of the ite
                BasicItem newItem = new BasicItem(itemName, Integer.parseInt(itemString));
                map[itemArea.x()][itemArea.y()].addItem(newItem);
                items.add(newItem);
            }
        }
        catch (Exception ex) {
            System.out.println("BasicItem reading error: " + ex);
        }
        return items;
    }

    private void readContainer(JSONObject containers) {
        Iterator<String> containerIterator  =  containers.keys();
        try{
            while(containerIterator.hasNext()){
                String containerString = containerIterator.next(); //the ID
                JSONObject containerContent = new JSONObject(containers.get(containerString).toString()); //contains name, coordinate, description and entities

                String containerAreaString = containerContent.get("coordinate").toString();
                String containerName = containerContent.get("name").toString(); // the name of the container
                String containerDescription = containerContent.get("description").toString();
                JSONObject containerEntities = new JSONObject(containerContent.get("entities").toString());
                List<BasicItem> basicItems = readBasicItems(containerEntities);

                Coordinate containerArea = readCoordinateFromString(containerAreaString); //the coordinate of the container

                map[containerArea.x()][containerArea.y()].addItem(new Container(containerName, Integer.parseInt(containerString), containerDescription, basicItems));
            }
        }
        catch (Exception ex) {
            System.out.println("BasicItem reading error: " + ex);
        }
    }

    //This simply makes a map to test movement.
    private void initDemoMap() {
        entryPoint = new Coordinate(2, 1);
        mapSize = 3;
        map = new Area[mapSize][mapSize];
//        |  N |    N      |  Eating Area      |
//        |  N | LOBBY     |  Cafeteria        |
//        |  N | Entrance  |        N          |

//       TODO:  Make the ITEM CLASS!!
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
