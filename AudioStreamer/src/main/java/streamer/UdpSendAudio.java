package streamer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
*
* @author dordonez@ute.edu.ec
*/
public class UdpSendAudio extends Thread {
    private final BlockingQueue<byte[]> audioQueue;
    private final String host;
    private final int puerto;

    public UdpSendAudio(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
        audioQueue = new LinkedBlockingQueue<>();
    }
    
    @Override
    public void run() {
        //Recupera los segmentos de audio que se encuentran en la cola (audioQueue) y los envía al cliente remoto
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress direccion = InetAddress.getByName(host);
            DatagramPacket packet;
            byte[] buffer;
            
            while (true) {  
                buffer = audioQueue.take();
                packet = new DatagramPacket(buffer, buffer.length, direccion, puerto);
                socket.send(packet);
                System.out.println("Segmento enviado: " + buffer.length);
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    //Alguien más debe llamar este método y poner los segmentos de audio en la cola
    public void queueAudio(byte[] buffer) {
        try {
            audioQueue.put(buffer);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
