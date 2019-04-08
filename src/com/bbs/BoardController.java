package com.bbs;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.util.MyUtil;
import com.util.dao.CommonDAO;

@Controller("bbs.boardController")
public class BoardController {
	
	@Resource(name="dao")
	private CommonDAO dao;
	
	@Resource(name="myUtil")
	private MyUtil myUtil;
	
	@RequestMapping(value="/bbs/created.action",method={RequestMethod.POST,RequestMethod.GET})
	public String created(BoardCommand command,HttpServletRequest request) throws Exception {
		
		if(command==null||command.getMode()==null||command.getMode().equals("")){
			request.setAttribute("mode", "insert");
			return "board/created";
		}
		
		int boardNumMax = dao.getIntValue("bbs.boardNumMax");
		
		command.setBoardNum(boardNumMax+1);
		command.setIpAddr(request.getRemoteAddr());
		
		dao.insertData("bbs.insertData", command);

		
			
		return "redirect:/bbs/list.action";
	}
	
	@RequestMapping(value="/bbs/list.action",method={RequestMethod.POST,RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		
		String cp = request.getContextPath();
		
		int numPerPage = 5;
		int totalPage = 0;
		int totalDataCount = 0;
		
		String pageNum = request.getParameter("pageNum");
		
		//updateSubmit에서 넘어온 pageNum
		if(pageNum==null)
			pageNum=(String)session.getAttribute("pageNum");
		
		session.removeAttribute("pageNum");
		
		
		int currentPage = 1;
		if(pageNum!=null&&!pageNum.equals(""))
			currentPage = Integer.parseInt(pageNum);

		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue==null||searchValue.equals("")){
			searchKey="subject";
			searchValue="";
		}
		
		if(request.getMethod().equalsIgnoreCase("GET")){
			searchValue=URLDecoder.decode(searchValue,"UTF-8");
		}

		
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("searchKey",searchKey);
		hMap.put("searchValue",searchValue);			
		
		totalDataCount = dao.getIntValue("bbs.dataCount",hMap);
		
		
		if(totalDataCount!=0)
			totalPage = myUtil.getPageCount(numPerPage, totalDataCount);


		if(currentPage>totalPage)
			currentPage = totalPage;		
		
		
		int start = (currentPage-1)*numPerPage + 1;
		int end = currentPage*numPerPage;

		hMap.put("start", start);
		hMap.put("end", end);

		
		List<Object> lists = (List<Object>)dao.getListData("bbs.listData", hMap);		
		
		
		int listNum,n=0;

		ListIterator<Object> it = lists.listIterator();

		while(it.hasNext()){

			BoardCommand data = (BoardCommand)it.next();
			listNum = totalDataCount - (start+n-1);
			data.setListNum(listNum);
			n++;

		}		
		
		String param = "";
		String urlArticle = "";
		String urlList = "";

		//검색한것이 있다면
		if(!searchValue.equals("")){
			searchValue = URLEncoder.encode(searchValue, "UTF-8");
			param += "searchKey="+searchKey;
			param += "&searchValue="+searchValue;
		}

		urlList += cp +"/bbs/list.action";
		urlArticle += cp + "/bbs/article.action?pageNum="+currentPage;

		if(!param.equals("")){
			urlList += "?" + param;
			urlArticle += "&" + param;
		}
		
		request.setAttribute("lists", lists);
		request.setAttribute("totalDataCount", totalDataCount);
		request.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, urlList));
		request.setAttribute("urlArticle", urlArticle);
		request.setAttribute("pageNum",pageNum);
		request.setAttribute("totalPage", totalPage);
		
		return "board/list";
	}
	
	@RequestMapping(value="/bbs/article.action",method={RequestMethod.POST,RequestMethod.GET})
	public String article(HttpServletRequest request) throws Exception {
		
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String pageNum = request.getParameter("pageNum");
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue==null){
			searchKey="subject";
			searchValue="";
		}
		
		if(request.getMethod().equalsIgnoreCase("GET"))
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		
		dao.updatetData("bbs.updateHitCount", boardNum);
		
		BoardCommand dto = (BoardCommand)dao.getReadData("bbs.readData",boardNum);
		

		if(dto==null){
			return "redirect:/bbs/list.action";
		}


		int lineSu = dto.getContent().split("\n").length;
		dto.setContent(dto.getContent().replace("\n","<br/>"));
		
		
		Map<String, Object> hMap = new HashMap<String, Object>();

		
		hMap.put("searchKey",searchKey);
		hMap.put("searchValue",searchValue);
		hMap.put("boardNum", boardNum);

		
		BoardCommand preReadData = (BoardCommand)dao.getReadData("bbs.preReadData",hMap);

		int preBoardNum = 0;
		String preSubject ="";

		if(preReadData!=null){
			preBoardNum = preReadData.getBoardNum();
			preSubject = preReadData.getSubject();
		}

		BoardCommand nextReadData = (BoardCommand)dao.getReadData("bbs.nextReadData",hMap);

		int nextBoardNum = 0;
		String nextSubject ="";

		if(nextReadData!=null){
			nextBoardNum = nextReadData.getBoardNum();
			nextSubject = nextReadData.getSubject();

		}	
		
		String params = "pageNum="+pageNum;

		if(!searchValue.equals("")){
			params += "&searchKey=" + searchKey;
			params += "&searchValue=" + URLEncoder.encode(searchValue,"UTF-8");
		}

		request.setAttribute("dto", dto);
		request.setAttribute("preBoardNum", preBoardNum);
		request.setAttribute("preSubject", preSubject);
		request.setAttribute("nextBoardNum", nextBoardNum);
		request.setAttribute("nextSubject", nextSubject);
		request.setAttribute("params",params);
		request.setAttribute("lineSu",lineSu);
		request.setAttribute("pageNum",pageNum);

		return "board/article";
	}
	
	@RequestMapping(value="/bbs/deleted.action",method={RequestMethod.POST,RequestMethod.GET})
	public String deleted(HttpServletRequest request,HttpSession session) throws Exception {

		//HttpSession session = request.getSession();

		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String pageNum = request.getParameter("pageNum");
		
		dao.delteData("bbs.deleteData", boardNum);
		
		session.setAttribute("pageNum", pageNum);
		
		return "redirect:/bbs/list.action";
	}
	
	//창만 띄워주기(GET방식)
	@RequestMapping(value="/bbs/updated.action",method={RequestMethod.GET})
	public String updateForm(HttpServletRequest request) throws Exception {

		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String pageNum = request.getParameter("pageNum");
		
		BoardCommand dto = (BoardCommand)dao.getReadData("bbs.readData", boardNum);
		
		if(dto==null){
			return "redirect:/bbs/list.action?pageNum=" + pageNum;
		}
		
		request.setAttribute("mode", "update");
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("dto", dto);
		
		return "board/created";
	}
	
	//실제 수정해주기(POST방식)
	@RequestMapping(value="/bbs/updated.action",method={RequestMethod.POST})
	public String updateSubmit(HttpServletRequest request,BoardCommand command) throws Exception {

		HttpSession session = request.getSession();
		
		dao.updatetData("bbs.updateData", command);
		
		session.setAttribute("pageNum", command.getPageNum());
		
		return "redirect:/bbs/list.action";
		//세션으로 안 넘길 경우
		//return "redirect:/bbs/list.action?pageNum=" + pageNum;
	}
}
