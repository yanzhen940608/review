import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * This class is used to execute gamma function.
 * 
 * @author Liangzhao Lin
 */
public class GammaX {
  static double PI = 3.14159265358979323846264338327950288419716;

  /**
   * This method execute gamma function.
   * 
   * @param args Unused.
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    boolean enter = true;
    while (enter) {
      try {
        System.out.println("Please enter a number x :");
        double x = sc.nextDouble();
        if ((!Double.isFinite(x))) {
          System.out.println("Input error, the input is not a finite floating-point value.");
        } else if (x == 0 || (x < 0 && x % 1 == 0)) {
          enter = false;
          System.out.println("Γ(x) = Infinity");
        } else if (x < -1E8) {
          enter = false;
          System.out.println("Γ(x) = Infinity");
        } else {
          enter = false;
          double gammaX = gamma(x);
          if (Double.isInfinite(gammaX)) {
            System.out.println("Γ(x) = Infinity");
          } else {
            if (x % 1 == 0 && gammaX < 1E9) {
              System.out.println("Γ(x) = " + new DecimalFormat("#").format(gammaX));
            } else {
              System.out.println("Γ(x) = " + new DecimalFormat("#.######E0").format(gammaX));
            }
          }
        }
      } catch (Exception e) {
        System.out.println("Input error, your input is not a number.");
        sc = new Scanner(System.in);
      }
    }
  }

  /**
   * This method execute lnGamma function.
   * 
   * @param x The value to be calculated logGamma(x). x should greater than 1;
   * @return The double type value of log Γ(x).
   */
  public static double logGamma(double x) {
    double sqrt2PI = 2.5066282746310002;
    double leadingFactor = (x - 0.5) * ln(x + 4.5) - (x + 4.5);
    double series = 1.000000000190015 + 76.18009172947146 / (x + 0) - 86.50532032941677 / (x + 1)
        + 24.01409824083091 / (x + 2) - 1.231739572450155 / (x + 3) + 0.001208650973866179 / (x + 4)
        - 0.000005395239384953 / (x + 5);
    return leadingFactor + ln(series * sqrt2PI);
  }

  /**
   * This method calculate the gamma(x).
   * 
   * @param x A double type value in the domain.
   * @return The double type value of Γ(x).
   */
  public static double gamma(double x) {
    if (x < 1 && x > -2000) {
      return gamma(x + 1) / x;
    } else if (x <= -2000) {
      return PI / ((gamma(1 - x)) * sine(PI * x));
    } else {
      return exp(logGamma(x));
    }
  }

  /**
   * This method calculate the value of ln(x).
   * 
   * @param x The value should greater than 1.
   * @return sum The double type value of ln(x).
   */
  public static double ln(double x) {
    if (x > 1) {
      double ln2 = 0.6931471805599453;
      return ln2 + ln(x / 2);
    } else {
      int sign = 1;
      double numerator = 1.0;
      double denominator = 1.0;
      double sum = 0.0;
      double term = 1.0;
      for (int i = 1; sum != sum + term; i++) {
        numerator = numerator * (x - 1);
        denominator = i;
        if (i % 2 == 1) {
          sign = 1;
        } else {
          sign = -1;
        }
        term = (numerator * sign) / denominator;
        sum = sum + term;
      }
      return sum;
    }
  }

  /**
   * This method calculate the value of e^x.
   * 
   * @param x The value should be double type.
   * @return sum The double type value of e^x.
   */
  public static double exp(double x) {
    boolean isNegative = false;
    if (x < 0) {
      isNegative = true;
      x = -x;
    }
    double term = 1.0;
    double sum = 0.0;
    for (int i = 1; sum != sum + term; i++) {
      sum += term;
      term *= x / i;
    }
    if (isNegative) {
      sum = 1.0 / sum;
    }
    return sum;
  }

  /**
   * This method calculate the value of sin(x).
   * 
   * @param x The value should greater than -1E8 and less than -6000.
   * @return The double type value of sin(x).
   */
  public static double sine(double x) {
    while (x < 0) {
      x = x + 2 * PI;
    }
    double sum = x;
    double fac = 1.0;
    double xn = x;
    double term = x;
    int i = 1;
    int sign = 1;
    while (term > 1E-15) {
      i++;
      fac = fac * i * (++i);
      xn *= x * x;
      sign = -sign;
      term = xn / fac;
      sum = (sign > 0) ? (sum + term) : (sum - term);
    }
    return sum;
  }
}
