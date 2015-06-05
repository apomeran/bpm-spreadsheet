package com.it.itba.bpm.model.form;

import java.util.List;

import com.it.itba.bpm.model.entry.FormEntry;

public class ActiveSchoolsForm extends Form {
	private List<FormEntry> entries;

	public ActiveSchoolsForm(String title, List<FormEntry> entries) {
		super();
		this.entries = entries;
	}

	public List<FormEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<FormEntry> entries) {
		this.entries = entries;
	}

}
