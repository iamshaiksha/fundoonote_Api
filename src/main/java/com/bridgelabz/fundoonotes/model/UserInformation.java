package com.bridgelabz.fundoonotes.model;
import java.net.URL;
/**
 * @author shaik shaiksha vali
 * 
 * */
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "UserInformation")
public class UserInformation {
	@Id
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long userId;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private String mobileNumber;
	@Column
	private int isVerified;
	@Column
	private LocalDateTime dateTime;
	@Column
	private String profile;
	private String fileType;
//	private URL url;
//	public URL getUrl() {
//		return url;
//	}
//
//	public void setUrl(URL url) {
//		this.url = url;
//	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String fileName) {
		this.profile = fileName;
	}

	@OneToMany(targetEntity =NoteInformation.class)
	@JoinColumn(name="userId")
	private List<NoteInformation> note;
	
	@OneToMany(targetEntity = LabelInformation.class)
	@JoinColumn(name="userId")
	private List<LabelInformation> lables;
	
	@ManyToMany
	private List<NoteInformation> collaboratorNote;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(int isVerified) {
		this.isVerified = isVerified;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public List<NoteInformation> getNote() {
		return note;
	}

	public void setNote(List<NoteInformation> note) {
		this.note = note;
	}

	public List<LabelInformation> getLables() {
		return lables;
	}

	public void setLables(List<LabelInformation> lables) {
		this.lables = lables;
	}

	public List<NoteInformation> getCollaboratorNote() {
		return collaboratorNote;
	}

	public void setCollaboratorNote(List<NoteInformation> collaboratorNote) {
		this.collaboratorNote = collaboratorNote;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	


}
