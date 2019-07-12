package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Reserva;

public class ReservaDAO {
    public Connection conn;
    public PreparedStatement state;
    public ResultSet rs;

    public ReservaDAO() {
        conn = null;
        state = null;
        rs = null;
    }

    public int insert(Reserva reserva) {
        String sql = "INSERT INTO reserva (horario, servico_id, "
                + "funcionario_id, cliente_id) VALUES (?, ?, ?, ?);";
        int id = -1;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            state.setTimestamp(1, reserva.getHorario());
            state.setInt(2, reserva.getServicoId());
            state.setInt(3, reserva.getFuncionarioId());
            state.setInt(4, reserva.getClienteId());
            state.executeUpdate();
            rs = state.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException ex) {
            Logger.getLogger(
                    ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            return id;
        }
    }

    public void delete(Reserva reserva) {
        String sql = "DELETE FROM reserva WHERE id = ?;";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, reserva.getId());
            state.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(Reserva reserva) {
        String sql = "UPDATE reserva SET horario = ?, servico_id = ?, "
                + "funcionario_id = ?, cliente_id = ? WHERE id = ?;";

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setTimestamp(1, reserva.getHorario());
            state.setInt(2, reserva.getServicoId());
            state.setInt(3, reserva.getFuncionarioId());
            state.setInt(4, reserva.getClienteId());
            state.setInt(5, reserva.getId());
            state.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(
                    ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Reserva queryForId(int id) {
        String sql = "SELECT * FROM reserva WHERE id = ?;";
        Reserva reserva = null;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, id);
            state.executeQuery();
            rs = state.getResultSet();

            if (rs.next()) {
                reserva = new Reserva(rs.getTimestamp("horario"),
                                      rs.getInt("servico_id"),
                                      rs.getInt("funcionario_id"),
                                      rs.getInt("cliente_id"));
                reserva.setId(rs.getInt("id"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(
                    ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            return reserva;
        }
    }

    public ArrayList<Reserva> queryAll() {
        String sql = "SELECT * FROM reserva;";
        ArrayList<Reserva> reservas = null;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.executeQuery();
            rs = state.getResultSet();
            reservas = new ArrayList<>();

            while (rs.next()) {
                Reserva reserva = new Reserva(rs.getTimestamp("horario"),
                                              rs.getInt("servico_id"),
                                              rs.getInt("funcionario_id"),
                                              rs.getInt("cliente_id"));
                reserva.setId(rs.getInt("id"));
                reservas.add(reserva);
            }

        } catch (SQLException ex) {
            Logger.getLogger(
                    ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return reservas;
    }
    
    public Object[] queryStringsForId(int id) {
        String sql = "SELECT to_char(r.horario, 'YYYY-MM-DD') AS horario_data, "
                + "to_char(r.horario, 'HH24') AS hora, c.nome AS cliente, "
                + "s.nome AS servico, f.nome AS funcionario FROM reserva AS r, "
                + "servico AS s, funcionario AS f, cliente AS c "
                + "WHERE r.id = ? AND r.servico_id = s.id "
                + "AND r.funcionario_id = f.id AND r.cliente_id = c.id;";
        Object[] reserva = null;

        try {
            conn = new Database().getConnection();
            state = conn.prepareStatement(sql);
            state.setInt(1, id);
            state.executeQuery();
            rs = state.getResultSet();

            if (rs.next()) {
                reserva = new Object[] {
                    rs.getString("horario_data"),
                    rs.getString("horario") + ":00",
                    rs.getString("cliente"),
                    rs.getString("funcionario"),
                    rs.getString("servico"),
                };
            }

        } catch (SQLException ex) {
            Logger.getLogger(
                    ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return reserva;
    }
}