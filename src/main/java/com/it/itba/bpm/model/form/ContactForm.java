package com.it.itba.bpm.model.form;

import com.it.itba.bpm.model.entry.SchoolEntry;

import java.util.List;

public class ContactForm 
{

	private List<SchoolEntry> entries;
	private String title;

	public ContactForm(String title, List<SchoolEntry> entries) {
		super();
		this.title = title;
		this.entries = entries;
	}

	public List<SchoolEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<SchoolEntry> entries) {
		this.entries = entries;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



}
