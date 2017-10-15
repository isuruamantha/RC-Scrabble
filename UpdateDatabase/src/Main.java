import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {

    static Connection connection = null;
    static Statement stmt = null;
    static ArrayList<String> wordsList = new ArrayList<>();

    public static void main(String[] args) {
        insertData();
//        createDatabase();
    }

    private static void createDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database/Words.db");
            System.out.println("Opened database successfully");

            stmt = connection.createStatement();
            String sql = "CREATE TABLE WORDS " +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    " NAME           TEXT)";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }


    public static void insertData() {


        String ret = "";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Database/Words.db");
            connection.setAutoCommit(true);
            System.out.println("Opened database successfully");

            try {
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                InputStream inputStream = classloader.getResourceAsStream("words.txt");

                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        wordsList.add(receiveString);
                        stringBuilder.append(receiveString);
                    }

                    inputStream.close();
                    ret = stringBuilder.toString();
                }
            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }


            for (int i = 0; i < wordsList.size(); i++) {
                String sql = "INSERT INTO 'words' (VALUE) " + "VALUES ('" + wordsList.get(i) + "');";
                stmt = connection.createStatement();
                stmt.executeUpdate(sql);
                System.out.println(i);
            }

            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

}
