package dg.project.UserManagement.infrastructure.utils;

public enum RoleEnum {
    ADMIN,
    USER;

    @Override
    public String toString() {
        return name();
    }
}