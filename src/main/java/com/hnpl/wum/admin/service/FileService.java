package com.hnpl.wum.admin.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {

        // uuid(universally unique identifier) 생성
        // 범용 고유 식별 (128비트 16진수로 표현되는 고유의 아이디)
        UUID uuid = UUID.randomUUID();

        // 확장자 추출
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // uuid와 확장자를 조합해서 파일 이름을 생성
        String savedFileName = uuid.toString() + extension;

        // 파일이 업로드 될 전체 경로를 생성
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        // 파일 출력스트림을 생성하고
        // 파일 경로에 해당하는 파일을 생성하거나 덮어쓰기 함
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);

        // 파일 기록
        fos.write(fileData);

        // 출력 스트림 닫기
        fos.close();

        // 저장된 파일 이름 반환
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        // 파일객체 생성
        File deleteFile = new File(filePath);

        // 파일이 존재하는지 확인
        if(deleteFile.exists()) {
            // 존재하면 삭제
            deleteFile.delete();
            System.out.println("파일을 삭제하였습니다.");
        } else {
            // 아니면 존재하지않는다는 메세지 출력
            System.out.println("파일이 존재하지 않습니다.");
        }
    }
}
