
package streamer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public class UdpReceiveAudio extends Thread {
	private final String grupoMc;
    private final int puerto;
    private final SourceDataLine speakers;
    private final int bufferSize;
    
    public UdpReceiveAudio(String grupoMc, int puerto, SourceDataLine speakers, int bufferSize) {
    	this.grupoMc = grupoMc;
        this.puerto = puerto;    	
        this.speakers = speakers;
        this.bufferSize = bufferSize;
    }

    @Override
    public void run() {
        //Captura los segmentos de audio enviados por el sistema remoto y los envía a los speakers
        try {
            MulticastSocket socket = new MulticastSocket(puerto);
            InetAddress mcGroupIp = InetAddress.getByName(grupoMc);
            socket.joinGroup(mcGroupIp);
            
            byte[] buffer = new byte[bufferSize];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            
            while (true) {
                socket.receive(packet);
                speakers.write(packet.getData(), 0, packet.getData().length);
                System.out.println("Recibido segmento: " + packet.getLength());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
