package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Servico;

public class ServicoDAO {
    public Connection conn;
    public PreparedStatement state;
    public ResultSet rs;

    public ServicoDAO() {
        conn = null;
        state = null;
        rs = null;
    }

    public int insert(Servico servico) {
        String sql = "INSERT INTO servico (nome, preco) VALUES (?, ?);";
        int id = -1;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            state.setString(1, servico.getNome());
            state.setFloat(2, servico.getPreco());
            state.executeUpdate();
            rs = state.getGeneratedKeys();

            if (rs.next())
                id = rs.getInt("id");
            
            state.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            return id;
        }
    }

    public void delete(Servico servico) {
        String sql = "DELETE FROM servico WHERE ID = ?;";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, servico.getId());
            state.executeUpdate();
            state.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Servico servico) {
        String sql = "UPDATE servico SET nome = ?, preco = ? WHERE id = ?;";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setString(1, servico.getNome());
            state.setFloat(2, servico.getPreco());
            state.setInt(3, servico.getId());
            state.executeUpdate();
            state.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Servico queryForId(int id) {
        String sql = "SELECT * FROM servico WHERE id = ?;";
        Servico servico = null;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, id);
            state.executeQuery();
            rs = state.getResultSet();

            if (rs.next()) {
                servico = new Servico(rs.getString("nome"),
                                      rs.getFloat("preco"));
                servico.setId(rs.getInt("id"));
            }
            
            state.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            return servico;
        }
    }

    public ArrayList<Servico> queryAll() {
        String sql = "SELECT * FROM servico;";
        ArrayList<Servico> servicos = null;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.executeQuery();
            rs = state.getResultSet();
            servicos = new ArrayList<>();

            while (rs.next()) {
                Servico servico = new Servico(rs.getString("nome"),
                                              rs.getFloat("preco"));
                servico.setId(rs.getInt("id"));
                servicos.add(servico);
            }
            
            state.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return servicos;
    }
    
    public ArrayList<Servico> queryForString(String string) {
        String sql = "SELECT * FROM servico where nome ILIKE ?;";
        ArrayList<Servico> servicos = null;
        string = "%" + string + "%";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setString(1, string);
            state.executeQuery();
            rs = state.getResultSet();
            servicos = new ArrayList<>();

            while (rs.next()) {
                Servico servico = new Servico(rs.getString("nome"),
                                              rs.getFloat("preco"));
                servico.setId(rs.getInt("id"));
                servicos.add(servico);
            }
            
            state.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return servicos;
    }
}