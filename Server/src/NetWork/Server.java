package NetWork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket ss;
        Socket socket = null;
        try {
            ss = new ServerSocket(9000);
            System.out.println("Server start");
            //TODO
            while (true) {
                socket = ss.accept();
                System.out.println("from " + socket.getInetAddress() + " connected +1");
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
