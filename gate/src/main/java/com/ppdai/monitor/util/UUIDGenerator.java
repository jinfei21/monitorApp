package com.ppdai.monitor.util;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;  

public class UUIDGenerator implements IdentifierGenerator {
	
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		
	}
	
	
	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		return  UUID.randomUUID().toString();
	}


}
