package com.peels.commonUtils.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadException extends IOException {
    Integer code;
    String msg;
}
