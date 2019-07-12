package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Alberto Teodoro de Arruda
 */
public class Database {
    public static final String URL = "jdbc:postgresql://localhost:5432/";
    public static final String DATABASE = "easycut";
    public static final String USER = "luiz";
    public static final String PASSWORD = "Sly1989";
    private Connection conn;
    private Statement state;
    private final String sql;

    public Database() {
        conn = null;
        state = null;
        sql =
                "CREATE TABLE IF NOT EXISTS public.cliente ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nome character varying(40) NOT NULL,"
                    + "telefone character varying(11) NOT NULL UNIQUE"
                + ");"
                + "CREATE TABLE IF NOT EXISTS public.funcionario ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nome character varying(40) NOT NULL UNIQUE"
                + ");"
                + "CREATE TABLE IF NOT EXISTS public.servico ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nome character varying(20) NOT NULL UNIQUE,"
                    + "preco NUMERIC (6, 2) NOT NULL"
                + ");"
                + "CREATE TABLE IF NOT EXISTS public.funcionario_servico ("
                    + "funcionario_id integer NOT NULL "
                        + "REFERENCES public.funcionario(id) ON DELETE CASCADE,"
                    + "servico_id integer NOT NULL "
                        + "REFERENCES public.servico(id) ON DELETE CASCADE"
                + ");"
                + "CREATE TABLE IF NOT EXISTS public.reserva ("
                    + "id SERIAL PRIMARY KEY,"
                    + "horario timestamp without time zone NOT NULL,"
                    + "servico_id integer NOT NULL "
                        + "REFERENCES public.servico(id) ON DELETE CASCADE,"
                    + "funcionario_id integer NOT NULL "
                        + "REFERENCES public.funcionario(id) ON DELETE CASCADE,"
                    + "cliente_id integer NOT NULL "
                        + "REFERENCES public.cliente(id) ON DELETE CASCADE"
                + ");";
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL + DATABASE, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(
                    Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean create() {
        try {
            conn = DriverManager.getConnection(URL + DATABASE, USER, PASSWORD);
            state = conn.createStatement();
            state.executeUpdate(sql);
            state.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(
                    Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}