package org.lemon.yhj.generator.code;


import org.lemon.yhj.generator.bean.GeneratorConfig;
import org.lemon.yhj.generator.bean.TableEntity;
import org.lemon.yhj.generator.freemarker.FreemarkerService;

public class SQLXmlGenerator extends AbstractGenerator{

	public SQLXmlGenerator(FreemarkerService freemarkerService, TableEntity tableEntity, GeneratorConfig config) {
		super(freemarkerService, tableEntity, config);
	}

	@Override
	public String getFTL() {
		return "sql_xml";
	}

	@Override
	public String genFileName() {
		return tableEntity.getClassName() + "Mapper.xml";
	}

	@Override
	protected String genFilePath() {
		return config.getXmlLocation();
	}
}
