package fun.yozora.admin.web.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/backup")
@RequiredArgsConstructor
public class BackupController {

    private final String backupPath = "D:\\SE\\ManagementSystem\\dev\\backup\\";
    @PostMapping("/create")
    public ResponseEntity<Void> createBackup() throws Exception {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "backup_" + date + ".sql";
        String backupPath = "/path/to/backup/directory/";
        String command = String.format("mysqldump -u root -p 318419 dbname > %s%s", backupPath, fileName);

        Process process = Runtime.getRuntime().exec(command);
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("数据库备份失败");
        }

        return ResponseEntity.ok().build();
    }
    @PostMapping("/restore")
    public ResponseEntity<Void> restoreDatabase(@RequestParam("file") MultipartFile file) throws Exception {
        String backupPath = "/path/to/backup/directory/";
        String tempFilePath = backupPath + file.getOriginalFilename();

        // 保存上传的文件
        file.transferTo(new File(tempFilePath));

        // 执行恢复命令
        String command = String.format("mysql -u your_user -pyour_pass dbname < %s", tempFilePath);
        Process process = Runtime.getRuntime().exec(command);
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("数据库还原失败");
        }

        return ResponseEntity.ok().build();
    }
}

