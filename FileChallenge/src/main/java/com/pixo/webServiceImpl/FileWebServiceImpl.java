package com.pixo.webServiceImpl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pixo.serviceImpl.FolderReaderServiceImpl;
import com.pixo.serviceImpl.FIleListUtility;
import com.pixo.webService.FilesWebService;


@Component("FileWebService")
public class FileWebServiceImpl implements FilesWebService{

	
	@Autowired
	FolderReaderServiceImpl readerServiceImpl;
	
	@Autowired
	FIleListUtility log;
	
	public Response  getFileList(String nameofDrive) throws InterruptedException {

		readerServiceImpl.getDrive(nameofDrive);
		Thread.sleep(5000);
		return Response.ok(log.getLog()).build();
	}

	

}

/*
 * IOFileFilter fileFilter = new IOFileFilter() { public boolean accept(File
 * file,String s) { return file.isDirectory(); }
 * 
 * public boolean accept(File file) { // TODO Auto-generated method stub return
 * file.isDirectory(); } }; IOFileFilter dirFilter= new IOFileFilter() { public
 * boolean accept(File file,String s) { return file.isFile(); }
 * 
 * public boolean accept(File file) { // TODO Auto-generated method stub return
 * file.isFile(); } } ; Iterator fileIterator =FileUtils.iterateFiles(directory,
 * fileFilter, TrueFileFilter.INSTANCE );
 */

