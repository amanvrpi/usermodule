package com.vrpigroup.usermodule.entity;

import lombok.*;

@Getter
public enum Roles {
    ADMIN, USER, GUEST, SUPER_ADMIN, STUDENT, TEACHER;

    public String getRoles() {
        return this.name();
    }
}
