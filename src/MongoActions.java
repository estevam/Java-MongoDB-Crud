
import com.est.mongodb.entity.User;
import java.util.List;

/**
 * @author Estevam Meneses
 */
public interface MongoActions<T> {
   
        public List<T> getAllObjects();
        public void saveObject(User aUser);
        public void updateObject(User aUser, String aUserName);
        public void deleteObject(String id);
        //public WriteResult updateObject(String id, String name);
}
