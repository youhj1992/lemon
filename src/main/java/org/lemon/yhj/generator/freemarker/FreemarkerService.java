package org.lemon.yhj.generator.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 *  FreeMarker模板
 */
public class FreemarkerService {

	private String location;

	private String suffix;

	private Configuration cfg = new Configuration();
	
	private String encoding = "utf-8";
	
	public FreemarkerService(){
		cfg.setDefaultEncoding(encoding);
		cfg.setClassForTemplateLoading(FreemarkerService.class, "");
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setNumberFormat("#");
		this.location = "";
		this.suffix = "ftl";
	}
	
	public String merge(String template, Map<String, Object> model) {
		try {
			Template tpl = cfg.getTemplate(template+"."+suffix);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(bos);
			tpl.process(model, writer);
			writer.flush();
			writer.close();
			byte[] byteArray = bos.toByteArray();
			return new String(byteArray,encoding);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
	}
}
