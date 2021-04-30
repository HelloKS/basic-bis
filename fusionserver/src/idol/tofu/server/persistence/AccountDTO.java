package idol.tofu.server.persistence;

import java.io.Serializable;
import java.sql.Date;


public class AccountDTO implements Serializable {
    private String id;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private Date birth; // 사용자 생년월일
    private String isAdmin; // 사용자 관리자 여부 F:일반유저 T:관리자
    public AccountDTO() {}
    public AccountDTO(String id, String password, String name, String nickname, String phone, String email, Date birth, String isAdmin) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.birth = birth;
        this.isAdmin = isAdmin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return id + ',' +
                password + ',' +
                name + ',' +
                nickname + ',' +
                phone + ',' +
                email + ',' +
                birth + ',' +
                isAdmin + ',';
    }
}
