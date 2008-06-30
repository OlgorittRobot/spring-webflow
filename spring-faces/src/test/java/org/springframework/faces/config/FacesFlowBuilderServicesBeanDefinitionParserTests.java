package org.springframework.faces.config;

import junit.framework.TestCase;

import org.springframework.binding.convert.ConversionExecutionException;
import org.springframework.binding.convert.ConversionExecutor;
import org.springframework.binding.convert.ConversionService;
import org.springframework.binding.expression.Expression;
import org.springframework.binding.expression.ExpressionParser;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.faces.model.converter.FacesConversionService;
import org.springframework.faces.webflow.JSFMockHelper;
import org.springframework.faces.webflow.JsfManagedBeanAwareELExpressionParser;
import org.springframework.faces.webflow.JsfViewFactoryCreator;
import org.springframework.webflow.engine.builder.ViewFactoryCreator;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.ViewFactory;
import org.springframework.webflow.expression.el.WebFlowELExpressionParser;

public class FacesFlowBuilderServicesBeanDefinitionParserTests extends TestCase {

	private ClassPathXmlApplicationContext context;
	private FlowBuilderServices builderServices;
	private JSFMockHelper jsf = new JSFMockHelper();

	public void setUp() throws Exception {
		jsf.setUp();
		context = new ClassPathXmlApplicationContext("org/springframework/faces/config/flow-builder-services.xml");
	}

	protected void tearDown() throws Exception {
		jsf.tearDown();
	}

	public void testConfigureDefaults() {
		builderServices = (FlowBuilderServices) context.getBean("flowBuilderServicesDefault");
		assertNotNull(builderServices);
		assertTrue(builderServices.getExpressionParser() instanceof WebFlowELExpressionParser);
		assertTrue(builderServices.getViewFactoryCreator() instanceof JsfViewFactoryCreator);
		assertTrue(builderServices.getConversionService() instanceof FacesConversionService);
	}

	public void testEnableManagedBeans() {
		builderServices = (FlowBuilderServices) context.getBean("flowBuilderServicesLegacy");
		assertNotNull(builderServices);
		assertTrue(builderServices.getExpressionParser() instanceof JsfManagedBeanAwareELExpressionParser);
		assertTrue(builderServices.getViewFactoryCreator() instanceof JsfViewFactoryCreator);
		assertTrue(builderServices.getConversionService() instanceof FacesConversionService);
	}

	public void testFlowBuilderServicesCustomized() {
		builderServices = (FlowBuilderServices) context.getBean("flowBuilderServicesCustom");
		assertNotNull(builderServices);
		assertNotNull(builderServices.getExpressionParser());
		assertTrue(builderServices.getViewFactoryCreator() instanceof TestViewFactoryCreator);
		assertTrue(builderServices.getConversionService() instanceof TestConversionService);
	}

	public static class TestViewFactoryCreator implements ViewFactoryCreator {

		public ViewFactory createViewFactory(Expression viewIdExpression, ExpressionParser expressionParser,
				ConversionService conversionService) {
			throw new UnsupportedOperationException("Auto-generated method stub");
		}

		public String getViewIdByConvention(String viewStateId) {
			return viewStateId;
		}

	}

	public static class TestConversionService implements ConversionService {

		public ConversionExecutor getConversionExecutor(Class sourceClass, Class targetClass)
				throws ConversionExecutionException {
			throw new UnsupportedOperationException("Auto-generated method stub");
		}

		public Class getClassByName(String name) throws ConversionExecutionException {
			throw new UnsupportedOperationException("Auto-generated method stub");
		}
	}
}