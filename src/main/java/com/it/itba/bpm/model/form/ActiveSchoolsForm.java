package com.it.itba.bpm.model.form;

import java.util.List;

import com.it.itba.bpm.model.entry.ActiveSchoolEntry;

public class ActiveSchoolsForm {
	private String title;
	private List<ActiveSchoolEntry> entries;
	
	public ActiveSchoolsForm(String title, List<ActiveSchoolEntry> entries) {
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
	public List<ActiveSchoolEntry> getEntries() {
		return entries;
	}
	public void setEntries(List<ActiveSchoolEntry> entries) {
		this.entries = entries;
	}
}
