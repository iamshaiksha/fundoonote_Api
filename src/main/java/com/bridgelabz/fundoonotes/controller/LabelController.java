/**
 * 
 */
package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelNote;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.model.LabelInformation;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UserResponse;
import com.bridgelabz.fundoonotes.service.LabelService;

/**
 * @author shaik shaiksha vali
 *
 */
@RestController
@PropertySource("classpath:Message.properties")
@RequestMapping("/labels")
public class LabelController {
	/*Api for creating a Label */
	@Autowired
	private LabelService labelService;
	@Autowired
	private Environment environment;
	/**
	 * 
	 * @param labelDto
	 * @param token
	 * @return LabelInformation
	 */
	@PostMapping("/create")
	public ResponseEntity<UserResponse> createLabel(@RequestBody LabelDto labelDto,@RequestHeader("token") String token)
	{
		System.out.println("1)Label="+labelDto.getName());
		LabelInformation label=labelService.createLabel(labelDto,token);
		if(label!=null)
		{
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new UserResponse(environment.getProperty("201"),201, label));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new UserResponse(environment.getProperty("208"),208, label));
		
	}
	/**
	 * 
	 * @return List<LabelInformation>
	 */
	/*Api for  Fetching AllLabels */
	@GetMapping("/getAllLabels")
	public List<LabelInformation> getAllLabels()
	{
		List<LabelInformation> labelInfromation=labelService.getAllLabels();
		return labelInfromation;
	
	}
	/**
	 * 
	 * @param id
	 * @return LabelInformation
	 */
	/*Api for getting a Label */
	@GetMapping("/getLabel/{id}")
	public ResponseEntity<UserResponse> getLabel(@PathVariable Long id )
	{
		LabelInformation labelInformation=labelService.getLabel(id);
		return  ResponseEntity.status(HttpStatus.FOUND).body(new UserResponse(environment.getProperty("302"),302,labelInformation));
		
	}
	/**
	 * 
	 * @param labelUpdate
	 * @param token
	 * 
	 */
	
	/*Api for deleting a Label */
	@DeleteMapping("/delete")
	public ResponseEntity<UserResponse> deleteLabel(@RequestHeader String token, @RequestParam Long labelId)
	{
		labelService.deleteLabel(token,labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(environment.getProperty("200"),200));
		
	}
	/**
	 * 
	 * @param labelUpdate
	 * @param token
	 * 
	 */
	/*Api for updating a Label */
	@PutMapping("/update")
	public ResponseEntity<UserResponse> updateLabel(@RequestBody LabelUpdate labelUpdate,@RequestHeader("token") String token )
	{
		labelService.update(labelUpdate,token);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(environment.getProperty("202"),202,labelUpdate));
		
	}
	/*Api for create and map a label Label */
//	@PostMapping("/label/createAndMap")
//	public ResponseEntity<UserResponse> createMapLabel(@RequestBody LabelDto labelDto,@RequestHeader("token") String token)
//	{
//		
//		labelService.createMapLabel(labelDto,token);
//		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse("Labels and notes mapped",200));
//		
//	}
	/*Api for mapping  a notes and labels to a particular user */
	/**
	 * 
	 * @param labelId
	 * @param token
	 * @param noteId
	 * @return LabelInformation
	 */
	@PostMapping("/addLabel/{token}")
	public LabelInformation addLabel(@RequestBody LabelNote labelNote,@PathVariable String token)
	{
		LabelInformation labelInformation=labelService.addLabel(labelNote.getLabelId(),token,labelNote.getNoteId());
		return 	labelInformation;
	}
	/**
	 * 
	 * @param token
	 * @return List<LabelInformation>
	 */
	/*Api for getting all labels by userId*/
	@GetMapping("/getLabelsByUserId/{token}")
	public List<LabelInformation> getLabelsByUseid(@PathVariable("token") String token)
	{
		List<LabelInformation> list=labelService.getLabelsByUserId(token);
		return  list;
		
	}
	/**
	 * 
	 * @param token
	 * @param labelId
	 * @return List<NoteInformation>
	 */
	/*Api for getting All notes */
	@GetMapping("/getAllNotes/{labelId}")
	public ResponseEntity<UserResponse> getNotes(@RequestHeader("token") String token,@PathVariable("labelId") Long labelId)
	{
		List<NoteInformation> notesList=labelService.getNotes(token,labelId);
		System.out.println(notesList);	
		return  ResponseEntity.status(HttpStatus.FOUND).body(new UserResponse(environment.getProperty("302"),302,notesList));
		
	}
	
	/*Api for sorting label name */
	@GetMapping(value = "/ascendingSortByName")
	public ResponseEntity<UserResponse> ascSortByLabelName() {
		List<String> result = labelService.ascsortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(environment.getProperty("202"),202, result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse(environment.getProperty("400"), 400,result));
	}
	/*Api for  descending label name */
	@GetMapping(value = "/DescSortByName")
	public ResponseEntity<UserResponse> descSortByLabelName() {
		List<String> result = labelService.descsortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(environment.getProperty("200"),200, result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse(environment.getProperty("400"), 400,result));
	}
	@PostMapping("/RemoveLabelToNote")
	public ResponseEntity<Response> removeLabel(@RequestParam Long noteId,@RequestHeader String token,@RequestParam Long labelId)
	{
		String message = labelService.removeLable(noteId, token, labelId);
		Response response= new Response(HttpStatus.OK.value(), message, "");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@GetMapping("/getnotesfromlabel/{labelid}")
	public List<NoteInformation> getNotesFromLabel(@RequestHeader String token,@PathVariable("labelid") Long labelid)
	{
		System.out.println("getting into labelsnotes");
		List<NoteInformation> notelist=labelService.getnotesfromlabel(token,labelid);
		return notelist;
	}
}
