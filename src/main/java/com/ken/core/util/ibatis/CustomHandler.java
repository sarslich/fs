package com.ken.core.util.ibatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;



public class CustomHandler extends BaseTypeHandler<Boolean> {

	@Override
	public Boolean getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
        String value = rs.getString(columnName);
        if (value != null && Integer.valueOf(value).intValue() == 1) {
          return new Boolean(true);
        }else{
        	return false;
        } 
	}

	@Override
	public Boolean getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
        String value = rs.getString(columnIndex);
        if (value != null && Integer.valueOf(value).intValue() == 1) {
          return new Boolean(true);
        }else{
        	return false;
        } 
	}

	@Override
	public Boolean getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
        String value = cs.getString(columnIndex);
        if (value != null && Integer.valueOf(value).intValue() == 1) {
            return new Boolean(true);
          }else{
          	return false;
          } 

	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Boolean parameter, JdbcType arg3) throws SQLException {
		if (parameter.booleanValue())
			ps.setInt(i, Integer.valueOf("1"));
		else
			ps.setInt(i, Integer.valueOf("0"));
		
	}

 


}
