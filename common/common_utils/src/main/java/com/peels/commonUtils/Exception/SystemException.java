package com.peels.commonUtils.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemException extends RuntimeException{
    Integer code;
    String msg;
}
