package tikape.runko.domain;

import java.text.SimpleDateFormat;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

/**
 *
 * @author Aleksi Huotala
 */
public class Message {

    private final String body;
    private final int userId;
    private String username;
    private final int messageId;
    private final String timestamp;
    private final String formattedTimestamp;
    private int threadId;
    private TextProcessor tp;

    /**
     * Viesti -luokka
     *
     * @param messageId Viestin ID
     * @param userId Käyttäjätunnuksen ID
     * @param body Viestin sisältö
     * @param timeStamp Viestin aikaleima
     */
    public Message(int messageId, int userId, String body, String timeStamp) {
        this.messageId = messageId;
        this.userId = userId;
        this.body = body;
        this.timestamp = timeStamp;
        //Parsitaan aikaleiman lopusta sekunnit ja muu roska pois
        formattedTimestamp = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(java.sql.Timestamp.valueOf(timeStamp));
        //Luodaan BBCoden parsija
        tp = BBProcessorFactory.getInstance().create();
    }

    /**
     * Viesti -luokka
     *
     * @param userId Viestin ID
     * @param body Viestin sisältö
     * @param timeStamp Viestin aikaleima
     */
    public Message(int userId, String body, String timeStamp) {
        this(-1, userId, body, timeStamp);
    }

    /**
     * Viesti -luokka
     *
     * @param threadId Viestiketjun ID
     * @param userId Käyttäjätunnuksen ID
     * @param title Otsikko
     * @param timeStamp Aikaleima
     * @param username Käyttäjätunnus
     */
    public Message(int threadId, int userId, String title, String timeStamp, String username) {
        this(-1, userId, "", timeStamp);
        this.username = username;
    }

    /**
     * Palauttaa käyttäjätunnuksen
     *
     * @return Käyttäjätunnus
     */
    public String getUsername() {
        return username;
    }

    /**
     * Asettaa käyttäjätunnuksen
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Palauttaa viestin sisällön
     *
     * @return Viestin sisältö
     */
    public String getBody() {
        return body;
    }

    /**
     * Palauttaa viestin sisällön muodossa, jossa BBCode on muunnettu toimivaan
     * muotoon.
     *
     * @return Viestin sisältö
     */
    public String getFormattedBody() {
        //TextProcessor parsii tietokannan viestistä haitalliset tägit pois XSS -injektioiden varalta sekä muuntaa BBCoden luettavaan muotoon.
        String tmpBody = tp.process(body);
        return tmpBody;
    }

    /**
     * Palauttaa viestin sisällön muodossa, jossa siitä on poistettu haitalliset
     * tägin aloitus- ja lopetusmerkit
     *
     * @return Viestin sisältö
     */
    public String getEscapedBody() {
//        String tmpBody = body.replace("<", "&lt;").replace(">", "&gt;");
//        return tmpBody;
        return body;
    }

    /**
     * Palauttaa käyttäjätunnuksen ID:n
     *
     * @return Käyttäjätunnuksen ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Palauttaa viestiketjun aikaleiman
     *
     * @return Viestiketjun aikaleima
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @return
     */
    public String getFormattedTimestamp() {
        return formattedTimestamp;
    }

    /**
     * Palauttaa viestiketjun ID:n
     *
     * @return Viestiketjun ID
     */
    public int getThreadId() {
        return threadId;
    }

    /**
     * Asettaa viestiketjun ID:n
     *
     * @param threadId Viestiketjun ID
     */
    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    /**
     * Palauttaa viestin ID:n
     *
     * @return Viestin ID
     */
    public int getMessageId() {
        return messageId;
    }

}
