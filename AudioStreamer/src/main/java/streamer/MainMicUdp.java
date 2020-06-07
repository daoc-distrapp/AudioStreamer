package streamer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class MainMicUdp {

	public static void main(String[] args) {	
		TargetDataLine mic = initMic();

		UdpSendAudio udpSend = new UdpSendAudio(Constants.UDP_HOST, Constants.UDP_PORT);
		udpSend.start();
		byte[] buffer = new byte[Constants.BUFFER_SIZE];
		
		while (true) {
			mic.read(buffer, 0, buffer.length);
			udpSend.queueAudio(buffer);
		}
	}
	
	private static TargetDataLine initMic() {
		TargetDataLine mic = null;//captura de audio (micrófono)

		AudioFormat format = new AudioFormat(
				Constants.sampleRate,
				Constants.sampleSizeInBits,
				Constants.channels,
				Constants.signed,
				Constants.bigEndian);
		
		Info info = new Info(TargetDataLine.class, format);
		try {		
			mic = (TargetDataLine) AudioSystem.getLine(info);
			mic.open(format);
			mic.start();
			return mic;
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
}