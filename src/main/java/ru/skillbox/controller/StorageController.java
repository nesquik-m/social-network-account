package ru.skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.dto.AccountDto;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
@Slf4j
public class StorageController {

    @PostMapping
    public ResponseEntity<AccountDto> uploadAPhoto(@RequestParam("file") String file) {
    /*
    {
        "file": "string"
    }
    */
        // Логика загрузки файла
        AccountDto accountDto = new AccountDto();
        // ... заполнение accountDto данными
        return ResponseEntity.ok(accountDto);
        // 200 (+ объект AccountDto!), 400, 401, 404
    }

}
