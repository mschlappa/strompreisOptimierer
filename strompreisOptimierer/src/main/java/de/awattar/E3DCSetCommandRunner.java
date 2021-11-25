package de.awattar;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
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
		
		ProcessBuilder builder = new ProcessBuilder().redirectErrorStream(true);
		
		try {

			if (isWindows) {
				System.out.println("Befehlszeile Windows:" + cmd);
			    builder.command("cmd.exe", "/c", cmd);
			} else {
				System.out.println("Befehlszeile Linux:" + cmd);
			    builder.command("sh", "-c", cmd);
			}
			
			Process process = builder.start();
			StreamGobbler streamGobbler =  new StreamGobbler(process.getInputStream(), System.out::println);
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.submit(streamGobbler);
			int exitCode = process.waitFor();
			System.out.println("exitCode=" + exitCode);
			assert exitCode == 0;
			executor.shutdown();
		
			
		} catch(Exception e) {
			throw new RuntimeException("Fehler beim Aufruf des Befehls:" + cmd, e);
		}


		
	}




	public void sperreSpeicherentladung() {
		execute(StrompreisOptimierer.cmdSperreSpeicherentladung);
	}




	public void erlaubeSpeicherentladung() {
		execute(StrompreisOptimierer.cmdErlaubeSpeicherentladung);		
	}
	
}
