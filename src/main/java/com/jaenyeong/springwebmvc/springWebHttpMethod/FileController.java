package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

	private final ResourceLoader resourceLoader;

	public FileController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@GetMapping("/files")
	public String fileUploadForm() {
		return "/files/index";
	}

	@PostMapping("/files")
//	public String fileUpload(@RequestParam("file") MultipartFile file) {
	public String fileUpload(MultipartFile file, RedirectAttributes redirectAttributes) {
		// 저장처리 하였다고 가정
		String message = file.getOriginalFilename() + " is uploaded";
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/files";
	}

	@GetMapping("/files/{fileName}")
	// 이 예제 경우엔 @ResponseBody 어노테이션을 태깅하지 않아도 됨
//	@ResponseBody
	public ResponseEntity<Resource> filesDownload(@PathVariable String fileName) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:" + fileName);
		// 파일 객체 생성때문에 메서드의 throws IOException 예외 추가
		File file = resource.getFile();

		// Tika 라이브러리
		Tika tika = new Tika();
		String mediaType = tika.detect(file);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachement; filename=\"" + resource.getFilename() + "\"")
				// 미디어 타입(파일 확장자)을 직접 지정하지 않고 자바 API를 통해 확인할 수 있음
				// 라이브러리 또한 자바 API를 통해 타입 확인
				.header(HttpHeaders.CONTENT_TYPE,
						mediaType)
				.header(HttpHeaders.CONTENT_LENGTH,
						String.valueOf(file.length()))
				.body(resource);
	}
}
