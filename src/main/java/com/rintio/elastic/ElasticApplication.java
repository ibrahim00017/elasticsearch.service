package com.rintio.elastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ElasticApplication {

	public static void main(String[] args) throws Exception{
//		System.out.println(args[0]);
		SpringApplication.run(ElasticApplication.class, args);
//		csvToJson(args[0]);
	}
// 	 public static void csvToJson(String csvFile) throws Exception{
//		 File input = new File(csvFile);
//		 File output = new File("output.json");
//
//		 CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
//		 CsvMapper csvMapper = new CsvMapper();
//
//		 // Read data from CSV file
//		 List<Object> readAll = csvMapper.readerWithTypedSchemaFor(Map.class).with(csvSchema).readValues(input).readAll();
//
//		 ElasticClient elasticClient = new ElasticClient();
//		 int id = 0;
//		 for(Object obj:readAll){
//		 	//elasticClient.creerIndexObjectNative("agros","agro",obj,id++);
//		 	//System.out.println(obj.toString());
//		 }
//		 ObjectMapper mapper = new ObjectMapper();
//
//		 // Write JSON formated data to output.json file
//		 mapper.writerWithDefaultPrettyPrinter().writeValue(output, readAll);
//
//		//  Write JSON formated data to stdout
//		 System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll));
//	 }
}
