package kr.co.fastcampus.travel.infrastructure.repository.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public abstract class FileIoRepository {

    private void createFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                if (file.getParentFile() != null && !file.getParentFile().exists()
                        && !file.getParentFile().mkdirs()) {
                    throw new RuntimeException(filename + " 폴더 생성 실패");
                }
                if (!file.createNewFile()) {
                    throw new RuntimeException(filename + " 파일 생성 실패");
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    protected String readFile(String filename) {
        try {
            File file = new File(filename);
            List<String> lines = Files.readAllLines(file.toPath());
            return String.join("\n", lines);
        } catch (IOException e) {
            return "";
        }
    }

    protected void writeFile(String filename, String content) {
        createFile(filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
