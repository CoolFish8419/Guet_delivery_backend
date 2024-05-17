package com.example.backend.util;

import java.util.HashMap;
import java.util.Map;

public class LocationUtil {
    private static Map<String, Location> locations = new HashMap<>();

    static {
        locations.put("中央食堂", new Location("110.416231", "25.316329"));
        locations.put("后街", new Location("110.415908", "25.320561"));
        locations.put("信科", new Location("110.414499", "25.307482"));
    }

    static class Location {
        private String longitude;
        private String latitude;

        public Location(String longitude, String latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }
    }

    public static Location getLocationByName(String name) {
        return locations.get(name);
    }

}