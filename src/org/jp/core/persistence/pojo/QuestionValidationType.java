package org.jp.core.persistence.pojo;

// Generated 27-abr-2009 18:04:46 by Hibernate Tools 3.2.2.GA

import java.util.HashSet;
import java.util.Set;

/**
 * QuestionValidationType generated by hbm2java
 */
public class QuestionValidationType implements java.io.Serializable {

	private int idTypeVal;
	private String desVal;
	private String param;
	private String help;
	private byte[] rules;
	private Set<QuestionValidation> questionValidations = new HashSet<QuestionValidation>(
			0);

	public QuestionValidationType() {
	}

	public QuestionValidationType(int idTypeVal) {
		this.idTypeVal = idTypeVal;
	}

	public QuestionValidationType(int idTypeVal, String desVal, String param,
			String help, byte[] rules,
			Set<QuestionValidation> questionValidations) {
		this.idTypeVal = idTypeVal;
		this.desVal = desVal;
		this.param = param;
		this.help = help;
		this.rules = rules;
		this.questionValidations = questionValidations;
	}

	public int getIdTypeVal() {
		return this.idTypeVal;
	}

	public void setIdTypeVal(int idTypeVal) {
		this.idTypeVal = idTypeVal;
	}

	public String getDesVal() {
		return this.desVal;
	}

	public void setDesVal(String desVal) {
		this.desVal = desVal;
	}

	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public byte[] getRules() {
		return this.rules;
	}

	public void setRules(byte[] rules) {
		this.rules = rules;
	}

	public Set<QuestionValidation> getQuestionValidations() {
		return this.questionValidations;
	}

	public void setQuestionValidations(
			Set<QuestionValidation> questionValidations) {
		this.questionValidations = questionValidations;
	}

}
