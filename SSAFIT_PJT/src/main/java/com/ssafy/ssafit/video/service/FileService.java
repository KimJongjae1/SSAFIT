package com.ssafy.ssafit.video.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    // ✅ 프로젝트 실행 위치 기준 상대 경로 → 발표 환경에서도 안전
    private static final String BASE_PATH = System.getProperty("user.dir") + "/uploads";

    public String saveFile(MultipartFile file, String folder) throws IOException {
        if (file.isEmpty()) throw new IOException("파일이 비어있습니다.");

        String uploadDir = BASE_PATH + "/" + folder;
        File dir = new File(uploadDir);

        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            System.out.println("📁 폴더 생성 여부: " + created + " → " + dir.getAbsolutePath());
        }

        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename != null && originalFilename.contains(".")
            ? originalFilename.substring(originalFilename.lastIndexOf("."))
            : "";

        String newFilename = UUID.randomUUID().toString() + ext;
        File dest = new File(dir, newFilename);

        System.out.println("📂 저장 대상 경로: " + dest.getAbsolutePath());

        file.transferTo(dest);
        System.out.println("✅ 파일 존재 여부: " + dest.exists());

        return "/files/" + folder + "/" + newFilename;
    }
}
