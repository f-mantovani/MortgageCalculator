package MortgageCalculator;

import java.text.NumberFormat;
import java.util.Scanner;

public class MortgageCalculator {
  private static final byte MONTHS_IN_YEAR = 12;
  private static final byte PERCENT = 100;
  private static final float RATE_MIN = 0;
  private static final float RATE_MAX = 0.3F;
  private static final float RATE_MAX_PERCENTAGE = RATE_MAX * 100;
  private static final byte YEARS_MIN = 1;
  private static final byte YEARS_MAX = 30;
  private static final int PRINCIPAL_MIN = 1_000;
  private static final int PRINCIPAL_MAX = 1_000_000;
  private static NumberFormat percent = NumberFormat.getPercentInstance();
  private static NumberFormat currency = NumberFormat.getCurrencyInstance();
  private static Scanner scanner = new Scanner(System.in);

  public static void main() {
    int principal = getPrincipal();

    float interestRate = getRates();

    byte years = getYears();
    short numberOfPayments = calculateMonths(years);
    float monthlyRate = calculateMonthlyRate(interestRate);

    double mortgage = calculateMortgage(principal, monthlyRate, numberOfPayments);

    String mortgageFormatted = currency.format(mortgage);
    System.out.println("Mortgage: " + mortgageFormatted);
    scanner.close();
  }

  private static int getPrincipal() {
    int principal;
    while (true) {
      System.out.print("Principal ($1K - $1M): ");
      principal = scanner.nextInt();
      if (principal < PRINCIPAL_MIN || principal > PRINCIPAL_MAX) {
        System.out.println(String.format("Enter a value between $%s and $%s", currency.format(PRINCIPAL_MIN),
            currency.format(PRINCIPAL_MAX)));
      } else {
        return principal;
      }
    }
  }

  private static float getRates() {
    float interestRate;
    while (true) {
      System.out.print("Annual Interest Rate: ");
      interestRate = scanner.nextFloat();
      if (interestRate <= RATE_MIN || interestRate > RATE_MAX_PERCENTAGE) {
        System.out.println(String.format("Enter a value bigger than %s and maximum %s",
            percent.format(RATE_MIN), percent.format(RATE_MAX)));
      } else {
        return interestRate;
      }
    }
  }

  private static float calculateMonthlyRate(float interestRate) {
    return (interestRate / PERCENT) / MONTHS_IN_YEAR;
  }

  private static byte getYears() {
    byte years;
    while (true) {
      System.out.print("Period (Years): ");
      years = scanner.nextByte();
      if (years < YEARS_MIN || years > YEARS_MAX) {
        System.out.println(String.format("Enter a value bigger than %s and maximum %s",
            YEARS_MIN, YEARS_MAX));
      } else {
        return years;
      }
    }
  }

  private static short calculateMonths(byte years) {
    return (short) (years * MONTHS_IN_YEAR);
  }

  private static double calculateMortgage(int principal, float monthlyRate, short numberOfPayments) {
    return principal
        * ((monthlyRate * Math.pow((1 + monthlyRate), numberOfPayments))
            / (Math.pow((1 + monthlyRate), numberOfPayments) - 1));
  }
}
