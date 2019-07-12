package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;

public class FuncionarioDAO {
    public Connection conn;
    public PreparedStatement state;
    public ResultSet rs;

    public FuncionarioDAO() {
        conn = null;
        state = null;
        rs = null;
    }
    
    public int insert(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (nome) VALUES (?);";
        int id = -1;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            state.setString(1, funcionario.getNome());
            state.executeUpdate();
            rs = state.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException ex) {
            Logger.getLogger(
                    FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            return id;
        }
    }

    public void delete(Funcionario funcionario) {
        String sql = "DELETE FROM funcionario WHERE id = ?;";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, funcionario.getId());
            state.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(
                    FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome = ? WHERE id = ?;";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setString(1, funcionario.getNome());
            state.setInt(2, funcionario.getId());
            state.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(
                    FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Funcionario queryForId(int id) {
        String sql = "SELECT * FROM funcionario WHERE id = ?;";
        Funcionario funcionario = null;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, id);
            state.executeQuery();
            rs = state.getResultSet();

            if (rs.next()) {
                funcionario = new Funcionario(rs.getString("nome"));
                funcionario.setId(rs.getInt("id"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(
                    FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            return funcionario;
        }
    }

    public ArrayList<Funcionario> queryAll() {
        String sql = "SELECT * FROM funcionario;";
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.executeQuery();
            rs = state.getResultSet();

            while (rs.next()) {
                Funcionario funcionario = new Funcionario(rs.getString("nome"));
                funcionario.setId(rs.getInt("id"));
                funcionarios.add(funcionario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(
                    FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return funcionarios;
    }
    
    public ArrayList<Funcionario> queryForString(String string) {
        String sql = "SELECT * FROM funcionario where nome ILIKE ?;";
        ArrayList<Funcionario> funcionarios = null;
        string = "%" + string + "%";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setString(1, string);
            state.executeQuery();
            rs = state.getResultSet();
            funcionarios = new ArrayList<>();

            while (rs.next()) {
                Funcionario funcionario = new Funcionario(rs.getString("nome"));
                funcionario.setId(rs.getInt("id"));
                funcionarios.add(funcionario);
            }
            
            state.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return funcionarios;
    }
}