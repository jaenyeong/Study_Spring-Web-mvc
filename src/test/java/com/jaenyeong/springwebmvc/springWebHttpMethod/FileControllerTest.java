package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
// 모든 빈이 등록됨
@SpringBootTest
// 모든 빈이 등록되지만 mockMvc를 자동으로 생성해주지 않아 해당 어노테이션으로 주입
@AutoConfigureMockMvc
public class FileControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void fileUpload() throws Exception {
		//  @param name the name of the file
		//  @param originalFilename the original filename (as on the client's machine)
		//  @param contentType the content type (if known)
		//  @param contentStream the content of the file as stream
		//  @throws IOException if reading from the stream failed
		MockMultipartFile file = new MockMultipartFile(
				"file", "test.txt", "text/plain", "hello file" .getBytes());

		// enctype="multipart/form-data"
		this.mockMvc.perform(
				multipart("/files")
						.file(file))
				.andDo(print())
				.andExpect(status().is3xxRedirection());
	}
}
