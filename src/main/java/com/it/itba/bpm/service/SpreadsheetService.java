package com.it.itba.bpm.service;

import java.util.List;

public class SpreadsheetService {

	private List<String> keyForms;

	public SpreadsheetService(List<String> keyForms) {
		this.keyForms = keyForms;
	}

	public static SpreadsheetService build(List<String> keyForms) {

		SpreadsheetService s = new SpreadsheetService(keyForms);
		return s;
	}

	// PUT THE CRUD METHODS	 HERE
	
	public List<String> getKeyForms() {
		return keyForms;
	}

	public void setKeyForms(List<String> keyForms) {
		this.keyForms = keyForms;
	}

}
