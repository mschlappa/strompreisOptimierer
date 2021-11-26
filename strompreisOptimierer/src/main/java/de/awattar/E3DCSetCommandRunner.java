package de.awattar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class E3DCSetCommandRunner {

	private static final Logger logger = LoggerFactory.getLogger(E3DCSetCommandRunner.class);
	
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
				logger.debug("Befehlszeile Windows:" + cmd);
			    builder.command("cmd.exe", "/c", cmd);
			} else {
				logger.debug("Befehlszeile Linux:" + cmd);
			    builder.command("sh", "-c", cmd);
			}
			
			Process process = builder.start();
			StreamGobbler streamGobbler =  new StreamGobbler(process.getInputStream(), System.out::println);
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.submit(streamGobbler);
			int exitCode = process.waitFor();
			logger.debug("exitCode=" + exitCode);
			assert exitCode == 0;
			executor.shutdown();
		
			
		} catch(Exception e) {
			throw new RuntimeException("Fehler beim Aufruf des Befehls:" + cmd, e);
		}


		
	}


	public void sperreSpeicherentladung() {
		execute(PropertiesHelper.getBefehlEntladungSperren());
	}

	
	public void erlaubeSpeicherentladung() {
		execute(PropertiesHelper.getBefehlEntladungEntsperren());		
	}
	
}
