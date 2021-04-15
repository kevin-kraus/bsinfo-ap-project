package eu.bsinfo.group2.approject.entities.user;

public enum ContactType {
    EMAIL("email"),
    SKYPE("skype"),
    TELEPHONE("telephone"),
    FAX("fax"),
    ADDRESS("address"),
    ;

    private final String type;

    ContactType(String type) {
        this.type = type;
    }
}
