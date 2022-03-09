package com.algaworks.brewer.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
			String valorAtributo = BeanUtils.getProperty(value, this.atributo);
			String valorAtributoConfirmacao = BeanUtils.getProperty(value, this.atributoConfirmacao);

			// TODO: Continuar implementação
			// valido = ambosSaoNull(valorAtributo, valorAtributoConfirmacao)
		} catch (Exception e) {
			throw new RuntimeException("Erro recuperando valores dos atributos", e);
		}

		return false;
	}

}
