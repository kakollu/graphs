package com.kakollu;

import java.util.Iterator;

public class BitPartition {

    public static int getStartEnd(int m, int n, boolean begin) {
        int start = 0;
        int end = 0;
        for (int x=0; x<m; x++) {
            start |= 1<<x;
            end |= 1<<((n-2)-x);
        }
        end = end + 1;
        return begin? start:end;
    }

    public static int next(int s) {
        int t = s | (s -1); // see comment below
        s = (t + 1) | (((~t & -~t) - 1) >> (Integer.numberOfTrailingZeros(s) + 1));
        return s;
    }

    public static void loops() {
        /*
    Let A = 2D array indexed by subsets S of {1,2,..n} that  contain 1 and destinations j in {1, 2, .. n}

    Base case A[S,1] = 0 if S  = {1} , +inf otherwise

    For m = 2,3,4,..,n ; m is sub problem size
      For each subset S of {1,2,..,n} of size m that contain 1
        For each j in S but j!=1
          A[S,j] = min k in S, k!=j { A[S-{j},k] + C[k,j] }
    return min j = 2, n { A[{1,2,..n}, 1] + C[j, 1] }
     */

        int n = 5;
        // For m = 2,3,4,..,n ; m is sub problem size
        for (int m=1; m<n; m++) { // zero index
            System.out.println("sub problem size m:"+(m+1));
            // For each subset S of {1,2,..,n} of size m that contain 1
            int start = getStartEnd(m, n, true);
            int end = getStartEnd(m,n,false);
            System.out.println("start: "+ Integer.toString(start,2));
            System.out.println("end: "+Integer.toString(end,2));
            int val = start;
            while (val < end) {
                System.out.println("val:"+Integer.toString(val,2));
                val = next(val);
            }

            /*
            sub problem size m:2
            start: 1
            end: 10000
            val:1
            val:10
            val:100
            val:1000
            // add implicit 1 at the end ..
            11, 101, 1001, 10001
            4C1 = 4

            sub problem size m:3
            start: 11
            end: 11000
            val:11
            val:101
            val:110
            val:1001
            val:1010
            val:1100
            val:10001
            val:10010
            val:10100
            // add implicit 1 at the end ..
            111
            1011
            1101
            10011
            10101
            11001
            100011
            100101
            101001

            1111, 10111, 11011, 100111, 101011, 110011, 1000111, 1001011, 1010011
            4C2 = 6
             */
        }
    }
    public static void main(String[] args) {
        // test1();
        loops();
    }

    private static void test1() {
        int n = 6;
        for (int m=1; m<3; m++) {
            int start = getStartEnd(m,n,true);
            int end = getStartEnd(m,n,false);

            System.out.println("start: "+ Integer.toString(start,2));
            System.out.println("end: "+Integer.toString(end,2));

            int val = start;
            while (val < end) {
                System.out.println("val:"+Integer.toString(val,2));
                val = next(val);
            }
        }
    }

        /*
http://stackoverflow.com/questions/1851134/generate-all-binary-strings-of-length-n-with-k-bits-set
Compute the lexicographically next bit permutation

Suppose we have a pattern of N bits set to 1 in an integer and we want the next permutation of N 1 bits in a lexicographical sense. For example, if N is 3 and the bit pattern is 00010011, the next patterns would be 00010101, 00010110, 00011001, 00011010, 00011100, 00100011, and so forth. The following is a fast way to compute the next permutation.

unsigned int v; // current permutation of bits
unsigned int w; // next permutation of bits

unsigned int t = v | (v - 1); // t gets v's least significant 0 bits set to 1
// Next set to 1 the most significant bit to change,
// set to 0 the least significant ones, and add the necessary 1 bits.
w = (t + 1) | (((~t & -~t) - 1) >> (__builtin_ctz(v) + 1));

The __builtin_ctz(v) GNU C compiler intrinsic for x86 CPUs returns the number of trailing zeros. If you are using Microsoft compilers for x86, the intrinsic is _BitScanForward. These both emit a bsf instruction, but equivalents may be available for other architectures. If not, then consider using one of the methods for counting the consecutive zero bits mentioned earlier. Here is another version that tends to be slower because of its division operator, but it does not require counting the trailing zeros.

unsigned int t = (v | (v - 1)) + 1;
w = t | ((((t & -t) / (v & -v)) >> 1) - 1);

Thanks to Dario Sneidermanis of Argentina, who provided this on November 28, 2009.
     */

}
