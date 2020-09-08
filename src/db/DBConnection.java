package db;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBConnection implements DBSource{
    private Properties props;
    private String url;
    private String user;
    private String passwd;
    private int max; // connect to the max number in the pool
    private List<Connection> connections;

    public DBConnection() throws IOException, ClassNotFoundException {
        this("db.properties");
    }

    public DBConnection(String configFile) throws IOException,
            ClassNotFoundException {
        props = new Properties();
        props.load(new FileInputStream(new File("db.properties")));

        url = props.getProperty("URL");
        user = props.getProperty("username");
        passwd = props.getProperty("password");
        max = Integer.parseInt(
                props.getProperty("poolmax"));
        Class.forName(
                props.getProperty("driver"));

        connections = new ArrayList<Connection>();
    }

    public synchronized Connection getConnection()
            throws SQLException {
        if(connections.size() == 0) {
            return DriverManager.getConnection(url, user, passwd);
        }
        else {
            int lastIndex = connections.size() - 1;
            return connections.remove(lastIndex);
        }
    }

    public synchronized void closeConnection(Connection conn)
            throws SQLException {
        if(connections.size() == max) {
            conn.close();
        }
        else {
            connections.add(conn);
        }
    }

}