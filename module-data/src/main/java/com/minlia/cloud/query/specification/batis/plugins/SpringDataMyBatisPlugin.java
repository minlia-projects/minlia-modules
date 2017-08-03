package com.minlia.cloud.query.specification.batis.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * MyBatis plugin which allows functions of the form
 *  
 *
 * @author sheenobu
 *
 */
//ISSUE 1: bad query transformation.
//ISSUE 2: The function must use PageI instead of Page, otherwise mybatis will choke.
@Intercepts({ @Signature(type = Executor.class, method = "query", args =
        { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class } )})
public class SpringDataMyBatisPlugin implements Interceptor {

	@Override
	public Object intercept(Invocation arg0) throws Throwable {
		boolean pageable = false;
		Pageable pageableObject = null;
		MappedStatement stmt = null;
				
		for(Object o : arg0.getArgs())
		{
			if(o != null)
			{
				if(Pageable.class.isAssignableFrom(o.getClass())) {
					pageable = true;
					pageableObject = (Pageable) o;					
				}
				if(o instanceof MappedStatement)
				{
					stmt = (MappedStatement)o;					
				}
			}
		}
		
		Object ret = arg0.proceed();

		if(pageable && pageableObject != null && stmt != null) {
			int total  = -1;
			
			Executor exec = (Executor) arg0.getTarget();
			Statement sqlstmt = exec.getTransaction().getConnection().createStatement();
			
			
			// This is a bit shady. I might want to replace it by finding a similar function on the same
			//   mapper. For example: FunctionA => countOfFunctionA
			
			//Convert the select ... from ... statement to a count statement. 
			//TODO: remove paginating LIMITS and such.
			ResultSet s = sqlstmt.executeQuery(stmt.getSqlSource().getBoundSql(null).getSql().replace('\n', ' ')
					.replaceFirst("[S|s][E|e][L|l][E|e][C|c][T\t]\\s+.*[F|f][R|r][O|o][M|m]","SELECT count(*) from "));
			
			s.next();
			total = s.getShort(1);

			PageImpl  pi = new PageImpl((List)ret, pageableObject,total);
			return pi;
		}

		return ret;
	}

	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0,(Interceptor) this);
	}

	@Override
	public void setProperties(Properties arg0) {
		
	}
}