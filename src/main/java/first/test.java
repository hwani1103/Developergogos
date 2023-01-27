package first;

public class test {
    public static void main(String[] args) {
/**
 * 내가생각한로직은.
 * index가 13인데 . pageNav가 11~20이라면. v%10을 하고
 * index가 13인데 pageNav가 11보다 작다면. navSize를 해라 이거면되겠다
 */


        int index = 13;
        int pageNav = 21;
        int x = index / 10 * 10;
        System.out.println((x < pageNav && pageNav <= x+10) );
//        for(int i=0; i<400; i++){
//            int index =  (int) Math.ceil(i / 20.0f);
//            System.out.print("i = " + i);
//            System.out.print(",   index = " + index);
//
////            System.out.println(" i / 20 = " + index);
//
//            System.out.println(",  index%10 = " + index%10);
//
//        }
    }
}