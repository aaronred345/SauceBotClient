package com.saucebot.twitch.message;


public class BotMessage implements Comparable<BotMessage> {

    private static int idCount = 0;
    private final int id = idCount++;

    private final Priority priority;

    private final String message;

    public BotMessage(final String message, final Priority priority) {
        super();
        this.priority = priority;
        this.message = message;
    }

    public BotMessage(final String line) {
        this(line, Priority.NORMAL);
    }

    public Priority getPriority() {
        return priority;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        if (priority == Priority.NORMAL) {
            return "BotMessage[\"" + message + "\"]";
        } else {
            return "BotMessage[" + priority + ": \"" + message + "\"]";
        }
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BotMessage other = (BotMessage) obj;
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(final BotMessage other) {
        int compare = getPriority().compareTo(other.getPriority());
        if (compare == 0) {
            return other.id - id;
        } else {
            return compare;
        }
    }

}
