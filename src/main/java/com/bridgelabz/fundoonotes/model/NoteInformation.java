package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * @author shaik shaiksha vali
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteInformation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long noteId;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private int isArchieved;
	@Column
	private int isPinned;
	@Column
	private int isTrashed;
	@Column
	private LocalDateTime createDateTime;
	@Column
	private LocalDateTime upDateTime;
	@Column
	private String reminder;
	@Column
	private String color;
	// we've set the fetch to EAGER, so that when we retrieve a notes from the database, we'll also automatically retrieve all of its corresponding labels.
	@JoinTable(name = "note_labels",joinColumns = {@JoinColumn(name = "labelId")},inverseJoinColumns = {@JoinColumn(name = "noteId")})
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	private List<LabelInformation> labelList;
	public Long getNoteId() {
		return noteId;
	}
	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIsArchieved() {
		return isArchieved;
	}
	public void setIsArchieved(int isArchieved) {
		this.isArchieved = isArchieved;
	}
	public int getIsPinned() {
		return isPinned;
	}
	public void setIsPinned(int isPinned) {
		this.isPinned = isPinned;
	}
	public int getIsTrashed() {
		return isTrashed;
	}
	public void setIsTrashed(int i) {
		this.isTrashed = i;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
	public LocalDateTime getUpDateTime() {
		return upDateTime;
	}
	public void setUpDateTime(LocalDateTime upDateTime) {
		this.upDateTime = upDateTime;
	}
	public String getReminder() {
		return reminder;
	}
	public void setReminder(String string) {
		this.reminder = string;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<LabelInformation> getLabelList() {
		return labelList;
	}
	public void setLabelList(List<LabelInformation> labelList) {
		this.labelList = labelList;
	}
	public int isTrashed() {
		return isTrashed;
	}

	
	
		
}