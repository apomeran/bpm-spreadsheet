package com.it.itba.bpm.model.entry;

import java.io.Serializable;

import com.google.gdata.data.DateTime;
import com.it.itba.bpm.model.Status;

public class SchoolEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private String schoolName;

	// Location
	private String zone;
	private String neighborhood;

	// Description
	private String telephone;
	private String email;

	// Contact
	private String contact;

	private String mode;
	private String map;
	private String volunteer;

	private Boolean presentationEmailSent;
	private Boolean phoneContacted;
	private DateTime phoneContactDate;
	private String comments;

	private DateTime firstDirectiveMeeting;
	private String meetingComments;

	private Status status;
	private String statusComments;

	public SchoolEntry(String schoolName, String zone, String neighborhood,
			String telephone, String email, String contact, String mode,
			String map, String volunteer, Boolean presentationEmailSent,
			Boolean phoneContacted, DateTime phoneContactDate, String comments,
			DateTime firstDirectiveMeeting, String meetingComments,
			Status status, String statusComments) {
		super();
		this.schoolName = schoolName;
		this.zone = zone;
		this.neighborhood = neighborhood;
		this.telephone = telephone;
		this.email = email;
		this.contact = contact;
		this.mode = mode;
		this.map = map;
		this.volunteer = volunteer;
		this.presentationEmailSent = presentationEmailSent;
		this.phoneContacted = phoneContacted;
		this.phoneContactDate = phoneContactDate;
		this.comments = comments;
		this.firstDirectiveMeeting = firstDirectiveMeeting;
		this.meetingComments = meetingComments;
		this.status = status;
		this.statusComments = statusComments;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(String volunteer) {
		this.volunteer = volunteer;
	}

	public Boolean getPresentationEmailSent() {
		return presentationEmailSent;
	}

	public void setPresentationEmailSent(Boolean presentationEmailSent) {
		this.presentationEmailSent = presentationEmailSent;
	}

	public Boolean getPhoneContacted() {
		return phoneContacted;
	}

	public void setPhoneContacted(Boolean phoneContacted) {
		this.phoneContacted = phoneContacted;
	}

	public DateTime getPhoneContactDate() {
		return phoneContactDate;
	}

	public void setPhoneContactDate(DateTime phoneContactDate) {
		this.phoneContactDate = phoneContactDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public DateTime getFirstDirectiveMeeting() {
		return firstDirectiveMeeting;
	}

	public void setFirstDirectiveMeeting(DateTime firstDirectiveMeeting) {
		this.firstDirectiveMeeting = firstDirectiveMeeting;
	}

	public String getMeetingComments() {
		return meetingComments;
	}

	public void setMeetingComments(String meetingComments) {
		this.meetingComments = meetingComments;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getStatusComments() {
		return statusComments;
	}

	public void setStatusComments(String statusComments) {
		this.statusComments = statusComments;
	}
}
