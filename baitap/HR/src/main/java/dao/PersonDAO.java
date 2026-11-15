package dao;

import java.sql.*;
import java.util.*;
import model.Person;
import util.DBConnection;

public class PersonDAO {

    public void insert(Person p) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO person(first_name,last_name,phone,email,hobbies,description) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setString(3, p.getPhone());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getHobbies());
            ps.setString(6, p.getDescription());

            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }

    public List<Person> search(String name){
        List<Person> list = new ArrayList<>();
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM person WHERE first_name LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%"+name+"%");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Person p = new Person();
                p.setId(rs.getInt("id"));
                p.setFirstName(rs.getString("first_name"));
                p.setLastName(rs.getString("last_name"));
                list.add(p);
            }
        }catch(Exception e){ e.printStackTrace(); }
        return list;
    }

    public Person getById(int id){
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM person WHERE id=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Person p = new Person();
                p.setId(id);
                p.setFirstName(rs.getString("first_name"));
                p.setLastName(rs.getString("last_name"));
                return p;
            }
        }catch(Exception e){}
        return null;
    }

    public void update(Person p){
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE person SET first_name=?,last_name=? WHERE id=?"
            );
            ps.setString(1,p.getFirstName());
            ps.setString(2,p.getLastName());
            ps.setInt(3,p.getId());
            ps.executeUpdate();
        }catch(Exception e){}
    }

    public void delete(int id){
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM person WHERE id=?");
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch(Exception e){}
    }
}