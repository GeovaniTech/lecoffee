package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.faces.application.FacesMessage;

import org.json.JSONObject;

public class CepUtil {
	public static JSONObject getCEPInformations(String cep) {
		try {
			URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			
			String line;
			
			while((line = reader.readLine()) != null) {
				response.append(line);
			}
			
			reader.close();
			connection.disconnect();
			
			return new JSONObject(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("error_get_cep_informations"), cep, FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}
}
