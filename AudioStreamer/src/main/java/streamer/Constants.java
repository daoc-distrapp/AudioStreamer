package streamer;

public class Constants {
    public static final float sampleRate = 44100f;
    public static final int sampleSizeInBits = 16;
    public static final int channels = 2;
    public static final boolean signed = true;
    public static final boolean bigEndian = false;
    
    public static final String UDP_HOST = "localhost";
    public static final String UDP_MC_IP = "224.0.0.2";
    public static final int UDP_PORT = 8889;
    public static final int BUFFER_SIZE = 4 * 1024;

}
