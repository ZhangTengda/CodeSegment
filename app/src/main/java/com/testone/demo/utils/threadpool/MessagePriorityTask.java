package com.testone.demo.utils.threadpool;

/**
 * @author wangqi
 */
public abstract class MessagePriorityTask implements Runnable, Comparable<MessagePriorityTask> {

//    private JSONObject json;
//    private int priority;
//
//    public MessagePriorityTask(JSONObject message) {
//        priority = message.getJSONObject("data").getIntValue("seq");
//        json = message;
//    }
//
//    @Override
//    public int compareTo(MessagePriorityTask t) {
//        if (this.getPriority() > t.getPriority()) {
//            return 1;
//        }
//        if (this.getPriority() < t.getPriority()) {
//            return -1;
//        }
//        return 0;
//
//    }
//
//    public ConversationMessage getConversationMessage() {
//        JSONObject data = json.getJSONObject("data");
//        return new ConversationParser(true).parseMessage(data);
//    }
//
//    public int getPriority() {
//        return priority;
//    }
}