/**
 * 
 */
package com.bridgelabz.fundoonotes.dto;

/**
 * @author shaik shaiksha vali
 *
 */
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RemainderDto {


	private LocalDateTime remainder;

}