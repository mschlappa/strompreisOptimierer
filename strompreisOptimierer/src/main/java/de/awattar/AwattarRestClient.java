package de.awattar;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class AwattarRestClient {

	private static final String URL =  "https://api.awattar.de/v1/marketdata";
	
	public List<PreisIntervall> getPreisIntervalle() {
		
        try {

            URL url = new URL(URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
                
            } else {

                String json = "";
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    json += scanner.nextLine();
                }

                scanner.close();
                
                json = JsonParser.parseString(json).getAsJsonObject().get("data").getAsJsonArray().toString();
                
                Gson gson = new GsonBuilder().create();
                
                return Arrays.asList(gson.fromJson(json, PreisIntervall[].class));
                
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
	}
}
