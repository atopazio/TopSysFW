package br.com.topsys.util;

import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;

public class TSGoogleVoiceListener implements GSpeechResponseListener {
	
	private String response;
	
	@Override
	public void onResponse(GoogleResponse gResponse) {
		this.response = gResponse.getResponse();
		
	}

	public String getResponse() {
		return response;
	}
	
	

}
