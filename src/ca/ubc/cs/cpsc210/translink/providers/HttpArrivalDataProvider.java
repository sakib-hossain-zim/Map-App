package ca.ubc.cs.cpsc210.translink.providers;

import ca.ubc.cs.cpsc210.translink.BusesAreUs;
import ca.ubc.cs.cpsc210.translink.model.Stop;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Wrapper for Translink Arrival Data Provider
 */
public class HttpArrivalDataProvider extends AbstractHttpDataProvider {
    private Stop stop;

    public HttpArrivalDataProvider(Stop stop) {
        super();
        this.stop = stop;
    }

    @Override
    /**
     * Produces URL used to query Translink web service for expected arrivals at
     * the stop specified in call to constructor.
     *
     * @returns URL to query Translink web service for arrival data
     *
     * TODO: Complete the implementation of this method (Task 8)
     */
    protected URL getURL() throws MalformedURLException {

        String start = "http://api.translink.ca/rttiapi/v1/stops/";
        int stopId = stop.getNumber();
        String estimate = "/estimates?apikey=";
        String api = BusesAreUs.TRANSLINK_API_KEY;




        String url = start + stopId + estimate + api;
        URL newUrl = new URL(url);







       // http://api.translink.ca/rttiapi/v1/stops/60980/estimates?apikey=[APIKey]&routeNo=050
      //  http://api.translink.ca/rttiapi/v1/stops/60980/estimates?apikey=[APIKey]

       // ZRJFRibrYuX7zU8WxAHY
        return newUrl;
    }

    @Override
    public byte[] dataSourceToBytes() throws IOException {
        return new byte[0];
    }
}
