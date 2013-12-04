
import com.est.mongodb.entity.Role;
import com.est.mongodb.entity.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estevam Meneses
 */
public class MongoController implements MongoActions {

    private static DB iDb = null;
    private DBCollection iCol;

    public static void main(String[] args) {
        MongoConnection mongo = new MongoConnection();
        try {
            iDb = mongo.startConnection("mydb");
        } catch (UnknownHostException ex) {
            Logger.getLogger(MongoController.class.getName()).log(Level.SEVERE, ex.toString());
        }

        MongoController mongoController = new MongoController();


        Scanner console = new Scanner(System.in);
        System.out.println("Type: \n 1) List User \n 2) Add User \n 3) Update User \n 4) Delete User");
        int op = console.nextInt();


        switch (op) {
            case 1: // list user
                mongoController.getAllObjects();
                break;
            case 2: // new user
                User user = new User();
                console.nextLine();
                System.out.println("User  name:");
                user.setUsername(console.nextLine());
                System.out.println("First  name:");
                user.setFirstName(console.nextLine());
                System.out.println("Last  name:");
                user.setLastName(console.nextLine());
                System.out.println("Password  name:");
                user.setPassword(console.nextLine());
                mongoController.saveObject(user);
                System.out.println("User " + user.getUsername() + " add!");
                break;
            case 3: // update
                User usr = new User();
                console.nextLine();
                System.out.println("Type user name for be update:");
                String updateUser = console.nextLine();
                System.out.println("Update user  name:");
                usr.setUsername(console.nextLine());
                System.out.println("Update first  name:");
                usr.setFirstName(console.nextLine());
                System.out.println("Update last  name:");
                usr.setLastName(console.nextLine());
                System.out.println("Update password  name:");
                usr.setPassword(console.nextLine());
                mongoController.updateObject(usr, updateUser);
                System.out.println("User " + usr.getUsername() + " update!");
                break;
            case 4: // delete user
                console.nextLine();
                System.out.println("Type user name for be delete:");
                String username = console.nextLine();
                mongoController.deleteObject(username);
                System.out.println(username + " was deleted with successfully!");
                break;
            default:
                System.out.println("This number ins't part of menu list!");
                break;
        }
    }

    @Override
    public List<User> getAllObjects() {


        iCol = iDb.getCollection("user");
        DBCursor cursor = iCol.find();
        DBObject doc;
        List<User> userList = new ArrayList<User>();
        User user;
        Role role;
        while (cursor.hasNext()) {
            doc = cursor.next();

            user = new User();
            role = new Role();
            user.setId(doc.get("_id").toString());
            user.setUsername(doc.get("username").toString());
            user.setFirstName(doc.get("firstName").toString());
            user.setLastName(doc.get("lastName").toString());
            user.setPassword(doc.get("password").toString());

            userList.add(user);

            role.setId(doc.get("_id").toString());
            role.setRole(doc.get("role").toString());
            user.setRole(role);
            System.out.println(doc.get("firstName") + " " + doc.get("lastName"));
        }

        cursor.close();

        return userList;
    }

    @Override
    public void saveObject(User aUser) {

        iCol = iDb.getCollection("user");
        BasicDBObject doc = new BasicDBObject("_class", "com.est.mongodb.entity.User").
                append("firstName", aUser.getFirstName()).
                append("lastName", aUser.getLastName()).
                append("username", aUser.getUsername()).
                append("password", aUser.getPassword()).
                append("role", new DBRef(iDb, "user", 1)); // add here id of item in collection with DBRef
        //   append("role", new BasicDBObject("role", "admin").append("y", 102)); 
        iCol.insert(doc);
    }

    @Override
    public void updateObject(User aUser, String aUserName) {

        iCol = iDb.getCollection("user");
        BasicDBObject newDocument = new BasicDBObject("_class", "com.est.mongodb.entity.User").
                append("firstName", aUser.getFirstName()).
                append("lastName", aUser.getLastName()).
                append("username", aUser.getUsername()).
                append("password", aUser.getPassword()).
                append("role", new DBRef(iDb, "user", 1)); // add here id of item in collection with DBRef
        //   append("role", new BasicDBObject("role", "admin").append("y", 102)); 
        BasicDBObject searchQuery = new BasicDBObject().append("username", aUserName);
        iCol.update(searchQuery, newDocument);

    }

    @Override
    public void deleteObject(String aUserName) {
        iCol = iDb.getCollection("user");
        BasicDBObject document = new BasicDBObject();
        document.put("username", aUserName);
        iCol.remove(document);
        
    }
}
