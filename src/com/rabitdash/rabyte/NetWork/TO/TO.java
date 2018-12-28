package com.rabitdash.rabyte.NetWork.TO;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class TO implements Serializable {

    private ActionEnum action = null;
    private Object data;

    //根据action不同，Object将被转型为各种对象进行操作
    public TO(ActionEnum action, Object data) {
        this.action = action;
        this.data = data;
    }

    public TO(ActionEnum action, @NotNull String... data) {
        this.action = action;
        String[] stringData = new String[data.length];
        int i = 0;
        for (String string : data) {
            stringData[i] = string;
            i++;
        }
        this.data = stringData;
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
        return action.toString() + " " + data.toString();
    }
}