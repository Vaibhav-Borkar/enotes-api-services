package com.enotes;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericResponse {

	private HttpStatus responseStatus;
	
	private String status;  // SUCCESS OR FAILED
	
	private String message; // SAVED SUCCESS
	
	private Object data;   // RESPONSE DATA
	
	public ResponseEntity<?> create(){
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("status", status);
		map.put("message", message);
		
		if(!ObjectUtils.isEmpty(data)) {
			map.put("data", data);                    
		}
		return new ResponseEntity<>(map,responseStatus);
	}
}



