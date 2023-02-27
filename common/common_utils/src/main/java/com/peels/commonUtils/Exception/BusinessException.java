package com.peels.commonUtils.Exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusinessException extends RuntimeException{
    Integer code;
    String msg;
}
