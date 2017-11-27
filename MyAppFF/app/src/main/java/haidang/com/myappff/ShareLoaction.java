package haidang.com.myappff;

/**
 * Created by HaiDang_PC on 11/10/2017.
 */

public class ShareLoaction {
    String userid1;
    String userid2;
    String nameu1;
    String Lati;
    String Longi;

    public ShareLoaction(String userid1, String nameu1, String userid2,String Lati,String Longi) {

        this.userid1 = userid1;
        this.userid2 = userid2;
        this.nameu1 = nameu1;
        this.Lati = Lati;
        this.Longi = Longi;

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

    public String getLati() {
        return Lati;
    }

    public void setLati(String lati) {
        Lati = lati;
    }

    public String getLongi() {
        return Longi;
    }

    public void setLongi(String longi) {
        Longi = longi;
    }
}
