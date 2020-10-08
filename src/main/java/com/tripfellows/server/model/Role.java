package com.tripfellows.server.model;

import com.tripfellows.server.enums.RoleCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Role {
    private Integer id;
    private RoleCodeEnum code;
    private String name;

    public Role(RoleCodeEnum code, String name) {
        this.code = code;
        this.name = name;
    }
}
