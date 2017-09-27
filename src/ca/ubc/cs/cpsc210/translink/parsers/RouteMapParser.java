package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser for routes stored in a compact format in a txt file
 */
public class RouteMapParser {
    private String fileName;


    public RouteMapParser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Parse the route map txt file
     */
    public void parse() {
        DataProvider dataProvider = new FileDataProvider(fileName);
        try {
            String c = dataProvider.dataSourceToString();
            if (!c.equals("")) {
                int posn = 0;
                while (posn < c.length()) {
                    int endposn = c.indexOf('\n', posn);
                    String line = c.substring(posn, endposn);
                    parseOnePattern(line);
                    posn = endposn + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse one route pattern, adding it to the route that is named within it
     * @param str
     */
    private void parseOnePattern(String str) {
        // TODO: Task 3: Implement this method

        String routePattern;
        String routeNumber;
        List<LatLon> latlons = new ArrayList<>();

        //String newTxt = str.replace("N", "").trim();

        String[] tokens = str.split(";");

        System.out.println();

        int i;
        for (i = 1; i < tokens.length; i += 2) {

            //change made
            if (tokens[i].isEmpty() && tokens[i + 1].isEmpty()) {
                Double lat = Double.parseDouble(tokens[i].trim());
                Double lon = Double.parseDouble(tokens[i + 1].trim());
                LatLon latlon = new LatLon(lat, lon);

                latlons.add(latlon);
                System.out.println();
            } else


                latlons.add(new LatLon(Double.parseDouble(tokens[i].trim()), Double.parseDouble(tokens[i + 1].trim())));
        }

        routeNumber = tokens[0].replace("N", "").split("-", 2)[0].trim();
        System.out.println();
        routePattern = tokens[0].split("-", 2)[1];
        System.out.println();




        storeRouteMap(routeNumber,routePattern,latlons);

   }





    /**
     * Store the parsed pattern into the named route
     * Your parser should call this method to insert each route pattern into the corresponding route object
     * There should be no need to change this method
     *
     * @param routeNumber       the number of the route
     * @param patternName       the name of the pattern
     * @param elements          the coordinate list of the pattern
     */
    private void storeRouteMap(String routeNumber, String patternName, List<LatLon> elements) {
        Route r = RouteManager.getInstance().getRouteWithNumber(routeNumber);
        RoutePattern rp = r.getPattern(patternName);
        rp.setPath(elements);
    }
}
