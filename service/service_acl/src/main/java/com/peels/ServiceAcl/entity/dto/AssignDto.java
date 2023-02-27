package com.peels.ServiceAcl.entity.dto;

import lombok.Data;

@Data
public class AssignDto {
    String roleId;
    String[] permissionId;
}
