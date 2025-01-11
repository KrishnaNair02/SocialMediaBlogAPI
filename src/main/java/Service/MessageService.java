package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;


public class MessageService {
    private MessageDAO msgDAO;

    public MessageService() {
        msgDAO = new MessageDAO();
    }

    public MessageService(MessageDAO dao) {
        this.msgDAO = dao;
    }

    public Message addMessage(Message msg) {
        if (msg.getMessage_text().equals("") || msg.getMessage_text().equals(null)) return null;
        if (msg.getMessage_text().length() > 255) return null;
        //if (msgDAO.getAccountByPostedId(msg).getAccount_id() != msg.getPosted_by()) return null;
        return msgDAO.insertMessage(msg);
    }

    public List<Message> getAllMessages() {
        return msgDAO.getAllMessages();
    }

    public Message getMsgById(int id) {
        Message msg = msgDAO.getMsgFromId(id);
        if (msg == null) return null;
        return msg;
    }

    public Message deleteMessage(int id) {
        Message msg = msgDAO.deleteMessage(id);
        if (msg == null) return null;
        return msg;
    }

    public Message updateMessage(Message msg, int id) {
        if (msg.getMessage_text().equals("") || msg.getMessage_text().equals(null) || msg.getMessage_text().length() > 255) return null;        
        Message message = msgDAO.updateMessage(msg, id);
        if (message == null) return null;
        return message;
    }

    public List<Message> getMsgsByAccount(int id) {
        return msgDAO.getMsgsByAccount(id);
    }
}
