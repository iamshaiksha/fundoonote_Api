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
	private LocalDateTime reminder;
	@Column
	private String color;
	// we've set the fetch to EAGER, so that when we retrieve a notes from the database, we'll also automatically retrieve all of its corresponding labels.
	@JoinTable(name = "note_labels",joinColumns = {@JoinColumn(name = "labelId")},inverseJoinColumns = {@JoinColumn(name = "noteId")})
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	private List<LabelInformation> labelList;
		
}