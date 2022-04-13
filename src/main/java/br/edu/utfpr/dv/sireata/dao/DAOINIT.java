package br.edu.utfpr.dv.sireata.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


//https://refactoring.guru/extract-method




public class DAOINIT {
    
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public DAOINIT() {
        
        
        
    }
    
    
    

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public PreparedStatement getStmt() {
        return stmt;
    }

    public void setStmt(PreparedStatement stmt) {
        this.stmt = stmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
    
    public void close(){
        
        
        try {
            if((rs != null) && !rs.isClosed()){
            rs.close();
            }
            if((stmt != null) && !stmt.isClosed()){
            stmt.close();
            }
			
            if((conn != null) && !conn.isClosed()){
            conn.close();
            }
                        
        } catch (SQLException ex) {
            Logger.getLogger(DAOINIT.class.getName()).log(Level.SEVERE, null, ex);
        }
			
        
    }
    
    
    
    
    
}
