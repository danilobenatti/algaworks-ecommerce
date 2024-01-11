package com.algaworks.ecommerce.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.service.spi.Startable;

public class MachineMultiTenantProvider implements
	MultiTenantConnectionProvider<Object>, ServiceRegistryAwareService, Startable {
	private static final long serialVersionUID = 1L;
	
	private transient Map<String, Object> properties = null;
	
	private Map<String, ConnectionProvider> connectionProviders = null;
	
	@Override
	public boolean isUnwrappableAs(Class<?> unwrapType) {
		return getAnyConnectionProvider().isUnwrappableAs(unwrapType);
	}
	
	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return getAnyConnectionProvider().unwrap(unwrapType);
	}
	
	@Override
	public void start() {
		connectionProviders = new HashMap<>();
		
		configureTenant("algaworks_ecommerce",
			"jdbc:mysql://localhost:3306/algaworks_ecommerce?"
				+ "createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC"
				+ "&useUnicode=true&connectionCollation=utf8_bin&characterSetResults=utf8",
			"root", "123456");
		
		configureTenant("ecosensor_ecommerce",
			"jdbc:mysql://127.0.0.1/ecosensor_ecommerce?"
				+ "createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC"
				+ "&useUnicode=true&connectionCollation=utf8_bin&characterSetResults=utf8",
			"root", "123456");
		
		this.properties = null;
	}
	
	@Override
	public void injectServices(ServiceRegistryImplementor serviceRegistry) {
		this.properties = serviceRegistry.getService(ConfigurationService.class)
			.getSettings();
		
	}
	
	@Override
	public Connection getAnyConnection() throws SQLException {
		return getAnyConnectionProvider().getConnection();
	}
	
	@Override
	public void releaseAnyConnection(Connection connection)
		throws SQLException {
		getAnyConnectionProvider().closeConnection(connection);
		connection.close();
		
	}
	
	@Override
	public Connection getConnection(Object tenantIdentifier)
		throws SQLException {
		return connectionProviders.get(tenantIdentifier).getConnection();
	}
	
	@Override
	public void releaseConnection(Object tenantIdentifier,
		Connection connection) throws SQLException {
		releaseAnyConnection(connection);
		
	}
	
	@Override
	public boolean supportsAggressiveRelease() {
		return getAnyConnectionProvider().supportsAggressiveRelease();
	}
	
	private void configureTenant(String tenant, String url, String username,
		String password) {
		Map<String, Object> props = new HashMap<>(this.properties);
		
		/**
		 * props.put("jakarta.persistence.jdbc.url", url);
		 * props.put("jakarta.persistence.jdbc.user", username);
		 * props.put("jakarta.persistence.jdbc.password", password);
		 */
		props.put("hibernate.connection.url", url);
		props.put("hibernate.connection.username", username);
		props.put("hibernate.connection.password", password);
		
		HikariCPConnectionProvider cp = new HikariCPConnectionProvider();
		cp.configure(props);
		
		this.connectionProviders.put(tenant, cp);
	}
	
	private ConnectionProvider getAnyConnectionProvider() {
		return connectionProviders.values().iterator().next();
	}
	
}
