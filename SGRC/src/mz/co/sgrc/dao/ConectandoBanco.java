package mz.co.sgrc.dao;

import java.sql.*;

import javax.swing.JOptionPane;


public class ConectandoBanco {

	public Statement stm;
	public ResultSet re;
	private String driver = "com.mysql.jdbc.Driver";
	private String caminho = "jdbc:mysql://localhost/vendas";
	private String usuario = "root";
	private String senha = null;
	public Connection conn;
	
	public void conexao(){
		System.setProperty("jdbc.Driver", driver);
		try {
			conn = DriverManager.getConnection(caminho, usuario, senha);
			JOptionPane.showMessageDialog(null, "Conectado com sucesso!");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errode de conexao!"+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void desconecta(){
		try {
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao fechar a conexao!"+ e.getMessage());
			e.printStackTrace();
		}
	}
}
