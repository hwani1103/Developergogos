package first;

public class test {
    public static void main(String[] args) {
//        //여기서 600은 총 게시물 수.
        for (int i = 0; i < 600; i++) { //N은 총 게시물 수.
            int v = (int) Math.ceil(i / 20.0f); //20은 걍 pageSize.
            if (v % 10 != 0) { //10은 하드코딩
                System.out.println("i = " + i + ", v%10 = " +v % 10 ); //여기서.. navSize가. 게시물이 201개일때부터. 1이 되면 되는게 아니라!
                // pageNav가 11일때 1이되게 해보자 일단.
            } else
                System.out.println("i = " + i + "v = " +v );
        }
//        for(int i=0; i<600; i++){
//            int v = (int) Math.ceil(i / 20.0f); //20은 걍 pageSize.
//            System.out.println("i = " + i + ", v = " + v);
//        }


//        for(int i=0; i<20; i++){
//
//        int k = ((i - 1) / 10) * 10;  //
//            System.out.println("i = " + i + " 일 때 " + " (i-1)/10)*10 = " + k);
//        }int k = ((pageNav - 1) / 10) * 10;  //
//        System.out.println(((10-1)/10)*10);
//        System.out.println(((11-1)/10)*10);
//        //그래서 이제


//        System.out.println(155/20+1); 10까지 155/20+1 이 10일때까지
//        System.out.println((255/20+1)%10); 11부터. 255/20+1이 11부터는 뒤에 %10

        /**
         *
         * 1은 1 11은 1 21은 1
         * 2는 2 12는 2 22는 2
         * 3은 3 13은 3
         *       14는 4
         *       15는 5
         *       16은 6
         *       17은 7
         *       18은 8
         *       19는 9
         * 10은 10 20은 10 30은 10.
         *
         *
         * 0~20.... 까지는 1을 원하는데 나누기를하면 20부터 달라져버리넹.
         * 원하는 값은..
         * 총 게시물 나누기 20 했을때
         * 20이하는 1
         * 40이하는 2
         * 60이하는 3
         * 80이하는 4
         * 100이하는 5 를 원함.
         * 120이하
         * 140이하
         * 160이하
         * 180이하
         * 200이하
         * 220이하.
         */
    }
}
