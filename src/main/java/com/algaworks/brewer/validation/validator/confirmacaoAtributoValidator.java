package com.algaworks.brewer.validation.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import com.algaworks.brewer.validation.AtributoConfirmacao;

public class confirmacaoAtributoValidator implements ConstraintValidator<AtributoConfirmacao, Object> {

	private String atributo;
	private String atributoConfirmacao;

	@Override
	public void initialize(AtributoConfirmacao constraintAnnotation) {
		this.atributo = constraintAnnotation.atributo();
		this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		boolean valido = false;
		try {
			Object valorAtributo = BeanUtils.getProperty(value, this.atributo);
			Object valorAtributoConfirmacao = BeanUtils.getProperty(value, this.atributoConfirmacao);

			valido = ambosSaoNull(valorAtributo, valorAtributoConfirmacao) || ambosSaoIguais(valorAtributo, valorAtributoConfirmacao);
		} catch (Exception e) {
			throw new RuntimeException("Erro recuperando valores dos atributos", e);
		}

		if (!valido) {
			context.disableDefaultConstraintViolation();
			String message = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(message);
			violationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
		}

		return valido;
	}

	private boolean ambosSaoNull(Object valorAtributo, Object valorAtributoConfirmacao) {
		return Objects.isNull(valorAtributo) && Objects.isNull(valorAtributoConfirmacao);
	}

	private boolean ambosSaoIguais(Object valorAtributo, Object valorAtributoConfirmacao) {
		return Objects.nonNull(valorAtributo) && valorAtributo.equals(valorAtributoConfirmacao);
	}

}
