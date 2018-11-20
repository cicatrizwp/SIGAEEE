/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Atleta;
import model.Equipe;
import model.Evento;
import model.Gestor;
import model.Logar;

/**
 *
 * @author Familia
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String acao = request.getParameter("acao");
            if (acao.equals("logar")) {
                request.setAttribute("gestores", Gestor.lerTodosGestores());
                logar(request, response);
            } else if(acao.equals("deslogar")) {
                RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
                view.forward(request, response);
            }
        }
    }

    private void logar(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String email = request.getParameter("txtEmail");
        String senha = request.getParameter("txtPwd");
        System.out.println(senha);
        Logar logar = new Logar(email, senha);
        System.out.println(logar.verificar());
        if (logar.verificar() == "gestor") {
            List<Equipe> equipes = new ArrayList<Equipe>();
            List<Gestor> gestores = new ArrayList<Gestor>();
            List<Atleta> atletas = new ArrayList<Atleta>();
            List<Evento> eventos = new ArrayList<Evento>();
            Equipe.lerTodasEquipes().forEach(equipe -> equipes.add(equipe));
            Gestor.lerTodosGestores().forEach(gestor -> gestores.add(gestor));
            Atleta.lerTodosAtletas().forEach(atleta -> atletas.add(atleta));
            Evento.lerTodosEventos().forEach(evento -> eventos.add(evento));
            Gestor gestor = null;
            Equipe equipe = null;
            int numeroAtletas = 0;
            int numeroEventos = 0;
            for (Gestor g : gestores) {
                if (g.getEmail().equals(email)) {
                    gestor = new Gestor(g.getIdUsuario(), g.getNomeUsuario(), g.getEmail(), g.getSenha(), g.getIdGestor());
                }
            }
            for (Equipe e : equipes) {
                if (e.getIdGestor().equals(gestor.getIdGestor())) {
                    equipe = new Equipe(e.getIdEquipe(), e.getIdGestor(),e.getNomeEquipe(), e.getLogo(), e.getPlaybook(), e.getIdEsporte());
                }
            }
            for (Atleta a : atletas) {
                int x = a.getEquipe();
               if (equipe.getIdEquipe() == x) {
                    numeroAtletas++;
                   
                }
            }
            for (Evento e : eventos) {
                int x = e.getIdEquipe();
               if (equipe.getIdEquipe() == x) {
                    numeroEventos++;
                   
                }
            }
            
            request.setAttribute("eventos", numeroEventos);
            request.setAttribute("atletas", numeroAtletas);
            request.setAttribute("gestor", gestor);
            request.setAttribute("equipe", equipe);
            System.out.println(equipe.getNomeEquipe()+"  aqui "+gestor.getNomeUsuario()+"  "+numeroAtletas+"  "+ numeroEventos);
            RequestDispatcher view = request.getRequestDispatcher("/PainelGestor");
            view.forward(request, response);
        } else {
        }
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
