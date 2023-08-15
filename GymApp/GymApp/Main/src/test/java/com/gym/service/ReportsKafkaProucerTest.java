package com.gym.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import org.apache.kafka.common.KafkaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import com.gym.dto.TrainingsSummaryDTO;
import com.gym.service.kafka.ReportsKafkaProducer;

@ExtendWith(MockitoExtension.class)
class ReportsKafkaProucerTest {
	
	@Mock(lenient = true)
	private KafkaTemplate<String, TrainingsSummaryDTO> reportsKafkaTemplate;
	@InjectMocks
	private ReportsKafkaProducer reportsKafkaProducer;
	
	@Test
	void addTrainingSummary() throws Exception{
		TrainingsSummaryDTO dto = TrainingsSummaryDTO.builder()
				.build();
		ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
	    ArgumentCaptor<TrainingsSummaryDTO> dtoCaptor = ArgumentCaptor.forClass(TrainingsSummaryDTO.class);

	    Mockito.when(reportsKafkaTemplate.send(topicCaptor.capture(),dtoCaptor.capture())).thenCallRealMethod();

		reportsKafkaProducer.addTrainingSummary(dto);
		assertEquals("reports",topicCaptor.getValue());
		assertEquals(dto,dtoCaptor.getValue());
		Mockito.verify(reportsKafkaTemplate,times(1)).send("reports",dto);
	}
	
	@Test
	void sendMailFail() throws Exception{
		TrainingsSummaryDTO dto = TrainingsSummaryDTO.builder()
								.build();

	    Mockito.doThrow(KafkaException.class).when(reportsKafkaTemplate).send("reportss", dto);
//	    assertThrows(KafkaException.class,()->{
			reportsKafkaProducer.addTrainingSummary(dto);	    	
//	    });

		Mockito.verify(reportsKafkaTemplate,never()).send("reportss",dto);
	}

}
