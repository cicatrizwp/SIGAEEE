package dao;

import model.Posicao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PosicaoDAO {

    public static void inserir(Posicao posicao) throws SQLException, ClassNotFoundException {
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = BD.getConexao();
            String sql = "INSERT INTO `posicao` (`nome`, `esporte_id`) VALUES (?, ?);";
            comando = conexao.prepareStatement(sql);
            comando.setString(1, posicao.getNomePosicao());
            System.out.println("idEsporte" + posicao.getIdEsporte());
            comando.setInt(2, posicao.getIdEsporte());
            comando.execute();
            BD.fecharConexao(conexao, comando);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void alterar(Posicao posicao) throws SQLException, ClassNotFoundException {
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = BD.getConexao();
            String sql = "UPDATE posicao SET nome = ? WHERE id = ?";
            comando = conexao.prepareStatement(sql);
            comando.setString(1, posicao.getNomePosicao());
            comando.setInt(2, posicao.getIdPosicao());
            comando.execute();
            BD.fecharConexao(conexao, comando);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void excluir(Posicao posicao) throws SQLException, ClassNotFoundException {
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            System.out.println("CHEGOU NA EXCLUÇÂO");
            conexao = BD.getConexao();
            String sql = "DELETE FROM `posicao` WHERE id = ?";
            comando = conexao.prepareStatement(sql);
            comando.setInt(1, posicao.getIdPosicao());
            comando.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            BD.fecharConexao(conexao, comando);
        }
    }

    public static Posicao lerPosicao(Integer id) throws ClassNotFoundException {
        Connection conexao = null;
        PreparedStatement comando = null;
        Posicao posicao = null;

        try {
            conexao = BD.getConexao();
            String sql = "SELECT * FROM posicao WHERE id= ?";
            comando = conexao.prepareStatement(sql);
            comando.setInt(1, id);
            ResultSet rs = comando.executeQuery();
            rs.first();
            posicao = getFromDatabase(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BD.fecharConexao(conexao, comando);
        }
        return posicao;
    }

    public static List<Posicao> lerTodasPosicoes() throws ClassNotFoundException {
        Connection conexao = null;
        Statement comando = null;
        List<Posicao> posicoes = new ArrayList<Posicao>();
        try {
            conexao = BD.getConexao();
            comando = conexao.createStatement();
            String sql = "SELECT * FROM posicao";
            ResultSet rs = comando.executeQuery(sql);
            while (rs.next()) {
                posicoes.add(getFromDatabase(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BD.fecharConexao(conexao, comando);
        }
        return posicoes;
    }

    private static Posicao getFromDatabase(ResultSet rs) throws SQLException {
        return new Posicao(rs.getInt("esporte_id"),
                rs.getInt("id"),
                rs.getString("nome")
        );
    }
}
