package haidang.com.myappff;

/**
 * Created by HaiDang_PC on 10/26/2017.
 */

public class User {
    String id;
    String name;
    String email;
    String phone;
    int stt;
    String photo;

    public User(String id, String name, String email, String phone, int stt, String photo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.stt = stt;
        this.photo = photo;

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
