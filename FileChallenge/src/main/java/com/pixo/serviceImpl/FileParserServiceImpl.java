package com.pixo.serviceImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixo.service.FileParserService;


@Service("folderParser")
public class FileParserServiceImpl implements FileParserService {

	@Autowired
	FolderReaderServiceImpl folderReaderServiceImpl;
	
	public void getDirectoryFiles(final String absolutePath) {
		
		ExecutorService threadPool = Executors.newFixedThreadPool(20);
		threadPool.submit(new Thread(new Runnable() {

			public void run() {
				folderReaderServiceImpl.getDrive(absolutePath);
			}
		}));
	
	}

}
