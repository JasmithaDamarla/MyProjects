package com.gym.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import com.gym.dto.EmailDTO;
import com.gym.service.kafka.EmailKafkaProducer;

@ExtendWith(MockitoExtension.class)
class EmailKafkaProducerTest {
	
	@Mock(lenient = true)
	private KafkaTemplate<String, EmailDTO> emailKafkaTemplate;
	@InjectMocks
	private EmailKafkaProducer emailKafkaProducer;
	
	@Test
	void sendMailSuccess() throws Exception{
		EmailDTO dto = EmailDTO.builder()
								.body("Hi")
								.ccEmail("jasmithadamarla@gmail.com")
								.fromEmail("jasmithadamarla@gmail.com")
								.toEmail("snvjas3.d@gmail.com")
								.status("SENT")
								.subject("Greeting")
								.remarks("Sample Msg")
								.build();
		ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
	    ArgumentCaptor<EmailDTO> dtoCaptor = ArgumentCaptor.forClass(EmailDTO.class);

	    Mockito.when(emailKafkaTemplate.send(topicCaptor.capture(),dtoCaptor.capture())).thenCallRealMethod();

		emailKafkaProducer.sendEmail(dto);
		assertEquals("email",topicCaptor.getValue());
		assertEquals(dto,dtoCaptor.getValue());
		Mockito.verify(emailKafkaTemplate,times(1)).send("email",dto);
	}
	
}
