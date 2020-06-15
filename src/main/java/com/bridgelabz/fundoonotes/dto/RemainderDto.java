/**
 * 
 */
package com.bridgelabz.fundoonotes.dto;

/**
 * @author shaik shaiksha vali
 *
 */
import java.time.LocalDateTime;
public class RemainderDto {

	 private long noteId;
	private String remainder;
	public long getNoteId() {
		return noteId;
	}
	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}
	public String getRemainder() {
		return remainder;
	}
	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}

}