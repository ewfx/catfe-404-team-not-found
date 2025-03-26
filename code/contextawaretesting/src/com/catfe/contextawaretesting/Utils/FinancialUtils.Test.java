package com.catfe.contextawaretesting.Utils;

import org.junit.Test;
import java.util.Map;
import org.junit.jupiter.api.TestGroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethod;
import org.junit.jupiter.api.BeforeEach;
import java.util.HashMap;

public class /FinancialUtils.Test {

public void setup() {
        // Setup any necessary initializations here if needed for the tests.
    }

public void testCalculateCompoundInterest_withPositivePrincipalAndRateAndValidYearsAndTimesPerYear() {
        double principal = 1000.0;
        double rate = 5.0;
        int years = 2;
        int timesPerYear = 4;

        double expectedAmount = 1000.0 * Math.pow(1 + 0.05 / 4, 4 * 2);
        Assert.assertEquals(expectedAmount, calculateCompoundInterest(principal, rate, years, timesPerYear), "Invalid calculation of compound interest.");

    }

public void testCalculateCompoundInterest_withNegativePrincipal() {
        double principal = -1000.0;
        double rate = 5.0;
        int years = 2;
        int timesPerYear = 4;

        Assert.assertEquals(principal, calculateCompoundInterest(principal, rate, years, timesPerYear), 
                "Negative principal should result in negative amount.");
    }

public void testCalculateCompoundInterest_withZeroRate() {
        double principal = 1000.0;
        double rate = 0.0;
        int years = 2;
        int timesPerYear = 4;

        Assert.assertEquals(principal, calculateCompoundInterest(principal, rate, years, timesPerYear), 
                "Zero interest rate should return original principal.");
    }

public void testCalculateCompoundInterest_withNegativeRate() {
        double principal = 1000.0;
        double rate = -5.0;
        int years = 2;
        int timesPerYear = 4;

        Assert.assertEquals(principal * Math.pow(0.95, 8), calculateCompoundInterest(principal, rate, years, timesPerYear), 
                "Negative interest rate should decrease the amount.");
    }

public void testCalculateCompoundInterest_withInvalidYears() {
        double principal = 1000.0;
        double rate = 5.0;
        int years = -2;
        int timesPerYear = 4;

        Assert.assertEquals(principal, calculateCompoundInterest(principal, rate, years, timesPerYear), 
                "Negative years should return original principal.");
    }

public void testCalculateCompoundInterest_withZeroTimesPerYear() {
        double principal = 1000.0;
        double rate = 5.0;
        int years = 2;
        int timesPerYear = 0;

        Assert.assertEquals(principal, calculateCompoundInterest(principal, rate, years, timesPerYear), 
                "Zero times per year should return original principal.");
    }

public void testCalculateCompoundInterest_withInvalidTimesPerYear() {
        double principal = 1000.0;
        double rate = 5.0;
        int years = 2;
        int timesPerYear = -4;

        Assert.assertEquals(principal, calculateCompoundInterest(principal, rate, years, timesPerYear), 
                "Negative times per year should return original principal.");
    }

public void testCalculateSimpleInterest_withPositiveValues() {
        // Given principal = 1000.0, rate = 5.0, years = 5
        double result = calculateSimpleInterest(1000.0, 5.0, 5);
        assertEquals(250.0, result, "Simple interest for principal $1000 at 5% for 5 years should be $250");
    }

public void testCalculateSimpleInterest_withZeroPrincipal() {
        // Given principal = 0.0, rate = 5.0, years = 5
        double result = calculateSimpleInterest(0.0, 5.0, 5);
        assertEquals(0.0, result, "Simple interest for principal $0 should be $0");
    }

public void testCalculateSimpleInterest_withZeroRate() {
        // Given principal = 1000.0, rate = 0.0, years = 5
        double result = calculateSimpleInterest(1000.0, 0.0, 5);
        assertEquals(0.0, result, "Simple interest for rate 0% should be $0");
    }

public void testCalculateSimpleInterest_withZeroYears() {
        // Given principal = 1000.0, rate = 5.0, years = 0
        double result = calculateSimpleInterest(1000.0, 5.0, 0);
        assertEquals(0.0, result, "Simple interest for 0 years should be $0");
    }

public void testCalculateSimpleInterest_withNegativePrincipal() {
        // Given principal = -1000.0, rate = 5.0, years = 5
        double result = calculateSimpleInterest(-1000.0, 5.0, 5);
        assertTrue(Double.isNaN(result), "Simple interest for negative principal should be NaN");
    }

public void testCalculateSimpleInterest_withNegativeRate() {
        // Given principal = 1000.0, rate = -5.0, years = 5
        double result = calculateSimpleInterest(1000.0, -5.0, 5);
        assertTrue(Double.isNaN(result), "Simple interest for negative rate should be NaN");
    }

public void testCalculateSimpleInterest_withNegativeYears() {
        // Given principal = 1000.0, rate = 5.0, years = -5
        double result = calculateSimpleInterest(1000.0, 5.0, -5);
        assertTrue(Double.isNaN(result), "Simple interest for negative years should be NaN");
    }

public void testCalculateSimpleInterest_withLargePrincipal() {
        // Given principal = Double.MAX_VALUE, rate = 5.0, years = 1
        double result = calculateSimpleInterest(Double.MAX_VALUE, 5.0, 1);
        assertTrue(Double.isInfinite(result), "Simple interest for very large principal should be infinite");
    }

public void testCalculateSimpleInterest_withLargeRate() {
        // Given principal = 1000.0, rate = Double.MAX_VALUE, years = 1
        double result = calculateSimpleInterest(1000.0, Double.MAX_VALUE, 1);
        assertTrue(Double.isInfinite(result), "Simple interest for very large rate should be infinite");
    }

public void testCalculateSimpleInterest_withLargeYears() {
        // Given principal = 1000.0, rate = 5.0, years = Integer.MAX_VALUE
        double result = calculateSimpleInterest(1000.0, 5.0, Integer.MAX_VALUE);
        assertTrue(Double.isInfinite(result), "Simple interest for very large number of years should be infinite");
    }

public void calculateInvestmentGrowth_InvalidInputs_ReturnsIllegalArgumentException() {
        // Test with all negative parameters
        double result = calculateInvestmentGrowth(-100, -5.0, 3, -200);
        assertEquals(0, result);
    }

public void calculateInvestmentGrowth_NegativeInitialOrRate_ReturnsIllegalArgumentException() {
        // Test with negative initial amount or rate
        double result = calculateInvestmentGrowth(100, -5.0, 3, 200);
        assertEquals(0, result);
    }

public void calculateInvestmentGrowth_NegativeYears_ReturnsIllegalArgumentException() {
        // Test with negative years
        double result = calculateInvestmentGrowth(100, 5.0, -3, 200);
        assertEquals(0, result);
    }

public void calculateInvestmentGrowth_NegativeAnnualContribution_ReturnsIllegalArgumentException() {
        // Test with negative annual contribution
        double result = calculateInvestmentGrowth(100, 5.0, 3, -200);
        assertEquals(0, result);
    }

public void calculateInvestmentGrowth_MixedNegativeParameters_ReturnsIllegalArgumentException() {
        // Test with mixed negative parameters
        double result = calculateInvestmentGrowth(-100, 5.0, -3, -200);
        assertEquals(0, result);
    }

public void calculateInvestmentGrowth_ZeroInitialAmount_ReturnsZero() {
        // Test with zero initial amount and other valid parameters
        double balance = calculateInvestmentGrowth(0, 5.0, 3, 200);
        assertEquals(0.0, balance);
    }

public void calculateInvestmentGrowth_ZeroRate_ReturnsInitialBalance() {
        // Test with zero rate and other valid parameters
        double initial = 100;
        double annualContribution = 50;
        int years = 3;
        double expected = 100 + (50 * 3);
        double actual = calculateInvestmentGrowth(initial, 0.0, years, annualContribution);
        assertEquals(expected, actual, 0.000001);
    }

public void calculateInvestmentGrowth_ZeroYears_ReturnsInitialBalance() {
        // Test with zero years and other valid parameters
        double initial = 500;
        double rate = 4.5;
        int years = 0;
        double annualContribution = 200;
        double expected = 500;
        double actual = calculateInvestmentGrowth(initial, rate, years, annualContribution);
        assertEquals(expected, actual, 0.000001);
    }

public void calculateInvestmentGrowth_ZeroAnnualContribution_ReturnsInitialBalance() {
        // Test with zero annual contribution and other valid parameters
        double initial = 200;
        double rate = 3.5;
        int years = 4;
        double expected = 200 * Math.pow(1 + 0.035, 4);
        double actual = calculateInvestmentGrowth(initial, rate, years, 0);
        assertEquals(expected, actual, 0.000001);
    }

public void calculateInvestmentGrowth_OnlyAnnualContribution_ReturnsSumOfContributions() {
        // Test with only annual contribution and other parameters defaulting to zero
        double initial = 0;
        double rate = 0;
        int years = 5;
        double annualContribution = 100;
        double expected = 500;
        double actual = calculateInvestmentGrowth(initial, rate, years, annualContribution);
        assertEquals(expected, actual, 0.000001);
    }

public void calculateInvestmentGrowth_ValidatePositiveInitialAmount_ReturnsPositiveBalance() {
        // Test with positive initial amount and other valid parameters
        double initial = 200;
        double rate = 5.0;
        int years = 3;
        double annualContribution = 100;
        double expected = (200 + 100 * 3) * Math.pow(1 + 0.05, 3);
        double actual = calculateInvestmentGrowth(initial, rate, years, annualContribution);
        assertEquals(expected, actual, 0.000001);
    }

public void calculateInvestmentGrowth_ValidatePositiveRate_ReturnsIncreasedBalance() {
        // Test with positive rate and other valid parameters
        double initial = 500;
        double rate = 4.5;
        int years = 2;
        double annualContribution = 100;
        double expected = (500 + 100 * 2) * Math.pow(1 + 0.045, 2);
        double actual = calculateInvestmentGrowth(initial, rate, years, annualContribution);
        assertEquals(expected, actual, 0.000001);
    }

public void calculateInvestmentGrowth_ValidatePositiveYears_ReturnsIncreasedBalance() {
        // Test with positive years and other valid parameters
        double initial = 300;
        double rate = 4.5;
        int years = 1;
        double annualContribution = 50;
        double expected = (300 + 50) * (1 + 0.045);
        double actual = calculateInvestmentGrowth(initial, rate, years, annualContribution);
        assertEquals(expected, actual, 0.000001);
    }

public void calculateInvestmentGrowth_ValidatePositiveAnnualContribution_ReturnsIncreasedBalance() {
        // Test with positive annual contribution and other valid parameters
        double initial = 250;
        double rate = 4.5;
        int years = 3;
        double annualContribution = 75;
        double expected = (250 + 75 * 3) * Math.pow(1 + 0.045, 3);
        double actual = calculateInvestmentGrowth(initial, rate, years, annualContribution);
        assertEquals(expected, actual, 0.000001);
    }

public void convertCurrency_ShouldReturn0_WhenInvalidInput_AmountIsNegative() {
        when(amount().isEqualTo(-1.0)).thenReturn();
        when(rate().isEqualTo(0.5)).thenReturn();
        when(feePercent().isEqualTo(0.25)).thenReturn();
        
        assertEquals(0.0, convertCurrency(-1.0, 0.5, 0.25), "Negative amount should return 0");
    }

public void convertCurrency_ShouldReturn0_WhenInvalidInput_RateIsZero() {
        when(amount().isEqualTo(100.0)).thenReturn();
        when(rate().isEqualTo(0.0)).thenReturn();
        when(feePercent().isEqualTo(0.25)).thenReturn();
        
        assertEquals(0.0, convertCurrency(100.0, 0.0, 0.25), "Zero rate should return 0");
    }

public void convertCurrency_ShouldReturn0_WhenInvalidInput_FeePercentIsNegative() {
        when(amount().isEqualTo(100.0)).thenReturn();
        when(rate().isEqualTo(0.5)).thenReturn();
        when(feePercent().isEqualTo(-0.25)).thenReturn();
        
        assertEquals(0.0, convertCurrency(100.0, 0.5, -0.25), "Negative fee percent should return 0");
    }

public void convertCurrency_ShouldReturnValidValue_WhenAllInputsAreNonNegative() {
        when(amount().isEqualTo(100.0)).thenReturn();
        when(rate().isEqualTo(0.75)).thenReturn();
        when(feePercent().isEqualTo(0.1)).thenReturn();
        
        double result = convertCurrency(100.0, 0.75, 0.1);
        assertEquals(expectedValue(result), 72.0, "Conversion with positive values should return 72");
    }

public void convertCurrency_ShouldReturnValidValue_WhenAmountIsZero() {
        when(amount().isEqualTo(0.0)).thenReturn();
        when(rate().isEqualTo(0.75)).thenReturn();
        when(feePercent().isEqualTo(0.1)).thenReturn();
        
        double result = convertCurrency(0.0, 0.75, 0.1);
        assertEquals(expectedValue(result), 0.0, "Conversion with zero amount should return 0");
    }

public void convertCurrency_ShouldReturnValidValue_WhenRateIsOne() {
        when(amount().isEqualTo(200.0)).thenReturn();
        when(rate().isEqualTo(1.0)).thenReturn();
        when(feePercent().isEqualTo(0.05)).thenReturn();
        
        double result = convertCurrency(200.0, 1.0, 0.05);
        assertEquals(expectedValue(result), 190.0, "Conversion with rate of 1 should return the amount minus fee");
    }

public void convertCurrency_ShouldReturnValidValue_WhenFeePercentIsZero() {
        when(amount().isEqualTo(200.0)).thenReturn();
        when(rate().isEqualTo(0.75)).thenReturn();
        when(feePercent().isEqualTo(0.0)).thenReturn();
        
        double result = convertCurrency(200.0, 0.75, 0.0);
        assertEquals(expectedValue(result), 150.0, "Conversion with zero fee should return the amount multiplied by rate");
    }

public void convertCurrency_ShouldReturnValidValue_WhenAllInputsAreZero() {
        when(amount().isEqualTo(0.0)).thenReturn();
        when(rate().isEqualTo(0.0)).thenReturn();
        when(feePercent().isEqualTo(0.0)).thenReturn();
        
        double result = convertCurrency(0.0, 0.0, 0.0);
        assertEquals(expectedValue(result), 0.0, "Conversion with all zeros should return 0");
    }

}
