package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.entity.FilePathDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class FileDownloadController {
    @PostMapping("/downloadFile")
    public ResponseEntity<FileSystemResource> downloadFile(@RequestBody FilePathDto filePathDto) {
        String filePath = filePathDto.getFilePath();
        File file = new File(filePath);
        if (file.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new FileSystemResource(file));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}



//import org.springframework.core.io.FileSystemResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//
//@RestController
//public class FileDownloadController {
//    @GetMapping("/downloadFile/{filePath}")
//    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable String filePath) {
//        File file = new File(filePath);
//        if (file.exists()) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .body(new FileSystemResource(file));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}