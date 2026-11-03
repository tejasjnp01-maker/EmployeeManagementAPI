package com.kiet.inventory;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class InventoryManagementApp {
    static String url, user, pass;

    public static void main(String[] args) {
        loadProperties();
        try (Scanner sc = new Scanner(System.in);
             Connection con = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Connected to retail_db.");
            insertThreeSampleProducts(con);
            int choice;
            do {
                System.out.println("\n--- INVENTORY MENU ---");
                System.out.println("1. Insert product");
                System.out.println("2. Search category where quantity < 20 (price descending)");
                System.out.println("3. Add stock");
                System.out.println("4. Search product and update price");
                System.out.println("5. Total inventory value");
                System.out.println("6. Delete only if quantity is zero");
                System.out.println("0. Exit");
                System.out.print("Choice: ");
                while (!sc.hasNextInt()) { System.out.print("Enter a number: "); sc.next(); }
                choice = sc.nextInt(); sc.nextLine();
                try {
                    switch (choice) {
                        case 1 -> insertProduct(con, sc);
                        case 2 -> categorySearch(con, sc);
                        case 3 -> addStock(con, sc);
                        case 4 -> updatePrice(con, sc);
                        case 5 -> inventoryValue(con);
                        case 6 -> deleteZeroStock(con, sc);
                        case 0 -> System.out.println("Goodbye.");
                        default -> System.out.println("Choose 0-6.");
                    }
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Duplicate product ID. Use a different ID.");
                } catch (SQLException e) {
                    System.out.println("Database error: " + e.getMessage());
                }
            } while (choice != 0);
        } catch (Exception e) {
            System.out.println("Connection/setup failed. Check MySQL is running and db.properties is correct.");
            System.out.println("Details: " + e.getMessage());
        }
    }

    static void loadProperties() {
        try (InputStream in = InventoryManagementApp.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new IllegalStateException("db.properties missing");
            Properties p = new Properties(); p.load(in);
            url = p.getProperty("db.url"); user = p.getProperty("db.username"); pass = p.getProperty("db.password");
            if ("YOUR_MYSQL_PASSWORD".equals(pass)) System.out.println("Set db.password in db.properties before running.");
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    // Inserts three sample products with PreparedStatement; duplicate IDs are ignored.
    static void insertThreeSampleProducts(Connection c) throws SQLException {
        String sql = "INSERT INTO Product(product_id,product_name,category,quantity,price) VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE product_id=product_id";
        Object[][] rows = {{101,"Keyboard","Electronics",15,1200.0},{102,"Mouse","Electronics",8,650.0},{103,"Notebook","Stationery",30,55.0}};
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            for (Object[] r : rows) {
                ps.setInt(1,(Integer)r[0]); ps.setString(2,(String)r[1]); ps.setString(3,(String)r[2]);
                ps.setInt(4,(Integer)r[3]); ps.setDouble(5,(Double)r[4]); ps.executeUpdate();
            }
        }
    }

    static void insertProduct(Connection c, Scanner s) throws SQLException {
        System.out.print("ID: "); int id=s.nextInt(); s.nextLine();
        System.out.print("Name: "); String name=s.nextLine();
        System.out.print("Category: "); String cat=s.nextLine();
        System.out.print("Quantity: "); int qty=s.nextInt();
        System.out.print("Price: "); double price=s.nextDouble(); s.nextLine();
        if(qty<0 || price<0){System.out.println("Quantity/price cannot be negative.");return;}
        try(PreparedStatement p=c.prepareStatement("INSERT INTO Product VALUES(?,?,?,?,?)")){
            p.setInt(1,id);p.setString(2,name);p.setString(3,cat);p.setInt(4,qty);p.setDouble(5,price);
            p.executeUpdate();System.out.println("Product inserted.");
        }
    }

    static void categorySearch(Connection c, Scanner s) throws SQLException {
        System.out.print("Category: "); String cat=s.nextLine();
        try(PreparedStatement p=c.prepareStatement("SELECT * FROM Product WHERE category=? AND quantity<20 ORDER BY price DESC")){
            p.setString(1,cat);try(ResultSet r=p.executeQuery()){printRows(r);}
        }
    }

    static void addStock(Connection c, Scanner s) throws SQLException {
        System.out.print("Product ID: "); int id=s.nextInt();
        System.out.print("Quantity to add: "); int qty=s.nextInt();s.nextLine();
        if(qty<=0){System.out.println("Added quantity must be positive.");return;}
        try(PreparedStatement p=c.prepareStatement("UPDATE Product SET quantity=quantity+? WHERE product_id=?")){
            p.setInt(1,qty);p.setInt(2,id);
            System.out.println(p.executeUpdate()>0?"Stock updated.":"Product does not exist.");
        }
    }

    static void updatePrice(Connection c, Scanner s) throws SQLException {
        System.out.print("Product ID: "); int id=s.nextInt();
        System.out.print("New price (>0): "); double price=s.nextDouble();s.nextLine();
        if(price<=0){System.out.println("Price must be greater than zero.");return;}
        try(PreparedStatement p=c.prepareStatement("SELECT * FROM Product WHERE product_id=?")){
            p.setInt(1,id);try(ResultSet r=p.executeQuery()){
                if(!r.next()){System.out.println("Product does not exist.");return;}
                System.out.println("Before update:");printRow(r);
            }
        }
        try(PreparedStatement p=c.prepareStatement("UPDATE Product SET price=? WHERE product_id=?")){
            p.setDouble(1,price);p.setInt(2,id);p.executeUpdate();
        }
        try(PreparedStatement p=c.prepareStatement("SELECT * FROM Product WHERE product_id=?")){
            p.setInt(1,id);try(ResultSet r=p.executeQuery()){if(r.next()){System.out.println("Updated details:");printRow(r);}}
        }
    }

    static void inventoryValue(Connection c) throws SQLException {
        try(PreparedStatement p=c.prepareStatement("SELECT COALESCE(SUM(quantity*price),0) AS total FROM Product");
            ResultSet r=p.executeQuery()){
            if(r.next())System.out.printf("Total inventory value: %.2f%n",r.getDouble("total"));
        }
    }

    static void deleteZeroStock(Connection c, Scanner s) throws SQLException {
        System.out.print("Product ID: ");int id=s.nextInt();s.nextLine();
        try(PreparedStatement p=c.prepareStatement("SELECT quantity FROM Product WHERE product_id=?")){
            p.setInt(1,id);try(ResultSet r=p.executeQuery()){
                if(!r.next()){System.out.println("Product does not exist.");return;}
                if(r.getInt("quantity")!=0){System.out.println("Cannot delete: quantity is not zero.");return;}
            }
        }
        try(PreparedStatement p=c.prepareStatement("DELETE FROM Product WHERE product_id=? AND quantity=0")){
            p.setInt(1,id);System.out.println(p.executeUpdate()>0?"Product deleted.":"Product could not be deleted.");
        }
    }

    static void printRows(ResultSet r) throws SQLException {
        boolean found=false;while(r.next()){found=true;printRow(r);}if(!found)System.out.println("No matching products.");
    }
    static void printRow(ResultSet r) throws SQLException {
        System.out.printf("ID:%d | Name:%s | Category:%s | Quantity:%d | Price:%.2f%n",
            r.getInt("product_id"),r.getString("product_name"),r.getString("category"),r.getInt("quantity"),r.getDouble("price"));
    }
}
