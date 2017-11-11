package haidang.com.myappff;

import android.app.Application;

/**
 * Created by HaiDang_PC on 11/9/2017.
 */

class MyAppApplication extends Application {
    private String idfb;
    private String myLocal;
    private String namefb;

    public String getIdfb() {
        return idfb;
    }

    public void setIdfb(String idfb) {
        this.idfb = idfb;
    }

    public String getMyLocal() {
        return myLocal;
    }

    public void setMyLocal(String myLocal) {
        this.myLocal = myLocal;
    }

    public String getNamefb() {
        return namefb;
    }

    public void setNamefb(String namefb) {
        this.namefb = namefb;
    }
}
