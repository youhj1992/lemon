package org.lemon.yhj.generator.code;


import org.lemon.yhj.generator.bean.GeneratorConfig;
import org.lemon.yhj.generator.bean.TableEntity;
import org.lemon.yhj.generator.freemarker.FreemarkerService;
import org.lemon.yhj.generator.utils.PathUtils;

public class JavaDaoGenerator extends AbstractGenerator{

	public JavaDaoGenerator(FreemarkerService freemarkerService, TableEntity tableEntity, GeneratorConfig config) {
		super(freemarkerService, tableEntity, config);
	}

	@Override
	public String getFTL() {
		return "java_dao";
	}

	@Override
	public String genFileName() {
		return tableEntity.getClassName() + "Mapper.java";
	}

	@Override
	protected String genFilePath() {
		return PathUtils.getPathByPackage(config.getDaoPackage(), config);
	}
}
