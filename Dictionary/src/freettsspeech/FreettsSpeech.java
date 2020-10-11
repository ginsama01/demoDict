package freettsspeech;
// Text to speech sử dụng thư viện freetts của java

import com.sun.speech.freetts.VoiceManager;

public class FreettsSpeech {
    /**
     * Phát âm một từ.
     * @param text từ cần phát âm
     */
    public static void speech(String text) {
        VoiceManager voiceManager = VoiceManager.getInstance();
        com.sun.speech.freetts.Voice syntheticVoice = voiceManager.getVoice("kevin16");
        syntheticVoice.allocate();
        syntheticVoice.speak(text);
        syntheticVoice.deallocate();
    }
}
