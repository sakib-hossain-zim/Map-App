package ca.ubc.cs.cpsc210.translink.model;

import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A description of one pattern of a route
 * Each pattern has a name, destination, direction, list of points (of class LatLon), and Route
 */

// TODO: Task 2: Complete all the methods in this class

public class RoutePattern {
    private String name;
    private String destination;
    private String direction;
    private List<LatLon> latlons;
    private Route route;

    /**
     * Construct a new RoutePattern with the given information
     * @param name          the name of the pattern
     * @param destination   the destination
     * @param direction     the direction
     * @param route         the Route of which this is a pattern
     */
    public RoutePattern(String name, String destination, String direction, Route route) {
        this.name = name;
        this.destination = destination;
        this.direction = direction;
        this.route = route;
        latlons = new ArrayList<>();

    }

    /**
     * Get the pattern name
     * @return      the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the pattern destination
     * @return      the destination
     */
    public String getDestination() {
        return this.destination;
    }

    /**
     * Get the pattern direction
     * @return      the direction
     */
    public String getDirection() {
        return this.direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoutePattern)) return false;

        RoutePattern that = (RoutePattern) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Set the pattern path: list of coordinates
     * @param path      the path
     */
    public void setPath(List<LatLon> path) {

        this.latlons = path;

    }

    /**
     * Return the list of coordinates making up this pattern
     *
     * @return      the list of coordinates
     */
    public List<LatLon> getPath() {
        return Collections.unmodifiableList(latlons);
    }

    /**
     * Set the direction
     * @param direction     the direction
     */
    public void setDirection(String direction) {
        this.direction = direction;

    }

    /**
     * Set the destination
     * @param destination     the destination
     */
    public void setDestination(String destination) {
        this.destination = destination;

    }
}
