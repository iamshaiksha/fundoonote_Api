
package com.bridgelabz.fundoonotes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LabelInformation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long labelId;
	private String name;
	public Long getLabelId() {
		return labelId;
	}
	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	@ManyToMany(mappedBy="labelList",fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	@JsonIgnore
	private List<NoteInformation> noteList;
	public List<NoteInformation> getNoteList() {
		return noteList;
	}
	public void setNoteList(List<NoteInformation> noteList) {
		this.noteList = noteList;
	}
	
}