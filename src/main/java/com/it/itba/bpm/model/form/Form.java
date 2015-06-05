package com.it.itba.bpm.model.form;

import java.util.List;

import com.it.itba.bpm.model.entry.FormEntry;

public abstract class Form {

	private String title;

	public Form() {
	}

	public Form(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public abstract List<FormEntry> getEntries();
		
	
}
