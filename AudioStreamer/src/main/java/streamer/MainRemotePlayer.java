package streamer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class MainRemotePlayer {

	public static void main(String args[]) throws Exception {
		SourceDataLine speakers = initSpeakers();
		
		UdpReceiveAudio udpReceive = new UdpReceiveAudio(Constants.UDP_MC_IP, Constants.UDP_PORT, speakers, Constants.BUFFER_SIZE);
		udpReceive.start();
	}

	private static SourceDataLine initSpeakers() {
		SourceDataLine speakers = null;//playback de audio (parlantes)
		
		AudioFormat format = new AudioFormat(
				Constants.sampleRate,
				Constants.sampleSizeInBits,
				Constants.channels,
				Constants.signed,
				Constants.bigEndian);
		
		Info info = new Info(SourceDataLine.class, format);		
		try {
			speakers = (SourceDataLine) AudioSystem.getLine(info);
			speakers.open(format);
			speakers.start();
			return speakers;
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;		
	}

}