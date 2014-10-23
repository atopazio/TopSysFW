package br.com.topsys.util;

import java.io.File;
import javaFlacEncoder.FLACFileWriter;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;

public final class TSGoogleVoice {
	
	private final Microphone microfone;
    private File file;
    private Recognizer recognizer;
    private GoogleResponse response;

    public TSGoogleVoice() {
        microfone = new Microphone(FLACFileWriter.FLAC);
    }

    public void start() throws TSApplicationException {
        file = new File("hosflow.flac");
        try {
            microfone.captureAudioToFile(file);
        } catch (Exception ex) {
            throw new TSApplicationException(ex);// Microfone não está ativo
        }

    }

    public String stop() {
        String retorno = null;

        try {
            microfone.close();
            
            Runtime.getRuntime().exec("curl -X POST --data-binary @'hosflow.wav' --header 'Content-Type: audio/l16; rate=16000;' 'https://www.google.com/speech-api/v2/recognize?output=json&lang=pt-BR&key=AIzaSyCONXcEXBH8GdOkt5lxH96EBjMikR8vrsk' >> teste.txt");            
           // recognizer = new Recognizer(Recognizer.Languages.PORTUGUESE_BRASIL);

            //response = recognizer.getRecognizedDataForFlac(file, 1);

            
            
            //retorno = response.getResponse();

        } catch (Exception ex) {
            throw new TSSystemException(ex);
        } finally {
            file.deleteOnExit();
        }

        return retorno;

    }

}
