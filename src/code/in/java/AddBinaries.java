package code.in.java;

public class AddBinaries {

//    Input: a = "11", b = "1"
//    Output: "100"
//
//    1 + 1 = 10 → write 0, carry 1
//    1 + 1 + carry(1) = 11 → write 1, carry 1
//    1 + 1 + carry(1) = 11 → write 1, carry 1
//    1 + 1 + carry(1) = 11 → write 1, carry 1


    public static void main(String args[]){
        String a = "11";
        String b = "1";
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;

        while( i >=0 || j>=0){
            int sum = carry;
            if( i >= 0) {
                sum += a.charAt(i) - '0';
                i--;
            }
            if( j >= 0){
                sum += b.charAt(j) - '0';
                j--;
            }
            sb.append( sum % 2 );
            carry = (sum / 2);

        }
        if(carry != 0) sb.append(carry);
        System.out.println( sb.reverse().toString() );
    }
}
