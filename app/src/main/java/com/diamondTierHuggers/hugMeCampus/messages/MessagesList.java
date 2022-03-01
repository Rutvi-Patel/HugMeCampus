package com.diamondTierHuggers.hugMeCampus.messages;

public class MessagesList {
    private String name, mobile, lastMessage, profilePic;
    private int unseenMessages;

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public MessagesList(String name, String mobile, String lastMessage, int unseenMessages, String profilePic) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
        this.profilePic = profilePic;
    }
}
