import org.json.JSONObject;

import javax.print.attribute.standard.JobImpressionsCompleted;
import java.io.File;
import java.io.InputStream;
import java.util.*;

public class Map {

    private Area[][] map;
    private Coordinate entryPoint;
    private int mapSize;

    Map () {
        initMapFile();
    }

    /*
     * Interface
     */

    public String getDescription(Coordinate c) {
        int x = c.x();
        int y = c.y();
        String intro = "You are currently in the " + map[x][y].getName() + " \n";
        return intro + map[x][y].getDescription();
    }

    public Coordinate getEntryPoint() {return entryPoint;} //makes a copy since coordinate is immutable.

    public final boolean isValidMove(Coordinate c) {
        if (c.x() >= 0 && c.y() >= 0 && c.x() < mapSize && c.y() < mapSize) {
            return map[c.x()][c.y()] != null;
        }
        return false;
    }

    public final boolean hasObstacles(Coordinate c) { return !map[c.x()][c.y()].canEnter(); }

    public Area getArea(Coordinate c) { return map[c.x()][c.y()]; }

    /*
    * Reading JSON
     */

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

//        read entryPoint
        entryPoint = readCoordinateFromString(jsonObject.get("entryPoint").toString());

//        read mapSize
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

                if(areaContent.has("obstacle")) {
                    JSONObject obstacle = areaContent.getJSONObject("obstacle");
                    map[areaCoordinate.x()][areaCoordinate.y()].setObstacle(new Obstacle(obstacle.get("name").toString(), obstacle.get("description").toString(), obstacle.get("toNeutralize").toString()));
                }

                JSONObject areaItems = new JSONObject(areaContent.get("items").toString());
                addAreaItems(map[areaCoordinate.x()][areaCoordinate.y()], areaItems);
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

    private void addAreaItems(Area area, JSONObject items) {
        JSONObject basicItems = items.getJSONObject("basicItems");
        JSONObject containers = items.getJSONObject("containers");

        List<BasicItem> itemList = addBasicItems(basicItems, area);
        for(Item item : itemList) {
            area.addItem(item);
        }
        addContainers(containers, area);
    }

    private List<BasicItem> addBasicItems(JSONObject basicItems, Area area) {
        List<BasicItem> items =  new ArrayList<BasicItem>();
        Iterator<String> itemIterator  =  basicItems.keys();
        try{
            while(itemIterator.hasNext()){
                String itemString = itemIterator.next(); //the ID
                JSONObject itemContent = new JSONObject(basicItems.get(itemString).toString()); //contains name and coordinate
                String itemName = itemContent.get("name").toString();
                BasicItem newItem = new BasicItem(itemName, Integer.parseInt(itemString), getActions(itemContent.get("canBe").toString()), getActions(itemContent.get("usedTo").toString()));
                items.add(newItem);
            }
        }
        catch (Exception ex) {
            System.out.println("BasicItem reading error: " + ex);
        }
        return items;
    }

    private List<String> getActions(String actions) {
        return new ArrayList<>(Arrays.asList(actions.split(",")));
    }

    private void addContainers(JSONObject containers, Area area) {
        Iterator<String> containerIterator  =  containers.keys();
        try{
            while(containerIterator.hasNext()){
                String containerString = containerIterator.next(); //the ID
                JSONObject containerContent = new JSONObject(containers.get(containerString).toString()); //contains name, coordinate, description and entities

                String containerName = containerContent.get("name").toString(); // the name of the container
                String containerDescription = containerContent.get("description").toString();
                JSONObject containerEntities = new JSONObject(containerContent.get("entities").toString());
                List<BasicItem> basicItems = addBasicItems(containerEntities, area);
                area.addItem(new Container(containerName, Integer.parseInt(containerString), containerDescription, basicItems, getActions(containerContent.get("canBe").toString()),  getActions(containerContent.get("usedTo").toString())));
            }
        }
        catch (Exception ex) {
            System.out.println("Container reading error: " + ex);
        }
    }

}
