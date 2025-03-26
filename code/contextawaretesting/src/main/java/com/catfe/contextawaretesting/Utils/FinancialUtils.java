package com.catfe.contextawaretesting.Utils;
import java.util.HashMap;
import java.util.Map;

public class FinancialUtils {

    // Calculate compound interest
    public static double calculateCompoundInterest(double principal, double rate, int years, int timesPerYear) {
        if (principal < 0 || rate < 0 || years < 0 || timesPerYear <= 0) {
            throw new IllegalArgumentException("Inputs cannot be negative or zero");
        }
        double total = principal * Math.pow(1 + (rate / 100) / timesPerYear, timesPerYear * years);
        return Math.round(total * 100.0) / 100.0;
    }

    // Calculate simple interest
    public static double calculateSimpleInterest(double principal, double rate, int years) {
        if (principal < 0 || rate < 0 || years < 0) {
            throw new IllegalArgumentException("Inputs cannot be negative");
        }
        return principal + (principal * rate * years) / 100;
    }

    // Calculate monthly loan payment with early payoff
    public static double calculateLoanPayment(double principal, double annualRate, int months, int earlyPayoffMonth) {
        if (principal < 0 || annualRate < 0 || months <= 0) {
            throw new IllegalArgumentException("Invalid loan input");
        }

        double monthlyRate = annualRate / 12 / 100;
        double payment = (principal * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        if (earlyPayoffMonth > 0 && earlyPayoffMonth < months) {
            double remainingBalance = principal;
            for (int i = 0; i < earlyPayoffMonth; i++) {
                remainingBalance = remainingBalance * (1 + monthlyRate) - payment;
            }
            return Math.round(remainingBalance * 100.0) / 100.0;
        }

        return Math.round(payment * 100.0) / 100.0;
    }

    // Calculate investment growth over time using annual contributions
    public static double calculateInvestmentGrowth(double initialAmount, double rate, int years, double annualContribution) {
        if (initialAmount < 0 || rate < 0 || years < 0 || annualContribution < 0) {
            throw new IllegalArgumentException("Invalid input values");
        }

        double balance = initialAmount;
        for (int i = 0; i < years; i++) {
            balance += annualContribution;
            balance *= (1 + rate / 100);
        }
        return Math.round(balance * 100.0) / 100.0;
    }

    // Convert currency with transaction fee
    public static double convertCurrency(double amount, double rate, double feePercent) {
        if (amount < 0 || rate <= 0 || feePercent < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        double convertedAmount = amount * rate;
        double fee = convertedAmount * feePercent / 100;
        return Math.round((convertedAmount - fee) * 100.0) / 100.0;
    }

    // Calculate tax with deductions and brackets
    public static double calculateTax(double income, double[] deductions) {
        if (income < 0 || deductions == null) {
            throw new IllegalArgumentException("Invalid income or deductions");
        }

        for (double deduction : deductions) {
            if (deduction < 0) {
                throw new IllegalArgumentException("Deductions cannot be negative");
            }
            income -= deduction;
        }

        if (income <= 10000) {
            return income * 0.10;
        } else if (income <= 50000) {
            return 1000 + (income - 10000) * 0.20;
        } else {
            return 9000 + (income - 50000) * 0.30;
        }
    }

    // Calculate monthly mortgage amortization schedule
    public static Map<Integer, Double> calculateMortgageSchedule(double principal, double annualRate, int months) {
        if (principal < 0 || annualRate < 0 || months <= 0) {
            throw new IllegalArgumentException("Invalid mortgage input");
        }

        double monthlyRate = annualRate / 12 / 100;
        double payment = (principal * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        Map<Integer, Double> schedule = new HashMap<>();
        double remainingBalance = principal;
        for (int month = 1; month <= months; month++) {
            remainingBalance = remainingBalance * (1 + monthlyRate) - payment;
            schedule.put(month, Math.round(remainingBalance * 100.0) / 100.0);
        }
        return schedule;
    }

    // Calculate credit score based on payment history and credit utilization
    public static int calculateCreditScore(int onTimePayments, int missedPayments, double utilizationRate) {
        if (onTimePayments < 0 || missedPayments < 0 || utilizationRate < 0 || utilizationRate > 1) {
            throw new IllegalArgumentException("Invalid credit score input");
        }

        int baseScore = 300;
        baseScore += onTimePayments * 5;
        baseScore -= missedPayments * 20;
        baseScore -= (int) (utilizationRate * 100);

        if (baseScore > 850) return 850;
        if (baseScore < 300) return 300;
        return baseScore;
    }
}