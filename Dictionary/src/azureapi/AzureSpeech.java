package azureapi;

/**
 * Sử dụng API Speech của Azure, code dựa vào source code hướng dẫn trong
 * forum docs.microsoft, đã được sửa theo ý hiểu và cách dùng của nhóm.
 * KEY của thành viên nhóm, free account nên có limit.
 */

import com.microsoft.cognitiveservices.speech.*;

/**
 * Quickstart: synthesize speech using the Speech SDK for Java.
 */
public class AzureSpeech {
    private static String speechSubscriptionKey = "3f40070cff6b49489b9d637252564558";
    private static String serviceRegion = "eastus";

    public static String getSsml(String language, String name, String text) {
        if (!language.equals("vi-VN")) {
            return "<speak version=\"1.0\" xmlns=\"https://www.w3.org/2001/10/synthesis\" xml:lang=\""+ language + "\">\n" +
                    "  <voice name=\"" + name + "\">\n" + text + "\n" +
                    "  </voice>\n" +
                    "</speak>";
        } else {
            return "<speak version=\"1.0\" xmlns=\"https://www.w3.org/2001/10/synthesis\" xml:lang=\""+ language + "\">\n" +
                    "  <voice name=\"" + name + "\">\n" +
                    "<prosody rate=\"0.8\">" + text + "\n</prosody>\n" +
                    "  </voice>\n" +
                    "</speak>";
        }
    }

    public static void textToSpeech (String language, String name, String text) {
        try {

            SpeechConfig config = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion);

            SpeechSynthesizer synth = new SpeechSynthesizer(config);

            SpeechSynthesisResult result = synth.SpeakSsml(getSsml(language, name, text));

            if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                System.out.println("Speech synthesized to speaker for text [" + text + "]");
            }
            else if (result.getReason() == ResultReason.Canceled) {
                SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(result);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you update the subscription info?");
                }
            }

            result.close();
            synth.close();

        } catch (Exception ex) {
            System.out.println("Unexpected exception: " + ex.getMessage());
        }
    }

}

