package Exercise7_FinancialForecasting;

import java.util.HashMap;
import java.util.Map;

/**
 * Financial Forecasting Tool
 *
 * Formula: FV = PV × (1 + r)^n
 *   PV = present value
 *   r  = annual growth rate (decimal, e.g. 0.08 for 8%)
 *   n  = number of years
 *
 * Recursive definition:
 *   futureValue(PV, r, 0) = PV                          (base case)
 *   futureValue(PV, r, n) = futureValue(PV, r, n-1) × (1 + r)
 *
 * Time Complexity:
 *   Plain recursion : O(n) – one call per year
 *   With memoization: O(n) time, O(n) space (avoids recomputation in extended scenarios)
 *
 * Optimization – Memoization:
 *   Cache intermediate results so the same sub-problem is not recomputed.
 *   Particularly useful when the same n is queried multiple times.
 */
public class FinancialForecasting {

    // Cache for memoization: key = n (years), value = multiplier (1+r)^n
    private static Map<Integer, Double> memo = new HashMap<>();

    // ---------- PLAIN RECURSION ----------
    public static double futureValueRecursive(double presentValue, double rate, int years) {
        if (years == 0) return presentValue;                         // base case
        return futureValueRecursive(presentValue, rate, years - 1) * (1 + rate);
    }

    // ---------- MEMOIZED RECURSION ----------
    // Computes the growth multiplier (1+r)^n with caching
    private static double growthMultiplier(double rate, int years) {
        if (years == 0) return 1.0;
        if (memo.containsKey(years)) return memo.get(years);

        double result = growthMultiplier(rate, years - 1) * (1 + rate);
        memo.put(years, result);
        return result;
    }

    public static double futureValueMemoized(double presentValue, double rate, int years) {
        memo.clear(); // reset cache for fresh calculation
        return presentValue * growthMultiplier(rate, years);
    }

    // ---------- ITERATIVE (for comparison) ----------
    public static double futureValueIterative(double presentValue, double rate, int years) {
        double fv = presentValue;
        for (int i = 0; i < years; i++) fv *= (1 + rate);
        return fv;
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        double presentValue = 100000.0; // ₹1,00,000
        double annualRate   = 0.08;     // 8% per annum
        int[] yearsList     = {1, 5, 10, 20, 30};

        System.out.printf("Present Value : ₹%.2f%n", presentValue);
        System.out.printf("Annual Rate   : %.0f%%%n%n", annualRate * 100);

        System.out.printf("%-6s | %-18s | %-18s | %-18s%n",
                "Years", "Recursive", "Memoized", "Iterative");
        System.out.println("-".repeat(68));

        for (int years : yearsList) {
            double r  = futureValueRecursive(presentValue, annualRate, years);
            double m  = futureValueMemoized(presentValue, annualRate, years);
            double it = futureValueIterative(presentValue, annualRate, years);
            System.out.printf("%-6d | ₹%-17.2f | ₹%-17.2f | ₹%-17.2f%n",
                    years, r, m, it);
        }

        System.out.println("\n--- Complexity Summary ---");
        System.out.println("Plain Recursion : O(n) time, O(n) stack space");
        System.out.println("Memoization     : O(n) time, O(n) cache space – avoids recomputation");
        System.out.println("Iterative       : O(n) time, O(1) space – most efficient for this problem");
    }
}
