package com.it.itba.bpm.model.form;

import java.util.List;

import com.it.itba.bpm.model.entry.SchoolEntry;

public class ContactForm {

	private String title;
	private List<SchoolEntry> entries;

	public ContactForm(String title, List<SchoolEntry> entries) {
		super();
		this.title = title;
		this.entries = entries;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<SchoolEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<SchoolEntry> entries) {
		this.entries = entries;
	}
	
	
}
