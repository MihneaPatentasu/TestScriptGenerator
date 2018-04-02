package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class TestScriptGenerator {
	public static int generateTestScript(String fileNameWithPath, ArrayList<String> paths) {
		try (Writer writer= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileNameWithPath), "utf-8"))) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("import sys\n\n");
			buffer.append("listOfAllTests = []\n\n");
			for(String path : paths) {
				File file = new File(path);
				if(file.exists()) {
					buffer.append("#=======================================================\n");
					buffer.append("#                      "+getModuleNameOfPath(path)+"\n");
					buffer.append("#=======================================================\n");
					buffer.append("sys.path.append(r\""+path+"\")\n\n");
					
					ArrayList<String> importListBuffer=new ArrayList<>();
					ArrayList<String> testNameList=new ArrayList<>();
					populateImportAndTestBuffers(path, importListBuffer, testNameList);
					
					int i=1;
					for(String importLine : importListBuffer) {
						importLine=importLine.replace("\n", " # "+i+"\n");
						i++;
						buffer.append(importLine);
					}
					
					buffer.append("\nlistOfAllTests += [\n");
					
					i=1;
					for(String testName : testNameList) {
						testName=testName.concat(", # "+i+"\n");
						i++;
						buffer.append(testName);
					}
					
					buffer.append("                  ]\n\n");
				}
			}
			writer.write(buffer.toString());
			
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return -1;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		
		return 0;
	}
	
	private static void populateImportAndTestBuffers(String path, ArrayList<String> importListBuffer, ArrayList<String> testNameList) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	String fileName=listOfFiles[i].getName();
	    	if (listOfFiles[i].isFile() && fileName.endsWith(".py")) {
	    		String testName= getTestName(listOfFiles[i]);
			    if(testName != null) {
				    importListBuffer.add("from "+fileName.replace(".py", "")+" import "+testName+"\n");
				    testNameList.add(testName);
			    }
		    }
	    }
	}

	private static String getTestName(File file) {
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				line=line.trim();
				if(line.startsWith("def ") && line.endsWith("(testcase):")) {
					return line.replace("(testcase):","").replace("def ", "");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	private static String getModuleNameOfPath(String path) {
		return path.substring(path.lastIndexOf('\\')+1, path.length());
	}
}
