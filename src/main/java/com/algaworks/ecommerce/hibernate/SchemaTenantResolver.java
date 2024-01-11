package com.algaworks.ecommerce.hibernate;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class SchemaTenantResolver implements CurrentTenantIdentifierResolver<Object> {
	
	private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
	
	public static void setTenantIdentifier(String tenantId) {
		threadLocal.set(tenantId);
	}
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		return threadLocal.get();
	}
	
	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}
	
	public static void unload() {
		threadLocal.remove();
	}
}
