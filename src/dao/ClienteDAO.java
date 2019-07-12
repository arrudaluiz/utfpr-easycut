package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;

public class ClienteDAO {
    public Connection conn;
    public PreparedStatement state;
    public ResultSet rs;

    public ClienteDAO() {
        conn = null;
        state = null;
        rs = null;
    }

    public int insert(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, telefone) VALUES (?, ?);";
        int id = -1;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            state.setString(1, cliente.getNome());
            state.setString(2, cliente.getTelefone());
            state.executeUpdate();
            rs = state.getGeneratedKeys();

            if (rs.next())
                id = rs.getInt("id");
            
            state.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(
                    ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            return id;
        }
    }

    public void delete(Cliente cliente) {
        String sql = "DELETE FROM cliente WHERE id = ?;";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, cliente.getId());
            state.executeUpdate();
            state.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(
                    ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, telefone = ? WHERE id = ?;";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setString(1, cliente.getNome());
            state.setString(2, cliente.getTelefone());
            state.setInt(3, cliente.getId());
            state.executeUpdate();
            state.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cliente queryForId(int id) {
        String sql = "SELECT * FROM cliente WHERE id = ?;";
        Cliente cliente = null;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, id);
            state.executeQuery();
            rs = state.getResultSet();

            if (rs.next()) {
                cliente = new Cliente(rs.getString("nome"),
                                      rs.getString("telefone"));
                cliente.setId(rs.getInt("id"));
            }
            
            state.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            return cliente;
        }
    }

    public ArrayList<Cliente> queryAll() {
        String sql = "SELECT * FROM cliente;";
        ArrayList<Cliente> clientes = null;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.executeQuery();
            rs = state.getResultSet();
            clientes = new ArrayList<>();

            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getString("nome"),
                                              rs.getString("telefone"));
                cliente.setId(rs.getInt("id"));
                clientes.add(cliente);
            }
            
            state.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return clientes;
    }

    public ArrayList<Cliente> queryForString(String string) {
        String sql = "SELECT * FROM cliente "
                + "WHERE nome ILIKE ? OR telefone ILIKE ?;";
        ArrayList<Cliente> clientes = null;
        string = "%" + string + "%";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setString(1, string);
            state.setString(2, string);
            state.executeQuery();
            rs = state.getResultSet();
            clientes = new ArrayList<>();

            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getString("nome"),
                                              rs.getString("telefone"));
                cliente.setId(rs.getInt("id"));
                clientes.add(cliente);
            }
            
            state.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return clientes;
    }
}