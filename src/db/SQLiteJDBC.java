/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author Nika
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteJDBC {

    Connection c = null;
    Statement stmt = null;
    ResultSet rs = null;

    public SQLiteJDBC() {
    }

    public void closeConnection()  {
        try {
            stmt.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void createtable(String db, String sql) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + db);
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            // stmt.close();
            // c.close();
            System.out.println(sql);
            System.out.println("Table created successfully on DB " + db);

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }

    }

    public void insert(String db, String sql) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + db);
            c.setAutoCommit(false);
            //  System.out.println("Opened database successfully");

            stmt = c.createStatement();
            stmt.executeUpdate(sql);

            //    stmt.close();
            c.commit();
            //    c.close();
            System.out.println(sql);
            System.out.println("Records created successfully on DB " + db);

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }
    }

    public ResultSet select(String db, String sql) throws SQLException {

        ResultSet rs2 = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + db);
            c.setAutoCommit(false);
            //  System.out.println("Opened database successfully");

            stmt = c.createStatement();
            rs2 = stmt.executeQuery(sql);
            System.out.println(sql);
            System.out.println("Select Operation done successfully on DB " + db);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.err.println("Select Operation was not done successfully on DB " + db);
            return null;
        }

        return rs2;

    }

    public void Update(String db, String sql) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + db);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            c.commit();

            //      stmt.close();
            //    c.close();
            System.out.println(sql);
            System.out.println("Update Operation done successfully on DB " + db);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }

    }

    public void Update(String db, String sql, Object obj)  {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + db);
            c.setAutoCommit(false);
            PreparedStatement ps = null;

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(obj);
            oos.flush();
            oos.close();
            bos.close();

            byte[] data = bos.toByteArray();

//            sql = "insert into javaobject (javaObject) values(?)";
            ps = c.prepareStatement(sql);
            ps.setObject(1, data);
            ps.executeUpdate();

            c.commit();

            System.out.println(sql);
            System.out.println("Update Operation done successfully on DB " + db);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        } catch (IOException ex) {
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Object getObject(String db, String sql)  {
        Object rmObj = null;
            try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + db);
            c.setAutoCommit(false);
            PreparedStatement ps = null;
            ResultSet rs = null;
            //String sql=null;
            
            //sql="select * from javaobject where id=1";
            ps = c.prepareStatement(sql);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                ByteArrayInputStream bais;
                
                ObjectInputStream ins;
                
                try {
                    
                    bais = new ByteArrayInputStream(rs.getBytes("VALUE"));
                    
                    ins = new ObjectInputStream(bais);
                    
                    ArrayList mc = (ArrayList) ins.readObject();
                    
                    System.out.println("Object in value ::" + mc);
                    ins.close();
                    
                    rmObj = mc;
                } catch (Exception e) {
                    
                    e.printStackTrace();
                }
                
            }

            return rmObj;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rmObj;
    }

    public void delete(String db, String sql) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + db);
            c.setAutoCommit(false);
            // System.out.println("Opened database successfully");

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            c.commit();

            // stmt.close();
            // c.close();
            System.out.println(sql);
            System.out.println("Delete Operation done successfully on DB " + db);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }
    }

    public void execute(String db, String sql) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + db);
            c.setAutoCommit(false);
            // System.out.println("Opened database successfully");

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            c.commit();

            // stmt.close();
            // c.close();
            System.out.println(sql);
            System.out.println("Query Executed Operation done successfully on DB " + db);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }

    }

    public void toFile(String dbloc, String sql, String file) {
        try {
            ResultSet result = this.select(dbloc, sql);
            ResultSetMetaData rsm = result.getMetaData();
            int columncount = rsm.getColumnCount();
            PrintStream out = new PrintStream(file); //new AppendFileStream
            for (int i = 1; i <= columncount; i++) {
                out.print(rsm.getColumnName(i) + "\t");
            }
            out.print("\n");
            while (result.next()) {
                for (int i = 1; i <= columncount; i++) {
                    out.print(result.getString(i) + "\t");
                }
                out.print("\n");

            }
            out.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String args[]) {
        SQLiteJDBC sqLiteJDBC = new SQLiteJDBC();
    }
}
