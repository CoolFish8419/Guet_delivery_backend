package com.example.backend.util;
import java.util.Optional;

import com.example.backend.entity.Order;
import com.example.backend.entity.Receiver;
import java.util.List;

import static com.example.backend.util.LocationUtil.getLocationByName;

public class Tools {
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 地球半径（千米）
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    public static Optional<Receiver> assignCourier(List<Receiver> couriers, Order order) {
        return couriers.stream().min((c1, c2) -> {
            double score1 = calculateScore(c1, order);
            double score2 = calculateScore(c2, order);
            return Double.compare(score1, score2);
        });
    }

    public static double calculateScore(Receiver receiver, Order order) {
        int distance2 = order.getDistancePubParcel();
        LocationUtil.Location location = getLocationByName(order.getParcelArea());
        Double parLon = null;
        Double parlat = null;
        if (location != null) {
            parLon = Double.valueOf(location.getLongitude());
            parlat = Double.valueOf(location.getLatitude());
        }
        String[] parts = receiver.getCoordinates().split(","); // 逗号作为分隔符
        Double longitude = Double.valueOf(parts[0]); // 经度
        Double latitude = Double.valueOf(parts[1]); // 纬度
        double distanceScore = calculateDistance(latitude, longitude, parLon, parlat) + distance2;
        double ratingScore = receiver.getScore() * 200; // 假设评分的权重是10
        return distanceScore - ratingScore;
    }
}
