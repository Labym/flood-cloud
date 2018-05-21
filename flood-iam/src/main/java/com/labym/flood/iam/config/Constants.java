package com.labym.flood.iam.config;

public interface Constants {

    String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";
    String SYSTEM_ACCOUNT = "system";
    String ANONYMOUS_USER = "anonymoususer";

    interface Menu{
        String PATH="path";
        String REDIRECT ="redirect";
        String COMPONENT ="component";
        String NAME="name";
        String PROPS ="props";
        String META="meta";
        String ICON="icon";
    }

}
