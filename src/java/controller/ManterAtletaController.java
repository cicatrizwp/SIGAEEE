package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import model.Atleta;
import model.Equipe;
import model.Gestor;
import model.Posicao;


public class ManterAtletaController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, ClassNotFoundException, IOException {
        String acao = request.getParameter("acao");
        String operacao = request.getParameter("operacao");
        request.setAttribute("operacao", operacao);
        request.setAttribute("acao", acao);
        System.out.println("AQUI");
        if (acao.equals("confirmarOperacao")) {
            confirmarOperacao(request, response);
        } else if (acao.equals("prepararOperacao")) {
        System.out.println("AQUI2");
            prepararOperacao(request, response);
        }
    }

    public void prepararOperacao(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, ClassNotFoundException {
        try {
            String operacao = request.getParameter("operacao");
            request.setAttribute("operacao", operacao);
            request.setAttribute("atletas", Atleta.lerTodosAtletas());
            if (!operacao.equals("Incluir")) {
                int id = Integer.parseInt(request.getParameter("idAtleta"));
                Atleta atleta = Atleta.lerAtleta(id);
                request.setAttribute("atleta", atleta);
            }
            request.setAttribute("posicoes", Posicao.lerTodasPosicoes());
            request.setAttribute("equipes", Equipe.lerTodasEquipes());
            RequestDispatcher view = request.getRequestDispatcher("/cadastroAtleta.jsp");
            view.forward(request, response);
        } catch (ServletException e) {
            throw e;
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }

    public void confirmarOperacao(HttpServletRequest request, HttpServletResponse response) throws ServletException, ClassNotFoundException, SQLException, IOException {
        String operacao = request.getParameter("operacao");
        String nome = request.getParameter("txtNomeAtleta");
        int id = parseInt(request.getParameter("txtIdAtleta"));
        int posicao = parseInt(request.getParameter("intIdEquipe"));
        int equipe = parseInt(request.getParameter("intIdPosicao"));
        int idUsuario = 0;
        String email = request.getParameter("txtEmailAtleta");
        String senha = request.getParameter("txtSenhaAtleta");
        String data = request.getParameter("txtDataNascimentoAtleta");
        float altura = parseFloat(request.getParameter("txtAlturaAtleta"));
        float peso = parseFloat(request.getParameter("txtPesoAtleta"));
        Atleta atleta = new Atleta(id, peso, altura, data, equipe, posicao, idUsuario,nome,email,senha);
        switch (operacao) {
            case "Incluir":
                atleta.inserir();
                break;
            case "Editar":
                atleta.alterar();
                break;
            case "Excluir":
                System.out.println("Excluir"+atleta.getIdAtleta());
                atleta.excluir();
                break;
            default:
                break;
        }
        request.getRequestDispatcher("PesquisaAtletaController").forward(request, response);
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManterEquipeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManterEquipeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManterEquipeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManterEquipeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
