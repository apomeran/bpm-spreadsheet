package com.it.itba.bpm.model.entry;

import com.google.gdata.data.DateTime;
import com.it.itba.bpm.model.Status;

import java.io.Serializable;

public class SchoolEntry extends FormEntry implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String comments;
	
	// Contact
	private String contact;
	private String email;
	private DateTime firstDirectiveMeeting;
	private String map;
	private String meetingComments;
	private String mode;
	private String neighborhood;
	private DateTime phoneContactDate;
	private Boolean phoneContacted;
	private Boolean presentationEmailSent;
	private String schoolName;
	private Status status;
	private String statusComments;
	// Description
	private String telephone;
	private String volunteer;
	// Location
	private String zone;

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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DateTime getFirstDirectiveMeeting() {
		return firstDirectiveMeeting;
	}

	public void setFirstDirectiveMeeting(DateTime firstDirectiveMeeting) {
		this.firstDirectiveMeeting = firstDirectiveMeeting;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getMeetingComments() {
		return meetingComments;
	}

	public void setMeetingComments(String meetingComments) {
		this.meetingComments = meetingComments;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public DateTime getPhoneContactDate() {
		return phoneContactDate;
	}

	public void setPhoneContactDate(DateTime phoneContactDate) {
		this.phoneContactDate = phoneContactDate;
	}

	public Boolean getPhoneContacted() {
		return phoneContacted;
	}

	public void setPhoneContacted(Boolean phoneContacted) {
		this.phoneContacted = phoneContacted;
	}

	public Boolean getPresentationEmailSent() {
		return presentationEmailSent;
	}

	public void setPresentationEmailSent(Boolean presentationEmailSent) {
		this.presentationEmailSent = presentationEmailSent;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(String volunteer) {
		this.volunteer = volunteer;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
}
