package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accService;
    MessageService msgService;

    public SocialMediaController() {
        accService = new AccountService();
        msgService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMsgHandler);
        app.get("/messages", this::getAllMsgHandler);
        app.get("/messages/{message_id}", this::getMsgByIdHandler);
        app.delete("messages/{message_id}", this::deleteMsgHandler);
        app.patch("messages/{message_id}", this::updateMsgHandler);
        app.get("/accounts/{account_id}/messages", this::getMsgByAccountHandler);
        return app;
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objM = new ObjectMapper();
        Account acc = objM.readValue(ctx.body(), Account.class);
        Account addedAccount = accService.addAccount(acc);
        if (addedAccount != null) {
            ctx.json(objM.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objM = new ObjectMapper();
        Account acc = objM.readValue(ctx.body(), Account.class);
        Account loggedInAcc = accService.logIntoAcc(acc);
        if (loggedInAcc != null) {
            ctx.json(objM.writeValueAsString(loggedInAcc));
        }
        else ctx.status(401);
    }

    private void postMsgHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objM = new ObjectMapper();
        Message msg = objM.readValue(ctx.body(), Message.class);
        Message newMsg = msgService.addMessage(msg);
        if (newMsg != null) {
            ctx.json(objM.writeValueAsString(newMsg));
        }
        else ctx.status(400);
    }

    private void getAllMsgHandler(Context ctx) {
        ctx.json(msgService.getAllMessages());
    }

    private void getMsgByIdHandler(Context ctx) {
        int msg_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message newMsg = msgService.getMsgById(msg_id);
        if (newMsg != null) {
            ctx.json(newMsg);
        } else ctx.status(200);
    }

    private void deleteMsgHandler(Context ctx) {
        int msg_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message dltdMessage = msgService.deleteMessage(msg_id);
        if (dltdMessage != null) ctx.json(dltdMessage);
        else ctx.status(200);

    }

    private void updateMsgHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objM = new ObjectMapper();
        Message msg = objM.readValue(ctx.body(), Message.class);
        int msg_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message newMsg = msgService.updateMessage(msg, msg_id);
        if (newMsg != null) {
            ctx.json(objM.writeValueAsString(newMsg));
        }
        else ctx.status(400);
    }

    private void getMsgByAccountHandler(Context ctx) {
        int acc_id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(msgService.getMsgsByAccount(acc_id));
    }

}