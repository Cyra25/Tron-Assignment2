package cyra.trongame;

import java.io.IOException;
import java.net.*;

public class GameClient extends Thread{

    // IP address of the Server that we are connecting to
    private InetAddress serverIpAddress;

    // for socket
    private DatagramSocket socket;

    // to take any information from the game
    private Game game;

    public GameClient(Game game, String ipAddress){
        this.game = game;

        // for listening on a port, can add the port number as a parameter
        try {
            this.socket = new DatagramSocket();
            this.serverIpAddress = InetAddress.getByName(ipAddress);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            // receives data
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String message = new String(packet.getData());
            System.out.println("SERVER > " + message);
        }
    }

    // for sending data to server
    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, serverIpAddress, 1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
