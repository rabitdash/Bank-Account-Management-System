package NetWork.TO;

import java.io.Serializable;

public class TO implements Serializable {

    private ActionEnum action = null;
    private Object data;

    //根据action不同，Object将被转型为各种对象进行操作
    public TO(ActionEnum action, Object data) {
        this.action = action;
        this.data = data;
    }

    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "\nAction:" + action.toString() + "data:\n" + data;
    }
}