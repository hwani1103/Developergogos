package first.gogos.domain;

import org.springframework.stereotype.Component;


@Component
public class Paging {

//    private int navSize = 10; // 10
    public int[] nav; // 1~10, 11~20 이렇게 초기화해서 네비게이션 표현할 배열
//    private int totalPost = 200; // 200
//    private int totalPage = totalPost / navSize ;


    public Paging() {}

    public void setNav(int[] nav) {
        this.nav = nav;
    }
    public int[] getNav() {
        return nav;
    }

}
