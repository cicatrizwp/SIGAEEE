package model;

import dao.EquipeDAO;
import java.sql.SQLException;
import java.util.List;

public class Equipe {

    public static Object lerEquipe(Integer id) throws ClassNotFoundException {
        return EquipeDAO.lerEquipe(id);
    }

    private int idEquipe;
    private Integer idGestor;
    private String nomeEquipe;
    private String logo;
    private String playbook;
    private Esporte esporte;
    private int idEsporte;

    public Equipe(int idEquipe,int idGestor, String nomeEquipe, String logo, String playbook, Esporte esporte) {
        this.setIdEquipe(idEquipe);
        this.setNomeEquipe(nomeEquipe);
        this.setEsporte(esporte);
        this.setLogo(logo);
        this.setPlaybook(playbook);
        this.setIdEsporte(esporte.getIdEsporte());
        this.setIdGestor(idGestor);
    }

    public Integer getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(Integer idGestor) {
        this.idGestor = idGestor;
    }

    public int getIdEsporte() {
        return idEsporte;
    }

    public void setIdEsporte(int idEsporte) {
        this.idEsporte = idEsporte;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPlaybook() {
        return playbook;
    }

    public void setPlaybook(String playbook) {
        this.playbook = playbook;
    }

    public Equipe(int aInt,Integer idGestor, String string, String logo, String playbook, int esporte_id) {
        this.setIdEquipe(aInt);
        this.setNomeEquipe(string);
        this.setLogo(logo);
        this.setPlaybook(playbook);
        this.idEsporte = esporte_id;
        this.setIdGestor(idGestor);
    }

    public Esporte getEsporte() {
        return esporte;
    }

    public void setEsporte(Esporte esporte) {
        this.esporte = esporte;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public void inserir() throws SQLException, ClassNotFoundException {
        EquipeDAO.inserir(this);
    }

    public static List<Equipe> lerTodasEquipes() throws ClassNotFoundException, SQLException {
        return EquipeDAO.lerTodasEquipes();
    }

    public void alterar() throws SQLException, ClassNotFoundException {
        EquipeDAO.alterar(this);
    }

    public void excluir() throws SQLException, ClassNotFoundException{
        EquipeDAO.excluir(this);
    }
}
