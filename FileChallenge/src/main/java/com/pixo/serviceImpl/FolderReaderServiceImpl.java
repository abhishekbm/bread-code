package com.pixo.serviceImpl;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixo.service.FolderReaderService;


@Service("folderReader")
public class FolderReaderServiceImpl implements FolderReaderService {
	
	
	@Autowired
	FileParserServiceImpl parserServiceImpl;
	
	@Autowired
	FIleListUtility log;
	
	public void  getDrive(String nameofDrive) {

		File directory = new File(nameofDrive);
		File[] files = directory.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return !file.isHidden();
			}
		});
		if (files != null) {
			List<File> files2 = Arrays.asList(files);

			for (int i = 0; i < files2.size(); i++) {
				File directory1 = (File) files2.get(i);
				if (directory1.isDirectory()) {
					addJsonData(directory1);
					parserServiceImpl.getDirectoryFiles(
							directory1.getAbsolutePath());
				}
				addJsonData(directory1);
			}
		}

	}
	
	
	private void addJsonData(File file){
		JSONObject json = new JSONObject();
		if(file!= null){
			json.put("Name", file.getName());
			json.put("size", file.length());
			json.put("Author", "Abhishek BM ");
			json.put("modified", file.lastModified());
			
			log.add(json);
		}
	}
}
