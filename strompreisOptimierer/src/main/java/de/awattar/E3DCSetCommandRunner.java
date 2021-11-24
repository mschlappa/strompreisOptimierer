package de.awattar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class E3DCSetCommandRunner {

	private static class StreamGobbler implements Runnable {
	    private InputStream inputStream;
	    private Consumer<String> consumer;

	    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
	        this.inputStream = inputStream;
	        this.consumer = consumer;
	    }

	    @Override
	    public void run() {
	        new BufferedReader(new InputStreamReader(inputStream)).lines()
	          .forEach(consumer);
	    }
	}

	
	
	
	private void execute(String cmd) {

		boolean isWindows = System.getProperty("os.name")
				  .toLowerCase().startsWith("windows");
		
		String homeDirectory = System.getProperty("user.home");
		
		Process process;
		
		try {

			System.out.println("\nRufe Befehlszeile auf:");
			
			if (isWindows) {
			    process = Runtime.getRuntime().exec(String.format("cmd.exe /c %s", cmd));
			} else {
			    process = Runtime.getRuntime().exec(String.format("sh -c %s", cmd));
			}
			
			StreamGobbler streamGobbler =  new StreamGobbler(process.getInputStream(), System.out::println);
			Executors.newSingleThreadExecutor().submit(streamGobbler);
			
			int exitCode = process.waitFor();
			assert exitCode == 0;
		
			
		} catch(Exception e) {
			throw new RuntimeException("Fehler beim Aufruf von e3dcset", e);
		}


		
	}




	public void sperreSpeicherentladung() {
		execute(StrompreisOptimierer.cmdSperreSpeicherentladung);
	}




	public void erlaubeSpeicherentladung() {
		execute(StrompreisOptimierer.cmdErlaubeSpeicherentladung);		
	}
	
}
