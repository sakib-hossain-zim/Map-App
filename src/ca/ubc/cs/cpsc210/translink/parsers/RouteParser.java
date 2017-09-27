package ca.ubc.cs.cpsc210.translink.parsers;


import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Parse route information in JSON format.
 */
public class RouteParser {
    private String filename;

    public RouteParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse route data from the file and add all route to the route manager.
     */
    public void parse() throws IOException, RouteDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);



        parseRoutes(dataProvider.dataSourceToString());


        }

    /**
     * Parse route information from JSON response produced by Translink.
     * Stores all routes and route patterns found in the RouteManager.
     *
     * @param jsonResponse string encoding JSON data to be parsed
     * @throws JSONException             when JSON data does not have expected format
     * @throws RouteDataMissingException when
     *                                   <ul>
     *                                   <li> JSON data is not an array </li>
     *                                   <li> JSON data is missing Name, StopNo, Routes or location elements for any stop</li>
     *                                   </ul>
     */

    public void parseRoutes(String jsonResponse)
            throws JSONException, RouteDataMissingException {
        // TODO: Task 4: Implement this method

        JSONArray routes = new JSONArray(jsonResponse);


        for (int i = 0; i < routes.length(); i++) {
            JSONObject route = routes.getJSONObject(i);

                parseRoute(route);

        }
    }

    public void parseRoute(JSONObject route) throws JSONException,RouteDataMissingException {

        if(((!route.has("RouteNo")))||(!route.has("Name"))||(!route.has("Patterns"))){
            throw new RouteDataMissingException("Missing info");
        }
        String number = route.getString("RouteNo");
        System.out.println();
        String name = route.getString("Name");
        System.out.println();

        RouteManager.getInstance().getRouteWithNumber(number,name);



        JSONArray routePatterns = route.getJSONArray("Patterns");

        String destination;
        String direction;
        String patternNo;

        for(int index=0;index <routePatterns.length();index++){
            JSONObject routeP = routePatterns.getJSONObject(index);
            if ((!routeP.has("Destination"))||(!routeP.has("Direction"))||(!routeP.has("PatternNo"))){
                throw new RouteDataMissingException("Missing info of pattern");
            }
            else
             destination = routeP.getString("Destination");
            System.out.println();
             direction = routeP.getString("Direction");
            System.out.println();
             patternNo = routeP.getString("PatternNo");
            System.out.println();

            RouteManager.getInstance().getRouteWithNumber(number,name).getPattern(patternNo,destination,direction);

        }

    }
}

