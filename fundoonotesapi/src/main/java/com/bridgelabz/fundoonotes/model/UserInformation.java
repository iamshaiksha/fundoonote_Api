package com.bridgelabz.fundoonotes.model;
/**
 * @author shaik shaiksha vali
 * 
 * */
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	@OneToMany(targetEntity =NoteInformation.class)
	@JoinColumn(name="userId")
	private List<NoteInformation> note;
	
	@OneToMany(targetEntity = LabelInformation.class)
	@JoinColumn(name="userId")
	private List<LabelInformation> lables;
	
	@ManyToMany
	private List<NoteInformation> collaboratorNote;


}
