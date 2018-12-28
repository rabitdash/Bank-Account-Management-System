package com.rabitdash.rabyte.NetWork;

import com.rabitdash.rabyte.Exception.ATMException;
import com.rabitdash.rabyte.NetWork.TO.ActionEnum;
import com.rabitdash.rabyte.NetWork.TO.TO;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket = null;
    private InputStream in = null;

    private OutputStream out = null;

    private ObjectOutputStream oos = null;

    private ObjectInputStream ois = null;

    public Client() {
    }

    //TODO事件触发式7
    public Client(Socket socket) {
        this.socket = socket;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean send(ActionEnum actionEnum, Object data) {
        return sendMsg(new TO(actionEnum, data));
    }

    public boolean send(ActionEnum action, @NotNull String... data) {
        String[] stringData = new String[data.length];
        int i = 0;
        for (String string : data) {
            stringData[i] = string;
            i++;
        }
        return sendMsg(new TO(action, stringData));
    }

    public boolean sendMsg(TO msg) {
        boolean success = false;
        try {
            oos.writeObject(msg);
            oos.reset();
            oos.flush();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public TO receiveMsg() {
        TO to = null;
        try {
            to = (TO) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return to;
    }

    //会接受Server的异常信息
    public Object receive() throws ATMException {
        TO msg = receiveMsg();
        if (msg.getAction() == ActionEnum.RESPONSE)
            return msg.getData();
        else if (msg.getAction() == ActionEnum.EXCEPTION)
            throw new ATMException((String) msg.getData());
        return msg.getData();
    }
}
