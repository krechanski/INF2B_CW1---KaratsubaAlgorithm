package imult;
/*
 * Class StudentCode: class for students to implement
 * the specific methods required by the assignment:
 * add()
 * sub()
 * koMul()
 * koMulOpt()
 * (See coursework handout for full method documentation)
 */

import java.io.File;

public class StudentCode {
  public static BigInt add(BigInt A, BigInt B) {
    int i=0; //index for keeping track of the variables we need to do opeartions on
    Arithmetic arr = new Arithmetic();
    BigInt C = new BigInt();
    DigitAndCarry dc = new DigitAndCarry();
    dc.setCarry(0);
    Unsigned digit, carry;
    while (i < A.length() || i < B.length()){ //Do the adding of the digits until we are finished.
      carry = dc.getCarry();
      dc = arr.addDigits(A.getDigit(i), B.getDigit(i), carry);
      digit = dc.getDigit();
      C.setDigit(i, digit);
      i++;
    }

    //Include the carry digit in the result if there is one.
    if(dc.getCarry().intValue() > 0){
      C.setDigit(i, dc.getCarry());
    }
    return C;
  }

  public static BigInt sub(BigInt A, BigInt B) {
    int i=0; //index for keeping track of the variables we need to do opeartions on
    Arithmetic arr = new Arithmetic();
    BigInt C = new BigInt();
    DigitAndCarry dc = new DigitAndCarry();
    dc.setDigit(0);
    dc.setCarry(0);
    Unsigned digit, carry;
    while (i < A.length()){ // If the index is less than the length, we get a carry from previous index and continue with the initial operation.
      carry = dc.getCarry();
      dc = arr.subDigits(A.getDigit(i), B.getDigit(i), carry);
      digit = dc.getDigit();
      C.setDigit(i, digit);
      i++;
    }
    return C;
  }

  public static BigInt koMul(BigInt A, BigInt B) {
    // Find the length of both numbers and multiply the digits directly if the length is less than 1
    Arithmetic arr = new Arithmetic();
    DigitAndCarry dc ;
    BigInt C = new BigInt();
    int n = Math.max(A.length(), B.length());
    if (n<=1) {
      Unsigned a0 = new Unsigned (A.value());
      Unsigned b0 = new Unsigned (B.value());
      dc = arr.mulDigits(a0, b0);
      C.setDigit(0, dc.getDigit());
      C.setDigit(1, dc.getCarry());

      return C;
    }

    //Here we find the floor, split and do the multiplication and subtracting recursevly.
    int floor = n/2;
    BigInt alpha1 = A.split(floor, n-1);
    BigInt alpha0 = A.split(0, floor - 1);
    BigInt beta1 = B.split(floor, n-1);
    BigInt beta0 = B.split(0, floor - 1);

    BigInt l, h, m;
    l = koMul(alpha0, beta0);
    h = koMul(alpha1, beta1);
    m = sub(sub(koMul(add(alpha0, alpha1), add(beta0, beta1)), l),  h);
    m.lshift(floor);
    h.lshift(2*floor);
    return add(add(l, m), h);
  }



  public static BigInt koMulOpt(BigInt A, BigInt B) {
    /*Here we have copied the code from koMul and the diffence is only that we added
      the condition of the length is less than 10 do the schoolMul instead.*/

    Arithmetic arr = new Arithmetic();
    if (Math.min(A.length(), B.length()) <= 10){
      return arr.schoolMul(A, B);
    }

    else {
      DigitAndCarry dc ;
      BigInt C = new BigInt();
      int n = Math.max(A.length(), B.length());
      if (n<=1) {
        Unsigned a0 = new Unsigned (A.value());
        Unsigned b0 = new Unsigned (B.value());
        dc = arr.mulDigits(a0, b0);
        C.setDigit(0, dc.getDigit());
        C.setDigit(1, dc.getCarry());

        return C;
      }

      int floor = n/2;
      BigInt alpha1 = A.split(floor, n-1);
      BigInt alpha0 = A.split(0, floor - 1);
      BigInt beta1 = B.split(floor, n-1);
      BigInt beta0 = B.split(0, floor - 1);

      BigInt l, h, m;
      l = koMulOpt(alpha0, beta0);
      h = koMulOpt(alpha1, beta1);
      m = sub(sub(koMulOpt(add(alpha0, alpha1), add(beta0, beta1)), l),  h);
      m.lshift(floor);
      h.lshift(2*floor);
      return add(add(l, m), h);
    }

  }

  public static void main(String argv[]) throws java.io.FileNotFoundException {
    BigInt a = new BigInt("3 1 4 2 1");
    BigInt b = new BigInt("1 3 5 4 6");
    BigIntMul mul = new BigIntMul();
    mul.mulTest(new Unsigned(100), new Unsigned(100));
    //File koMulOptTimes = new File("/afs/inf.ed.ac.uk/user/s15/s1527764/IdeaProjects/Inf2B/koMulOptTimes.txt");
    //File fout = new File("/afs/inf.ed.ac.uk/user/s15/s1527764/IdeaProjects/Inf2B/fout.txt");
    //mul.getRunTimes(new Unsigned(1), new Unsigned(10), new Unsigned(90), koMulOptTimes, true );
    //mul.getRatios(new Unsigned(1), new Unsigned(10), new Unsigned(90), fout, new Unsigned(66) );
    //mul.plotRunTimes(0.003038973711103859, 0.001959784555028598, koMulOptTimes);
  }
} //end StudentCode class

