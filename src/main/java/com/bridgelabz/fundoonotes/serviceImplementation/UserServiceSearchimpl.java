package com.bridgelabz.fundoonotes.serviceImplementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.service.UserServiceSearch;
import com.bridgelabz.fundoonotes.util.JwtGenerator;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceSearchimpl implements UserServiceSearch {

	String INDEX = "pkl";
	String TYPE = "notes";
	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private ObjectMapper objectMapper;
		
	@Override
	@SuppressWarnings("unchecked")
	public String createNote(NoteInformation note) throws IOException {
		// TODO Auto-generated method stub
		
		Map<String, Object> documentMappper = objectMapper.convertValue(note, Map.class);		
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getNoteId())).source(documentMappper);
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		return indexResponse.getResult().name();
		
		
	}
	@Override
	public NoteInformation findById(String id) throws Exception {
		// TODO Auto-generated method stub
			GetRequest getRequest = new GetRequest(INDEX, TYPE, id);

			GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
			Map<String, Object> resultMap = getResponse.getSource();

			return objectMapper.convertValue(resultMap, NoteInformation.class);

		
	}
	@Override
	public String upDateNote(NoteInformation note) throws Exception {
		NoteInformation notes = findById(String.valueOf(note.getNoteId()));

		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(notes.getNoteId()));

		@SuppressWarnings("unchecked")
		Map<String, Object> mapDoc = objectMapper.convertValue(note, Map.class);
		updateRequest.doc(mapDoc);

		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
		return updateResponse.getResult().name();
		
		
	}
	@Override
	public String deleteNote(String id) throws IOException {
		// TODO Auto-generated method stub
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
		DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);

		return response.getResult().name();
		
	}
	@Override
	public List<NoteInformation> getNoteByTitleAndDescription(String text) {
		// TODO Auto-generated method stub
		
		SearchRequest searchRequest = buildSearchRequest(INDEX, TYPE);
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text).lenient(true).field("title").field("description"))
				.should(QueryBuilders.queryStringQuery("*" + text + "*").lenient(true).field("title")
						.field("description"));
		searchSourceBuilder.query(query);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getSearchResult(searchResponse);
		
	}
	private List<NoteInformation> getSearchResult(SearchResponse searchResponse) {
		// TODO Auto-generated method stub
		
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		List<NoteInformation> notesDoc = new ArrayList<NoteInformation>();
		if (searchHits.length > 0) {
			Arrays.stream(searchHits)
					.forEach(hit -> notesDoc.add(objectMapper.convertValue(hit.getSourceAsMap(), NoteInformation.class)));
		}

		return notesDoc;
		
	}
	private SearchRequest buildSearchRequest(String index, String type) {
		// TODO Auto-generated method stub
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(index);
		searchRequest.types(type);

		return searchRequest;
	
		
		
	}
	
}
