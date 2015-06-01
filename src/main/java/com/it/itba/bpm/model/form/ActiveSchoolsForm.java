package com.it.itba.bpm.model.form;

import com.it.itba.bpm.model.entry.ActiveSchoolEntry;

import java.util.List;

public class ActiveSchoolsForm
{
	private List<ActiveSchoolEntry> entries;
	private String title;

	public ActiveSchoolsForm(String title, List<ActiveSchoolEntry> entries) {
		super();
		this.title = title;
		this.entries = entries;
	}

	public List<ActiveSchoolEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<ActiveSchoolEntry> entries) {
		this.entries = entries;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
