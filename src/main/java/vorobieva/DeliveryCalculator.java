package vorobieva;


public class DeliveryCalculator {

    public double calculateCost(int distance, String size, String fragile, String overload) {
        double cost = 0;

        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be greater than 0.");
        }

        if (size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("Size cannot be null or empty.");
        }
        if (fragile == null || fragile.trim().isEmpty()) {
            throw new IllegalArgumentException("Fragile status cannot be null or empty.");
        }
        if (overload == null || overload.trim().isEmpty()) {
            throw new IllegalArgumentException("Overload status cannot be null or empty.");
        }

        if (!size.equalsIgnoreCase("large") && !size.equalsIgnoreCase("small")) {
            throw new IllegalArgumentException("Size must be 'Large' or 'Small'.");
        }
        if (!fragile.equalsIgnoreCase("fragile") && !fragile.equalsIgnoreCase("not fragile")) {
            throw new IllegalArgumentException("Fragile status must be 'Fragile' or 'Not fragile'.");
        }
        if (!overload.equalsIgnoreCase("very high") &&
                !overload.equalsIgnoreCase("medium high") &&
                !overload.equalsIgnoreCase("high") &&
                !overload.equalsIgnoreCase("low")) {
            throw new IllegalArgumentException("Overload must be 'Very high', 'Medium high', 'High', or 'Low'.");
        }
        if (distance > 30 && fragile.equalsIgnoreCase("fragile")) {
            throw new IllegalArgumentException("You cannot deliver fragile stuff on distance more than 30km.");
        }

        if (distance > 30) {
            cost += 300;
        } else if (distance > 10) {
            cost += 200;
        } else if (distance > 2) {
            cost += 100;
        } else {
            cost += 50;
        }

        cost += size.equalsIgnoreCase("large") ? 200 : 100;

        if (fragile.equalsIgnoreCase("fragile")) {
            cost += 300;
        }

        switch (overload.toLowerCase()) {
            case "very high" -> cost *= 1.6;
            case "medium high" -> cost *= 1.4;
            case "high" -> cost *= 1.2;
            default -> cost *= 1.0;
        }

        return Math.max(cost, 400);
    }
}