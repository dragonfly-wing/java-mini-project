package utils;

// Generic class to convert units
public class UnitConverter<T extends Number> {

    // Convert kg to lbs
    public double kgToLbs(T kg) {
        if (kg.doubleValue() < 0) throw new RuntimeException("Weight cannot be negative.");
        return kg.doubleValue() * 2.20462;
    }

    // Convert lbs to kg
    public double lbsToKg(T lbs) {
        if (lbs.doubleValue() < 0) throw new RuntimeException("Weight cannot be negative.");
        return lbs.doubleValue() / 2.20462;
    }

    // Convert km to miles
    public double kmToMiles(T km) {
        if (km.doubleValue() < 0) throw new RuntimeException("Distance cannot be negative.");
        return km.doubleValue() * 0.621371;
    }

    // Convert miles to km
    public double milesToKm(T miles) {
        if (miles.doubleValue() < 0) throw new RuntimeException("Distance cannot be negative.");
        return miles.doubleValue() / 0.621371;
    }
}

