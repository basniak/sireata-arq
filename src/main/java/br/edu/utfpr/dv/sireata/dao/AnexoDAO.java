package br.edu.utfpr.dv.sireata.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.dv.sireata.model.Anexo;

public class AnexoDAO {
	
	public Anexo buscarPorId(int id) throws SQLException{
		
		//*Objetivo - Tornar o código menos repetitivo.
		// Foi criada uma classe chamada DAOINIT que tem como objetivo a criação e inicialização das classes Connection, Statement e ResultSet.
		// O encerramento das conexões após o termino da operação também foi transferido para a nova classe DAOINIT, tornando o código menos repetitivo. referencia https://refactoring.guru/extract-method*/
		
		
                
            DAOINIT daoinit = new DAOINIT();
		
		try{
			daoinit.setConn(ConnectionDAO.getInstance().getConnection());
			daoinit.setStmt(daoinit.getConn().prepareStatement("SELECT anexos.* FROM anexos " +
				"WHERE idAnexo = ?"));
		
			daoinit.stmt.setInt(1, id);
			
                        daoinit.setRs(daoinit.stmt.executeQuery());
                        
		
			
			if(daoinit.rs.next()){
				return this.carregarObjeto(daoinit.rs);
			}else{
				return null;
			}
		}finally{
                    
                    daoinit.close();
                    
		
		}
	}
	
	public List<Anexo> listarPorAta(int idAta) throws SQLException{

		  DAOINIT daoinit = new DAOINIT();
		try{
			
                    daoinit.setConn(ConnectionDAO.getInstance().getConnection());
             
                    daoinit.setStmt((PreparedStatement) daoinit.conn.createStatement());
                   
		daoinit.setRs(daoinit.stmt.executeQuery("SELECT anexos.* FROM anexos " +
				"WHERE idAta=" + String.valueOf(idAta) + " ORDER BY anexos.ordem"));
		
			List<Anexo> list = new ArrayList<Anexo>();
			
			while(daoinit.rs.next()){
				list.add(this.carregarObjeto(daoinit.rs));
			}
			
			return list;
		}finally{
			
                    daoinit.close();
                    
		}
	}
	
	public int salvar(Anexo anexo) throws SQLException{
		boolean insert = (anexo.getIdAnexo() == 0);
		
		
                 DAOINIT daoinit = new DAOINIT();
                 
             
                
                
		try{
		    daoinit.setConn(ConnectionDAO.getInstance().getConnection());
		
			if(insert){
				

daoinit.setStmt(daoinit.conn.prepareStatement("INSERT INTO anexos(idAta, ordem, descricao, arquivo) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS));


			}else{
                            
                            daoinit.setStmt(daoinit.conn.prepareStatement("UPDATE anexos SET idAta=?, ordem=?, descricao=?, arquivo=? WHERE idAnexo=?"));

			}
			
			daoinit.stmt.setInt(1, anexo.getAta().getIdAta());
			daoinit.stmt.setInt(2, anexo.getOrdem());
			daoinit.stmt.setString(3, anexo.getDescricao());
			daoinit.stmt.setBytes(4, anexo.getArquivo());
			
			if(!insert){
				daoinit.stmt.setInt(5, anexo.getIdAnexo());
			}
			
			daoinit.stmt.execute();
			
			if(insert){
                            
                            daoinit.setRs(daoinit.stmt.getGeneratedKeys());
				
				
				if(daoinit.rs.next()){
                                    
					anexo.setIdAnexo(daoinit.rs.getInt(1));
				}
			}
			
			return anexo.getIdAnexo();
		}finally{
			daoinit.close();
                    
		}
	}
	
	public void excluir(int id) throws SQLException{

		
                   DAOINIT daoinit = new DAOINIT();
                
		try{
			daoinit.setConn(ConnectionDAO.getInstance().getConnection());
			daoinit.setStmt((PreparedStatement) daoinit.conn.createStatement());
                        
                     
		
			daoinit.stmt.execute("DELETE FROM anexos WHERE idanexo=" + String.valueOf(id));
		}finally{
		
                    daoinit.close();
                    
		}
	}
	
	private Anexo carregarObjeto(ResultSet rs) throws SQLException{
		Anexo anexo = new Anexo();
		
		anexo.setIdAnexo(rs.getInt("idAnexo"));
		anexo.getAta().setIdAta(rs.getInt("idAta"));
		anexo.setDescricao(rs.getString("descricao"));
		anexo.setOrdem(rs.getInt("ordem"));
		anexo.setArquivo(rs.getBytes("arquivo"));
		
		return anexo;
	}

}
