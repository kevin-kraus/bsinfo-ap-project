package eu.bsinfo.group2.approject.entities.user;

public enum UserType {
    STANDARD("standard"),
    ADMIN("admin");

    private final String type;

    UserType(String type) {
        this.type = type;
    }
}
