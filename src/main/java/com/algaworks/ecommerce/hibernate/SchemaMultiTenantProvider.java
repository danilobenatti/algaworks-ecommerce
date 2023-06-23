package com.algaworks.ecommerce.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.service.spi.Startable;

public class SchemaMultiTenantProvider implements MultiTenantConnectionProvider,
	ServiceRegistryAwareService, Startable {
	private static final long serialVersionUID = 1L;
	
	private transient Map<String, Object> properties = null;
	
	private ConnectionProvider connectionProvider = null;
	
	@Override
	public boolean isUnwrappableAs(Class<?> unwrapType) {
		return connectionProvider.isUnwrappableAs(unwrapType);
	}
	
	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return connectionProvider.unwrap(unwrapType);
	}
	
	@Override
	public void start() {
		HikariCPConnectionProvider hcp = new HikariCPConnectionProvider();
		hcp.configure(this.properties);
		connectionProvider = hcp;
	}
	
	@Override
	public void injectServices(ServiceRegistryImplementor serviceRegistry) {
		this.properties = serviceRegistry.getService(ConfigurationService.class)
			.getSettings();
	}
	
	@Override
	public Connection getAnyConnection() throws SQLException {
		return connectionProvider.getConnection();
	}
	
	@Override
	public void releaseAnyConnection(Connection connection)
		throws SQLException {
		connectionProvider.closeConnection(connection);
	}
	
	@Override
	public Connection getConnection(String tenantIdentifier)
		throws SQLException {
		try (Statement statement = getAnyConnection().createStatement()) {
			statement.execute("use " + tenantIdentifier);
			return statement.getConnection();
		} catch (SQLException ex) {
			throw new HibernateException(
				"Unable change to schema " + tenantIdentifier, ex);
		}
	}
	
	@Override
	public void releaseConnection(String tenantIdentifier,
		Connection connection) throws SQLException {
		releaseAnyConnection(connection);
	}
	
	@Override
	public boolean supportsAggressiveRelease() {
		return connectionProvider.supportsAggressiveRelease();
	}
	
}
