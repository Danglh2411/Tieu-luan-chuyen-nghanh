package haidang.com.myappff;

/**
 * Created by HaiDang_PC on 11/10/2017.
 */

public class Friend {
    int id;
    String userid1;
    String nameu1;
    String userid2;
    String nameu2;

    public Friend(int id, String userid1, String nauser1, String userid2, String nameu2) {
        this.id = id;
        this.userid1 = userid1;
        this.nameu1 = nauser1;
        this.userid2= userid2;
        this.nameu2 = nameu2;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid1() {
        return userid1;
    }

    public void setUserid1(String userid1) {
        this.userid1 = userid1;
    }

    public String getNameu1() {
        return nameu1;
    }

    public void setNameu1(String nameu1) {
        this.nameu1 = nameu1;
    }

    public String getUserid2() {
        return userid2;
    }

    public void setUserid2(String userid2) {
        this.userid2 = userid2;
    }

    public String getNameu2() {
        return nameu2;
    }

    public void setNameu2(String nameu2) {
        this.nameu2 = nameu2;
    }

}
