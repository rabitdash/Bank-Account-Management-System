package NetWork;

import com.rabitdash.rabyte.Bank;
import com.rabitdash.rabyte.Exception.ATMException;
import com.rabitdash.rabyte.NetWork.TO.ActionEnum;
import com.rabitdash.rabyte.NetWork.TO.TO;
import com.rabitdash.rabyte.Util.ACCOUNT_TYPE;
import com.sun.istack.internal.NotNull;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private static final Bank bank = Bank.getInstance();
    private Socket socket = null;

    public ServerThread() {
    }

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        OutputStream out = null;
        InputStream in = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);

            TO sendMsg = null;
            TO receiveMsg = null;
            ActionEnum action = null;
            boolean exit = false;
            while (!Thread.interrupted() && !exit) {

                receiveMsg = (TO) ois.readObject();
                System.out.println("Server Receive Start: \n" + receiveMsg + "\nServer Receive End\n");
                if (receiveMsg.getAction() == ActionEnum.DISCONNECT) {
                    exit = true;
                    System.out.println("Exit Server");
                }
                sendMsg = performAction(receiveMsg);
                System.out.println("Server Send Start: \n" + sendMsg + "\nServer Send End\n");
                oos.writeObject(sendMsg);
                oos.reset();
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null)
                    oos.close();
                if (out != null)
                    out.close();
                if (ois != null)
                    ois.close();
                if (in != null)
                    in.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private TO performAction(TO msg) {
        @NotNull TO sendMsg;
        final ActionEnum RESPONSE = ActionEnum.RESPONSE;
        final ActionEnum ERROR = ActionEnum.EXCEPTION;
        String[] data;
        try {
            //同步锁
            synchronized (bank) {
                switch (msg.getAction()) {
                    case LOGIN:
                        data = (String[]) msg.getData();
                        sendMsg = new TO(RESPONSE,
                                bank.login(
                                        Long.valueOf(data[0]),
                                        String.valueOf(data[1])));
                        break;
                    case DEPOSIT:
                        data = (String[]) msg.getData();
                        sendMsg = new TO(RESPONSE,
                                bank.deposit(
                                        Long.valueOf(data[0]),
                                        Double.valueOf(data[1])));
                        break;
                    case REGISTER:
//                    String password, String name, String personId, String email, ACCOUNT_TYPE type
                        data = (String[]) msg.getData();
                        sendMsg = new TO(RESPONSE,
                                bank.register(
                                        String.valueOf(data[0]),
                                        String.valueOf(data[1]),
                                        String.valueOf(data[2]),
                                        String.valueOf(data[3]),
                                        ACCOUNT_TYPE.valueOf(data[4])
                                ));
                        break;
                    case WITHDRAW:
                        data = (String[]) msg.getData();
                        sendMsg = new TO(RESPONSE,
                                bank.withdraw(
                                        Long.valueOf(data[0]),
                                        Double.valueOf(data[1])));
                        break;
                    case TRANSFER:
                        data = (String[]) msg.getData();
                        sendMsg = new TO(RESPONSE,
                                bank.transfer(
                                        Long.valueOf(data[0]),
                                        Long.valueOf(data[1]),
                                        Double.valueOf(data[2])));
                        break;
                    case PAY_LOAN:
                        data = (String[]) msg.getData();
                        sendMsg = new TO(RESPONSE,
                                bank.payLoan(
                                        Long.valueOf(data[0]),
                                        Double.valueOf(data[1])));
                        break;
                    case REQUEST_LOAN:
                        data = (String[]) msg.getData();
                        sendMsg = new TO(RESPONSE,
                                bank.requestLoan(
                                        Long.valueOf(data[0]),
                                        Double.valueOf(data[1])));
                        break;
                    case SET_CEILING:
                        data = (String[]) msg.getData();
                        sendMsg = new TO(RESPONSE,
                                bank.setCeiling(
                                        Long.valueOf(data[0]),
                                        Double.valueOf(data[1])));
                        break;
                    case DEBUG:
                        sendMsg = new TO(ActionEnum.DEBUG, msg.getData());
                        break;
                    default:
                        sendMsg = new TO(ActionEnum.EXCEPTION, msg.getData());
                        break;
                }
            }
        } catch (ATMException e) {
            sendMsg = new TO(ERROR, e.getMessage());
            return sendMsg;
        }
        return sendMsg;
    }
}
