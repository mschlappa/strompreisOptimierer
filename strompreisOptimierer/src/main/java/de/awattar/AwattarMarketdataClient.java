package de.awattar;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class AwattarMarketdataClient {
	
	private static final Logger logger = LoggerFactory.getLogger(AwattarMarketdataClient.class);

	private static final String FILE = StrompreisOptimierer.WORK_DIR + "marketdata";

	private List<PreisIntervall> getPreisIntervalleViaRestService() {

		try {

			URL url = new URL(PropertiesHelper.getAwattarApiURL());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			int responsecode = conn.getResponseCode();

			if (responsecode != 200) {
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			} else {

				Scanner scanner = new Scanner(url.openStream());

				return konvertiereJsonNachObjekt(scanner);
			}

		} catch (Exception e) {
			throw new RuntimeException("Preisintervalle konnten nicht via Rest eingelesen werden", e);
		}
	}

	private List<PreisIntervall> getPreisIntervalleViaDatei() {

		try {
			return konvertiereJsonNachObjekt(new Scanner(new File(FILE)));

		} catch (FileNotFoundException e) {
			throw new RuntimeException("Preisintervalle konnten nicht via Datei eingelesen werden", e);
		}

	}

	private List<PreisIntervall> konvertiereJsonNachObjekt(Scanner scanner) {

		String json = "";

		while (scanner.hasNext()) {
			json += scanner.nextLine();
		}

		scanner.close();

		json = JsonParser.parseString(json).getAsJsonObject().get("data").getAsJsonArray().toString();

		Gson gson = new GsonBuilder().create();

		return Arrays.asList(gson.fromJson(json, PreisIntervall[].class));

	}

	public List<PreisIntervall> getPreisIntervalle() {

		 List<PreisIntervall> liste = new ArrayList<>();
		
		if (PropertiesHelper.getPreisIntervalleViaDateiEinlesen()) {
			logger.debug("Lese Preisintervalle ueber Datei ein");
			liste = getPreisIntervalleViaDatei();
		} else {
			logger.debug("Lese Preisintervalle ueber Rest-Service ein");
			liste = getPreisIntervalleViaRestService();	
		}

		return liste;
		
	}
}
