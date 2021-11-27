package de.awattar;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class KommandozeilenArgumente {

	@Parameter
	private List<String> parameters = new ArrayList<>();

	@Parameter(names = { "-soc" }, required = true, description = "Ladezustand des Akkus in Prozent")
	private int soc;

	@Parameter(names = "-workdir", description = "Arbeitsverzeichnis")
	private String workdir = "./";

	@Parameter(names = "-debug", description = "Detaillierter Loglevel")
	private boolean debug = false;
	
	public int getSoc() {
		return soc;
	}

	public String getWorkdir() {
		return workdir;
	}

	public boolean isDebug() {
		return debug;
	}


}