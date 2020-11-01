package com.tripfellows.server.model.request;

import com.tripfellows.server.enums.RoleCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AddMemberRequest {
    private Integer tripId;
    private Integer accountId;
    private RoleCodeEnum roleCode;
}
