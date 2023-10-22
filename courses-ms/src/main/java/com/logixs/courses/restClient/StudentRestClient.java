package com.logixs.courses.restClient;

import com.logixs.courses.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class StudentRestClient {
    public static final String URL = "http://students-ms:8081/students/";

    @Autowired
    private RestTemplate restTemplate;

    public StudentDTO getStudent(Long studentId) {
        try {
            String endpointUrl = URL + studentId;
            ResponseEntity<StudentDTO> studentDTO = restTemplate.exchange(endpointUrl,
                    HttpMethod.GET, null, StudentDTO.class);

            return studentDTO.getBody();
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public ResponseEntity<StudentDTO> updateStudent(Long studentId, StudentDTO studentDTO) {
        try {
            String endpointUrl = URL + studentId;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<StudentDTO> request = new HttpEntity<>(studentDTO, headers);

            return restTemplate.exchange(endpointUrl, HttpMethod.PUT, request, StudentDTO.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
