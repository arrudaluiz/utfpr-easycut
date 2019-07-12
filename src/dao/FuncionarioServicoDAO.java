package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;
import model.Servico;

/**
 *
 * @author Luiz Alberto Teodoro de Arruda
 */
public class FuncionarioServicoDAO {
    public Connection conn;
    public PreparedStatement state;
    public ResultSet rs;

    public FuncionarioServicoDAO() {
        conn = null;
        state = null;
        rs = null;
    }

    public void insert(Funcionario funcionario, Servico servico) {
        String sql = "INSERT INTO funcionario_servico "
                + "(funcionario_id, servico_id) VALUES (?, ?);";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, funcionario.getId());
            state.setInt(2, servico.getId());
            state.executeUpdate();
            state.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioServicoDAO.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Funcionario funcionario, Servico servico) {
        String sql = "DELETE FROM funcionario_servico "
                + "WHERE funcionario_id = ? AND servico_id = ?;";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, funcionario.getId());
            state.setInt(2, servico.getId());
            state.executeUpdate();
            state.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioServicoDAO.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Servico> queryForFuncionario(Funcionario funcionario) {
        String sql = "SELECT s.id, s.nome, s.preco FROM servico AS s, "
                + "funcionario_servico AS fs, funcionario AS f WHERE f.id = ? "
                + "AND f.id = fs.funcionario_id AND fs.servico_id = s.id;";
        ArrayList<Servico> servicos = null;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, funcionario.getId());
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
                    FuncionarioServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return servicos;
    }
    
    public ArrayList<Funcionario> queryForServico(Servico servico) {
        String sql = "SELECT f.id, f.nome FROM funcionario AS f, "
                + "funcionario_servico AS fs, servico AS s WHERE s.id = ? "
                + "AND s.id = fs.servico_id AND fs.funcionario_id = f.id;";
        ArrayList<Funcionario> funcionarios = null;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, servico.getId());
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
                    FuncionarioServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return funcionarios;
    }
}