package entity;
import cn.bmob.v3.BmobUser;

public class Myuser extends BmobUser {
    private boolean sex;


    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

}
