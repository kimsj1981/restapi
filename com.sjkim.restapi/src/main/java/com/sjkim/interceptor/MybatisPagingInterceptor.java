package com.sjkim.interceptor;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sjkim.base.AbstractDto;

@Intercepts({
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }) })
public class MybatisPagingInterceptor implements Interceptor {

	Logger logger = LoggerFactory.getLogger(getClass());

	private static int MAPPED_STATEMENT_INDEX = 0;
	private static int PARAMETER_INDEX = 1;

	public static class BoundSqlSqlSource implements SqlSource {

		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		@Override
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		paginateWithRowNum(invocation.getArgs());
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	private void paginateWithRowNum(Object[] args) {
		if (args[PARAMETER_INDEX] instanceof AbstractDto) {
			AbstractDto dto = (AbstractDto) args[PARAMETER_INDEX];
			if (getIsWithRowNum(dto)) {
				MappedStatement ms = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
				BoundSql boundSql = ms.getBoundSql(dto);
				String sql = boundSql.getSql().trim();
				if (logger.isDebugEnabled()) {
					logger.debug("sql : " + sql);
				}

				String sqlWithRowNum = getSqlWithRowNum(sql, dto);
				if (logger.isDebugEnabled()) {
					logger.debug("sqlWithRowNum : " + sqlWithRowNum);
				}
				BoundSql boundSqlWithRowNum = getBoundSqlWithRowNum(ms, boundSql, sqlWithRowNum,
						boundSql.getParameterMappings(), dto);
				MappedStatement msWithRowNum = getMappedStatementWithRowNum(ms,
						new BoundSqlSqlSource(boundSqlWithRowNum));
				args[MAPPED_STATEMENT_INDEX] = msWithRowNum;
			}
		}
	}

	private Boolean getIsWithRowNum(AbstractDto dto) {
		return (dto.getEndRowNum() != null || (dto.getStartRowNum() != null && dto.getEndRowNum() != null));
	}

	private String getSqlWithRowNum(String sql, AbstractDto dto) {
		StringBuffer sb = new StringBuffer();
		if (null != dto.getEndRowNum()) {
			sb.append("SELECT * FROM ( ");
			if (null != dto.getStartRowNum()) {
				sb.append("SELECT ROWNUM AS RNUM , A.* FROM ( ");
			}
		}
		sb.append(sql);
		if (dto.getStartRowNum() != null && dto.getEndRowNum() != null) {
			sb.append(" ) A WHERE ROWNUM <= " + dto.getEndRowNum() + " ) WHERE RNUM > " + dto.getStartRowNum());
		} else {
			sb.append(" ) WHERE ROWNUM <= " + dto.getEndRowNum());
		}
		return sb.toString();
	}

	private BoundSql getBoundSqlWithRowNum(MappedStatement ms, BoundSql boundSql, String sql,
			List<ParameterMapping> parameterMappings, Object parameter) {
		BoundSql boundSqlWithRowNum = new BoundSql(ms.getConfiguration(), sql, parameterMappings, parameter);
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				boundSqlWithRowNum.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}
		return boundSqlWithRowNum;
	}

	private MappedStatement getMappedStatementWithRowNum(MappedStatement ms, SqlSource sqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), sqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
			StringBuffer keyProperties = new StringBuffer();
			for (String keyProperty : ms.getKeyProperties()) {
				keyProperties.append(keyProperty).append(",");
			}
			keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		return builder.build();
	}
}