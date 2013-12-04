
import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estevam Meneses
 */
public class MongoConnection {

    private MongoClient iMongoClient;
    private DB iDb = null;
    private final String HOST = "localhost";
    private final int PORT = 27017;
    private  Logger LOGGER = Logger.getLogger("InfoLogging");
    
    
    public DB startConnection(String aCollection) throws UnknownHostException {
        try {
            iMongoClient = new MongoClient();
            iMongoClient = new MongoClient(HOST, PORT);
        } catch (UnknownHostException ex) {
            LOGGER.info("Erro connecting to MongoDb.");
            Logger.getLogger(MongoConnection.class.getName()).log(Level.SEVERE, ex.toString());
            return null;
        }

//        boolean statusAuthenticate = iDb.authenticate(HOST, password)
//        
//        if(statusAuthenticate){
//          LOGGER.info("MongoDb user authenticate!");
//        }else{
//        
//           LOGGER.info("MogoDb user failed in authentication!");
//           return null;
//        }
        
        iDb = iMongoClient.getDB(aCollection);

         LOGGER.info("Starting connection with MongoDb...");
        return iDb;

    }
}
