package com.sfile;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.util.FileManager;
import com.util.MyUtil;
import com.util.dao.CommonDAO;

@Controller("fileController")
public class FileController {
	
	@Resource(name="dao")
	private CommonDAO dao;
	
	@Resource(name="myUtil")
	private MyUtil myUtil;
	

	@RequestMapping(value="/file/write.action",method={RequestMethod.POST,RequestMethod.GET})
	public String write(HttpServletRequest request) throws Exception {
		
			return "sfile/write";
		
	}
	
	@RequestMapping(value="/file/write_ok.action",method={RequestMethod.POST,RequestMethod.GET})
	public String write_ok(FileCommand command,MultipartHttpServletRequest request,HttpSession session) throws Exception {
		

		MultipartFile file = request.getFile("upload");
		InputStream is = file.getInputStream();
		
		String root = session.getServletContext().getRealPath("/");
		String savepath = root + File.separator + "pds" + File.separator + "saveFile";

		String saveFileName = FileManager.doFileUpload(is, file.getOriginalFilename(), savepath);
		
		if(saveFileName!=null){

			int maxNum = dao.getIntValue("file.maxNum");

			command.setNum(maxNum+1);
			command.setSaveFileName(saveFileName);
			command.setOriginalFileName(file.getOriginalFilename());

			dao.insertData("file.insertData", command);
		}
		
			
		return "redirect:/file/list.action";
	}
	
	@RequestMapping(value="/file/list.action",method={RequestMethod.POST,RequestMethod.GET})
	public String list(FileCommand command,HttpServletRequest request,HttpSession session) throws Exception {
		
		String cp = request.getContextPath();

		int numPerPage = 5;
		int totalPage = 0;
		int totalDataCount =0;

		String pageNum = request.getParameter("pageNum");

		int currentPage = 1;

		if(pageNum!=null&&!pageNum.equals(""))
			currentPage = Integer.parseInt(pageNum);

		totalDataCount = dao.getIntValue("file.dataCount");

		if(totalDataCount!=0)
			totalPage = myUtil.getPageCount(numPerPage, totalDataCount);

		if(currentPage>totalPage)
			currentPage = totalPage;

		Map<String, Object> hMap = new HashMap<String, Object>();

		int start = (currentPage-1)*numPerPage+1;
		int end = currentPage*numPerPage;

		hMap.put("start", start);
		hMap.put("end", end);

		List<Object> lists = (List<Object>)dao.getListData("file.listData",hMap);
		
		Iterator<Object> it = lists.iterator();
		int listNum,n = 0;
		String str;
		
		while(it.hasNext()){
			FileCommand dto = (FileCommand)it.next();
			listNum = totalDataCount - (start+n-1);
			dto.setListNum(listNum);
			n++;
			str = cp + "/file/download.action?num="+dto.getNum();
			dto.setUrlFile(str);
		}
		
		String urlList = cp + "/file/list.action";

		request.setAttribute("pageNum", pageNum);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("totalDataCount", totalDataCount);
		request.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, urlList));
		request.setAttribute("lists", lists);

		
		
		return "sfile/list";
		
	}

	@RequestMapping(value="/file/download.action",method={RequestMethod.POST,RequestMethod.GET})
	public String download(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		
		String root = session.getServletContext().getRealPath("/");
		String savepath = root + File.separator + "pds" + File.separator + "saveFile";
		int num = Integer.parseInt(request.getParameter("num"));

		//saveFile을 보내서 삭제해줘야 되니까 일단 num을 가지고 해당 파일의 정보를 모두 읽어온다
		FileCommand dto = (FileCommand)dao.getReadData("file.readData",num);
		
		if(dto==null)
			return "redirect:/file/list.action";
		
		boolean flag = FileManager.doFileDownload(dto.getSaveFileName(), dto.getOriginalFileName(), savepath, response);
		
		if(!flag){
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();

			out.print("<script type='text/javascript'>");
			out.print("alert('다운로드에러!!');");
			out.print("history.back()");
			out.print("</script>");
		}
		
		return null;
	}
	
	@RequestMapping(value="/file/deleted.action",method={RequestMethod.POST,RequestMethod.GET})
	public String delet(HttpServletRequest request,HttpSession session,HttpServletResponse response,int num) throws Exception {
		
		
		String root = session.getServletContext().getRealPath("/");
		String savepath = root + File.separator + "pds" + File.separator + "saveFile";
		
		//saveFile을 보내서 삭제해줘야 되니까 일단 num을 가지고 해당 파일의 정보를 모두 읽어온다
		FileCommand dto = (FileCommand)dao.getReadData("file.readData",num);
		
		String saveFileName = dto.getSaveFileName();
		FileManager.doFileDelete(saveFileName, savepath);
		
		dao.delteData("file.deleteData",num);

		return "redirect:/file/list.action";
	}

}
