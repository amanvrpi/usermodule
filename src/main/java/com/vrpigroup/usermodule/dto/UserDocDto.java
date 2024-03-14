package com.vrpigroup.usermodule.dto;

import lombok.*;

@Data
@RequiredArgsConstructor
public class UserDocDto {

    private Long userId;
    private byte[] aadharFront;
    private byte[] aadharBack;
    private byte[] profilePic;
}
