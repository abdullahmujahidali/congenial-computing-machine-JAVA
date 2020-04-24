public class Rational extends Number implements Comparable<Rational> {
// Data fields for numerator and denominator
private long numerator = 0;
private long denominator = 1;

/**
* Construct a rational with default properties
*/
public Rational() {
this(0, 1);
}

/**
* Construct a rational with specified numerator and denominator
*/
public Rational(long numerator, long denominator) {
long gcd = gcd(numerator, denominator);
this.numerator = ((denominator > 0) ? 1 : -1) * numerator / gcd;
this.denominator = Math.abs(denominator) / gcd;
}

/**
* Find GCD of two numbers
*/
private static long gcd(long n, long d) {
long n1 = Math.abs(n);
long n2 = Math.abs(d);
int gcd = 1;

for (int k = 1; k <= n1 && k <= n2; k++) {
if (n1 % k == 0 && n2 % k == 0)
gcd = k;
}

return gcd;
}

/**
* Return numerator
*/
public long getNumerator() {
return numerator;
}

/**
* Return denominator
*/
public long getDenominator() {
return denominator;
}

/**
* Add a rational number to this rational
*/
public Rational add(Rational secondRational) {
long n = numerator * secondRational.getDenominator() +
denominator * secondRational.getNumerator();
long d = denominator * secondRational.getDenominator();
return new Rational(n, d);
}

/**
* Subtract a rational number from this rational
*/
public Rational subtract(Rational secondRational) {
long n = numerator * secondRational.getDenominator()
- denominator * secondRational.getNumerator();
long d = denominator * secondRational.getDenominator();
return new Rational(n, d);
}

/**
* Multiply a rational number to this rational
*/
public Rational multiply(Rational secondRational) {
long n = numerator * secondRational.getNumerator();
long d = denominator * secondRational.getDenominator();
return new Rational(n, d);
}

/**
* Divide a rational number from this rational
*/
public Rational divide(Rational secondRational) {
long n = numerator * secondRational.getDenominator();
long d = denominator * secondRational.numerator;
return new Rational(n, d);
}

@Override
public String toString() {
if (denominator == 1)
return numerator + "";
else
return numerator + "/" + denominator;
}

@Override // Override the equals method in the Object class
public boolean equals(Object other) {
if ((this.subtract((Rational) (other))).getNumerator() == 0)
return true;
else
return false;
}

@Override // Implement the abstract intValue method in Number
public int intValue() {
return (int) doubleValue();
}

@Override // Implement the abstract floatValue method in Number
public float floatValue() {
return (float) doubleValue();
}

@Override // Implement the doubleValue method in Number
public double doubleValue() {
return numerator * 1.0 / denominator;
}

@Override // Implement the abstract longValue method in Number
public long longValue() {
return (long) doubleValue();
}

// @Override // Implement the compareTo method in Comparable
public int compareTo(Rational o) {
if (this.subtract(o).getNumerator() > 0)
return 1;
else if (this.subtract(o).getNumerator() < 0)
return -1;
else
return 0;
}
  
}

//____________________________________________________________________

// ---------- FractionCalculator.java----------------------

import java.util.Scanner;


public class FractionCalculator {

   public static void main(String[] args) {
      
Scanner input = new Scanner(System.in);
   System.out.println("Enter a math problem containing fractions: ");
       String s = input.nextLine();
       s = s.replace(" ",""); // removing spaces
      
       int num,denom,indexOfSlash;
       String numstr,denomstr;
       Rational[] rationals = new Rational[2]; // maximum of 2 rationals expected
      
       // extract the correct sign from the entered expression
       char sign;
       if(s.contains("+"))
           sign = '+';
       else if(s.contains("*"))
           sign = '*';
       else if (s.contains("-"))
           sign = '-';
       else
           sign = '/'; // check it last since by default, every rational expression contains "/"
      
       String [] arrOfStr = new String[2]; // to hold the string version of the two rationals
       if (sign!='/')
       arrOfStr = s.split("\\"+sign); // split at the sign for +-* only
       else{ // for dividing
           // deal with the multiple slash signs
           int[] slashIndices = new int[3]; //expected 3 slashes maximum
           int idSlash = s.indexOf("/");
          
           // finding all indices of slashes in the input expression
           int i=0;
           while(idSlash>= 0) {
               slashIndices[i++] = idSlash;
           idSlash = s.indexOf("/",idSlash+1);
           }
          
           // the actual index of "divide" slash is the second index of "/" in every rational expression of 2 rationals
           int actualSlashIndex = slashIndices[1];
          
           // update the rational string array with the rationals and after the actual slash
           arrOfStr[0] = s.substring(0,actualSlashIndex);
           arrOfStr[1] = s.substring(actualSlashIndex+1);  
       }
      
       // dealing with the individual rationals to extract numerator and denominator
       int j = 0;
       for(String st:arrOfStr){
          
indexOfSlash = st.indexOf("/"); // in the current rational

// picking num and denom in string format
        numstr = st.substring(0,indexOfSlash);
       denomstr = st.substring(indexOfSlash+1,st.length());
         
       // converting num and denom to int
           num = Integer.parseInt(numstr);
           denom = Integer.parseInt(denomstr);
          
           // constructing a fraction of class Rational with num and denom found
       rationals[j++] = new Rational(num,denom);
       }
      
       // computing the final results of the operation
       Rational result = new Rational();
      
       switch(sign){
       case '+':
       result = rationals[0].add(rationals[1]); // call add method
       break;
       case '-':
           result = rationals[0].subtract(rationals[1]); // call subtract method
           break;
       case '*':
           result = rationals[0].multiply(rationals[1]); // call multiply method
           break;
       case '/':
           result = rationals[0].divide(rationals[1]); // call divide method
           break;
       default:
           System.out.println("Unknown operation");
           break;
       }
      
       // output final result formatted by the toString() method of class Rational
       System.out.println(rationals[0].toString()+" "+sign+" "+rationals[1].toString()+" = "+result.toString());
  
       input.close();
   }
}
