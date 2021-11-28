package de.awattar;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class KommandozeilenArgumente {

	@Parameter
	private List<String> parameters = new ArrayList<>();

	@Parameter(names = "-soc" , required = true, description = "Ladezustand des Akkus in Prozent (ganzzahlig)")
	private int soc = 100;

	@Parameter(names = "-workdir", description = "Angabe des Arbeitsverzeichnisses")
	private String workdir = "./";

	@Parameter(names = "-debug", description = "Ausgabe zusaetzlicher Infos")
	private boolean debug = false;
	
	@Parameter(names = "-dry", description = "Befehl zur Speichersteuerung wird nur auf Konsole ausgegeben")
	private boolean dryRun = false;
	
	@Parameter(names = "-help", help = true)
    private boolean help = false;
	
	public boolean isHelp() {
	    return help;
	 }
	
	public int getSoc() {
		return soc;
	}

	public String getWorkdir() {
		return workdir;
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean isDryRun() {
		return dryRun;
	}

	
}