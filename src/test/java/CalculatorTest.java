import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import vorobieva.DeliveryCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class CalculatorTest {

    @ParameterizedTest
    @Tags({@Tag("delivery"), @Tag("smoke")})
    @DisplayName("Verifying amount of delivery cost")
    @CsvSource({
            "11, Small, Fragile, High, 720",
            "35, large, not fragile, very high, 800",
            "9, Small, Not fragile, Medium high, 400",
            "1, Small, Fragile, Low, 450"
    })
    void calculateCostTest(int distance, String size, String fragile, String overload, double expectedCost) {
        DeliveryCalculator calculator = new DeliveryCalculator();
        double actualCost = calculator.calculateCost(distance, size, fragile, overload);

        assertEquals(expectedCost, actualCost, 0.01, "Cost is not calculated correct");
    }

    @ParameterizedTest
    @Tag("Exception message")
    @DisplayName("Verifying exception message for not valid distance")
    @CsvSource({
            "0, Small, Fragile, High",
            "-5, Large, Not fragile, Very high",
    })
    void verifyExceptionNotValidDistanceTest(int distance, String size, String fragile, String overload) {
        DeliveryCalculator calculator = new DeliveryCalculator();
        IllegalArgumentException thrown = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> calculator.calculateCost(distance, size, fragile, overload)
        );

        assertEquals("Distance must be greater than 0.", thrown.getMessage());
    }

    @ParameterizedTest
    @Tag("Exception message")
    @DisplayName("Verifying exception message for empty or null size")
    @NullSource
    @EmptySource
    void verifyExceptionEmptyOrNullSizeTest(String size) {
        DeliveryCalculator calculator = new DeliveryCalculator();
        IllegalArgumentException thrown = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> calculator.calculateCost(10, size, "Fragile", "High")
        );

        assertEquals("Size cannot be null or empty.", thrown.getMessage());
    }

    @ParameterizedTest
    @Tag("Exception message")
    @DisplayName("Verifying exception message for empty or null fragile")
    @NullSource
    @EmptySource
    void verifyExceptionEmptyOrNullFragileTest(String fragile) {
        DeliveryCalculator calculator = new DeliveryCalculator();
        IllegalArgumentException thrown = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> calculator.calculateCost(10, "Small", fragile, "High")
        );

        assertEquals("Fragile status cannot be null or empty.", thrown.getMessage());
    }

    @ParameterizedTest
    @Tag("Exception message")
    @DisplayName("Verifying exception message for empty or null overload status")
    @NullSource
    @EmptySource
    void verifyExceptionEmptyOrNullOverloadStatusTest(String overload) {
        DeliveryCalculator calculator = new DeliveryCalculator();
        IllegalArgumentException thrown = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> calculator.calculateCost(10, "large", "fragile", overload)
        );
        assertEquals("Overload status cannot be null or empty.", thrown.getMessage());
    }

    @Test
    @Tag("Exception message")
    @DisplayName("Verifying exception message for not valid size input")
    void verifyMessageInvalidSizeInput() {
        DeliveryCalculator calculator = new DeliveryCalculator();
        IllegalArgumentException thrown = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> calculator.calculateCost(20, "huge", "fragile", "high")
        );
        assertEquals("Size must be 'Large' or 'Small'.",
                thrown.getMessage());
    }

    @Test
    @Tag("Exception message")
    @DisplayName("Verifying exception message for not valid fragile input")
    void verifyMessageInvalidFragileInput() {
        DeliveryCalculator calculator = new DeliveryCalculator();
        IllegalArgumentException thrown = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> calculator.calculateCost(20, "Large", "little fragile", "high")
        );
        assertEquals("Fragile status must be 'Fragile' or 'Not fragile'.",
                thrown.getMessage());
    }

    @Test
    @Tag("Exception message")
    @DisplayName("Verifying exception message for not valid overload input")
    void verifyMessageInvalidOverloadInput() {
        DeliveryCalculator calculator = new DeliveryCalculator();
        IllegalArgumentException thrown = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> calculator.calculateCost(10, "small", "not fragile", "little")
        );

        assertEquals("Overload must be 'Very high', 'Medium high', 'High', or 'Low'.",
                thrown.getMessage());
    }

    @Test
    @Tag("Exception message")
    @DisplayName("Verifying that no delivery for fragile for certain distance")
    void verifyExceptionForFragileTest() {
        DeliveryCalculator calculator = new DeliveryCalculator();
        IllegalArgumentException thrown = assertThrowsExactly(
                IllegalArgumentException.class,
                () -> calculator.calculateCost(40, "large", "fragile", "high")
        );

        assertEquals("You cannot deliver fragile stuff on distance more than 30km.",
                thrown.getMessage());
    }
}
