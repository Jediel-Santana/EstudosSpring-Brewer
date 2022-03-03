package com.algaworks.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class OrderElementTagProcessor extends AbstractElementTagProcessor {

	private static final String NOME_TAG = "order";
	private static final int precedencia = 1000;

	public OrderElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, NOME_TAG, true, null, false, precedencia);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {
		IModelFactory modelFactory = context.getModelFactory();

		IAttribute pagina = tag.getAttribute("page");
		IAttribute propriedade = tag.getAttribute("field");
		IAttribute texto = tag.getAttribute("text");

		IModel model = modelFactory.createModel();

		model.add(modelFactory.createStandaloneElementTag("th:block", "th:replace",
				String.format("fragments/Ordenacao :: order (%s, %s, '%s')", pagina.getValue(), propriedade.getValue(), texto.getValue())));

		structureHandler.replaceWith(model, true);
	}

}
