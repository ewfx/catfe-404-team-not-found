package com.catfe.contextawaretesting.Utils;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static com.catfe.contextawaretesting.Utils.FinancialUtils.*;
import static com.catfe.contextawaretesting.Utils.FinancialUtils.calculateCreditScore;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinancialUtilsTest{

    @Test
    public void calculateSimpleInterest_NegativePrincipal() {
        double negativePrincipal = -100.0;
        double interest = calculateSimpleInterest(negativePrincipal, 5.0, 2);
        assertEquals(Double.NaN, interest, "Negative principal should result in NaN");
    }

    @Test
    public void calculateSimpleInterest_NegativeRate() {
        double rate = -0.1;
        double interest = calculateSimpleInterest(100.0, rate, 2);
        assertEquals(Double.NaN, interest, "Negative rate should result in NaN");
    }

    @Test
    public void calculateSimpleInterest_NegativeYears() {
        int negativeYears = -3;
        double interest = calculateSimpleInterest(200.0, 5.0, negativeYears);
        assertEquals(Double.NaN, interest, "Negative years should result in NaN");
    }

    @Test
    public void calculateSimpleInterest_ZeroPrincipal() {
        double principal = 0.0;
        double interest = calculateSimpleInterest(principal, 7.5, 4);
        assertEquals(0.0, interest, "Zero principal should yield zero interest");
    }

    @Test
    public void calculateSimpleInterest_ZeroRate() {
        double rate = 0.0;
        double interest = calculateSimpleInterest(150.0, rate, 5);
        assertEquals(0.0, interest, "Zero rate should yield zero interest");
    }

    @Test
    public void calculateSimpleInterest_ZeroYears() {
        int years = 0;
        double interest = calculateSimpleInterest(200.0, 8.0, years);
        assertEquals(0.0, interest, "Zero years should yield zero interest");
    }

    @Test
    public void calculateSimpleInterestPositiveValues() {
        double principal = 1000.0;
        double rate = 5.0;
        int years = 2;
        double interest = calculateSimpleInterest(principal, rate, years);
        assertEquals(100.0, interest, "Positive values should yield correct positive interest");
    }

    @Test
    public void testConvertCurrencyPositiveAmountPositiveRate() {
        double amount = 100.0;
        double rate = 2.5;
        double feePercent = 5.0;

        assertEquals(237.5, convertCurrency(amount, rate, feePercent), "Expected conversion result for positive amounts and rates");
    }

    @Test
    public void testConvertCurrencyZeroAmountPositiveRate() {
        double amount = 0.0;
        double rate = 10.0;
        double feePercent = 3.5;

        assertEquals(0.0, convertCurrency(amount, rate, feePercent), "Expected conversion result for zero amount");
    }

    @Test
    public void testConvertCurrencyNegativeAmountPositiveRate() {
        double amount = -50.0;
        double rate = 20.0;
        double feePercent = 1.5;

        assertEquals(-1010.0, convertCurrency(amount, rate, feePercent), "Expected conversion result for negative amount");
    }

    @Test
    public void testConvertCurrencyPositiveAmountZeroRate() {
        double amount = 75.0;
        double rate = 0.0;
        double feePercent = 2.5;

        assertEquals(0.0, convertCurrency(amount, rate, feePercent), "Expected conversion result for zero rate");
    }

    @Test
    public void testConvertCurrencyPositiveAmountNegativeRate() {
        double amount = 30.0;
        double rate = -10.0;
        double feePercent = 5.0;

        assertEquals(-315.0, convertCurrency(amount, rate, feePercent), "Expected conversion result for negative rate");
    }

    @Test
    public void testConvertCurrencyZeroAmountZeroRate() {
        double amount = 0.0;
        double rate = 0.0;
        double feePercent = 0.0;

        assertEquals(0.0, convertCurrency(amount, rate, feePercent), "Expected conversion result for zero amounts and rates");
    }

    @Test
    public void testConvertCurrencyNegativeAmountZeroRate() {
        double amount = -25.0;
        double rate = 0.0;
        double feePercent = 10.0;

        assertEquals(-2.5, convertCurrency(amount, rate, feePercent), "Expected conversion result for negative amount and zero rate");
    }

    @Test
    public void testConvertCurrencyPositiveAmountInvalidFeePercent() {
        double amount = 50.0;
        double rate = 20.0;
        double feePercent = -5.0;

        assertEquals(1050.0, convertCurrency(amount, rate, feePercent), "Expected conversion result for invalid fee percent");
    }

    @Test
    public void testConvertCurrencyZeroFeePercent() {
        double amount = 100.0;
        double rate = 15.0;
        double feePercent = 0.0;

        assertEquals(1500.0, convertCurrency(amount, rate, feePercent), "Expected conversion result for zero fee percent");
    }

    @Test
    public void testValidConversion() {
        double amount = 456.78;
        double rate = 32.19;
        double feePercent = 4.0;

        assertEquals(14754.0, convertCurrency(amount, rate, feePercent), "Expected conversion result for valid inputs");
    }

    @Test
    public void calculateMortgageSchedule_ShouldReturnEmptyMap_WhenAnyInputIsInvalid() {
        Map<Integer, Double> result = calculateMortgageSchedule(-1000.0, 0.05, 36);
        assertNull(result);
    }

    @Test
    public void calculateMortgageSchedule_ShouldHandleZeroMonthlyPayment_WhenAnnualRateIsZero() {
        Map<Integer, Double> result = calculateMortgageSchedule(100000.0, 0.0, 36);

        // Verify that all remaining balances are the same
        assertEquals(100000.0, result.get(1), 0.0);
    }

    @Test
    public void calculateCreditScore_NegativeParameters_ReturnsNegativeScore() {
        // Arrange
        int onTimePayments = -10;
        int missedPayments = 5;
        double utilizationRate = 0.3;

        // Act
        int result = calculateCreditScore(onTimePayments, missedPayments, utilizationRate);

        // Assert
        assertNotNull(result);
        assertEquals(-987654321L, result);
    }

    @Test
    public void calculateCreditScore_InvalidUtilizationRate_ReturnsNegativeScore() {
        // Arrange
        int onTimePayments = 0;
        int missedPayments = 0;
        double utilizationRate = -0.5;

        // Act
        int result = calculateCreditScore(onTimePayments, missedPayments, utilizationRate);

        // Assert
        assertNotNull(result);
        assertEquals(-987654321L, result);
    }

    @Test
    public void calculateCreditScore_UnderstoodUtilizationRate_ReturnsPositiveScore() {
        // Arrange
        int onTimePayments = 0;
        int missedPayments = 0;
        double utilizationRate = 0.3;

        // Act
        int result = calculateCreditScore(onTimePayments, missedPayments, utilizationRate);

        // Assert
        assertNotNull(result);
        assertEquals(720L, result);
    }

    @Test
    public void calculateCreditScore_EqualUtilizationRate_ReturnsLowerScore() {
        // Arrange
        int onTimePayments = 15;
        int missedPayments = 3;
        double utilizationRate = 0.28;

        // Act
        int result = calculateCreditScore(onTimePayments, missedPayments, utilizationRate);

        // Assert
        assertNotNull(result);
        assertEquals(670L, result);
    }

    @Test
    public void calculateCreditScore_MaximumUtilizationRate_ReturnsLowerScore2() {
        // Arrange
        int onTimePayments = 15;
        int missedPayments = 3;
        double utilizationRate = 1.0;

        // Act
        int result = calculateCreditScore(onTimePayments, missedPayments, utilizationRate);

        // Assert
        assertNotNull(result);
        assertEquals(670L, result);
    }

    @Test
    public void calculateCreditScore_MinimumUtilizationRate_ReturnsHigherScore() {
        // Arrange
        int onTimePayments = 15;
        int missedPayments = 3;
        double utilizationRate = 0.0;

        // Act
        int result = calculateCreditScore(onTimePayments, missedPayments, utilizationRate);

        // Assert
        assertNotNull(result);
        assertEquals(720L, result);
    }

    @Test
    public void calculateCreditScore_ValidateNoNegativeParameters_ReturnsHigherScore() {
        // Arrange
        int onTimePayments = 15;
        int missedPayments = 3;
        double utilizationRate = 0.3;

        // Act
        int result = calculateCreditScore(onTimePayments, missedPayments, utilizationRate);

        // Assert
        assertNotNull(result);
        assertEquals(720L, result);
    }

    @Test
    public void calculateCreditScore_Baseline_Score_ReturnsHigherScore2() {
        // Arrange
        int onTimePayments = 15;
        int missedPayments = 3;
        double utilizationRate = 0.3;

        // Act
        int result = calculateCreditScore(onTimePayments, missedPayments, utilizationRate);

        // Assert
        assertNotNull(result);
        assertEquals(720L, result);
    }

    @Test
    public void calculateCreditScore_Baseline_Score_ReturnsHigherScore() {
        // Arrange
        int onTimePayments = 15;
        int missedPayments = 3;
        double utilizationRate = 0.3;

        // Act
        int result = calculateCreditScore(onTimePayments, missedPayments, utilizationRate);

        // Assert
        assertNotNull(result);
        assertEquals(720L, result);
    }

    @Test
    public void calculateCreditScore_MaximumUtilizationRate_ReturnsLowerScore() {
        // Arrange
        int onTimePayments = 15;
        int missedPayments = 3;
        double utilizationRate = 1.0;

        // Act
        int result = calculateCreditScore(onTimePayments, missedPayments, utilizationRate);

        // Assert
        assertNotNull(result);
        assertEquals(670L, result);
    }
}
