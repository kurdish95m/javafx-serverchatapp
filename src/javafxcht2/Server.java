
package javafxcht2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.scene.layout.VBox;
public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(ServerSocket serverSocket) {
        try {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("error from Server");
            closeEveryThing(socket, bufferedReader, bufferedWriter);

        }
    }

    public void closeEveryThing(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("error from closeEveryThing");
        }
    }

    public void receiveMessageFromClient(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String messageFromClient = bufferedReader.readLine();
                        ServerController.addLable(messageFromClient, vBox);
                    } catch (IOException ex) {
                        System.out.println(ex);
                        System.out.println("error from receiveMessageFromClient");
                        closeEveryThing(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void sendMessageToClient(String messageToClient) {
        try {
            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("error from sendMessageToClient");
            closeEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }
}
