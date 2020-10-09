package freettsspeech;

import com.sun.speech.freetts.VoiceManager;

public class FreettsSpeech {
    public static void speech(String text) {
        VoiceManager voiceManager = VoiceManager.getInstance();
        com.sun.speech.freetts.Voice syntheticVoice = voiceManager.getVoice("kevin16");
        syntheticVoice.allocate();
        syntheticVoice.speak(text);
        syntheticVoice.deallocate();
    }
}
