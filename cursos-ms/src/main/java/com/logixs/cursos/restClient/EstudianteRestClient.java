package com.logixs.cursos.restClient;

import com.logixs.cursos.dto.EstudianteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class EstudianteRestClient {
    public static final String URL = "http://localhost:8081/estudiantes/";

    @Autowired
    private RestTemplate restTemplate;

    public EstudianteDTO obtenerEstudiante(Long estudianteId) {
        try {
            String endpointUrl = URL + estudianteId;
            ResponseEntity<EstudianteDTO> estudianteDTO = restTemplate.exchange(endpointUrl,
                    HttpMethod.GET, null, EstudianteDTO.class);

            return estudianteDTO.getBody();
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public ResponseEntity<EstudianteDTO> actualizarEstudiante(Long estudianteId, EstudianteDTO estudianteDTO) {
        try {
            String endpointUrl = URL + estudianteId;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<EstudianteDTO> request = new HttpEntity<>(estudianteDTO, headers);

            return restTemplate.exchange(endpointUrl, HttpMethod.PUT, request, EstudianteDTO.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
