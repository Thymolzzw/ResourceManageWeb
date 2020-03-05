package com.example.demo.user.controller;


import com.example.demo.entity.*;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.plugin2.main.server.JVMHealthData;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class UserActionController {

    @Autowired(required = false)
    UserService userService;


    @RequestMapping("/uploadPaperResource")
    public String uploadPaperResource(HttpServletRequest request){
        List<ResourceTable> list1=userService.searchResourceByType(2);
        List<ResourceTable> list2=userService.searchResourceByType(3);
        list1.addAll(list2);
        sortResource(list1);
        request.setAttribute("resources",list1);
        System.out.println("list1:"+list1.toString());
        return "registeruser/uploadPaperResource";
    }

    @RequestMapping("/uploadCodeResource")
    public String uploadCodeResource(HttpServletRequest request){
        List<ResourceTable> list3=userService.searchResourceByType(1);
        sortResource(list3);
        request.setAttribute("resources",list3);
        System.out.println("list1:"+list3.toString());
        return "registeruser/uploadCodeResource";
    }

    @RequestMapping("/uploadDataSetResource")
    public String uploadDataSetResource(HttpServletRequest request){
        List<ResourceTable> list1=userService.searchResourceByType(1);
        sortResource(list1);
        request.setAttribute("resources",list1);
        System.out.println("list1:"+list1.toString());
        return "registeruser/uploadDataSetResource";
    }



    @RequestMapping(value = "/uploadPaper", method = RequestMethod.POST)
    public String testUploadFile(HttpServletRequest request, MultipartHttpServletRequest multiReq) {

        String introduction=request.getParameter("introduction");
        String[] territory=request.getParameterValues("territory");
        String[] relation=request.getParameterValues("relation");
        String uploadPath ="D:\\IDEA_workspace\\mydemo\\src\\main\\uploadresource\\";
        // 获取上传文件的路径
        String uploadFilePath = multiReq.getFile("myfile").getOriginalFilename();
        if(uploadFilePath.trim().length()==0){
            return "/common/nofileerror";
        }
        System.out.println("上传文件的路径:" + uploadFilePath);
        // 截取上传文件的文件名
        String uploadFileName = uploadFilePath.substring(
                uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));
        System.out.println("上传文件的文件名" + uploadFileName);
        if(userService.searchResourceByName(uploadFileName)!=null){
            return "/common/samefileerror";
        }

        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(
                uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
        System.out.println("uploadFileSuffix:" + uploadFileSuffix);
        OutputStream fos = null;
        InputStream fis = null;
        String resourcelocation=uploadPath+ uploadFileName+"."+uploadFileSuffix;
        System.out.println("服务器存放地址："+resourcelocation);
        try {
            fis = (InputStream) multiReq.getFile("myfile").getInputStream();
            fos = new FileOutputStream(new File( uploadPath+ uploadFileName
                    + ".")
                    + uploadFileSuffix);
            byte[] temp = new byte[1024];
            int i = fis.read(temp);
            while (i != -1){
                fos.write(temp,0,temp.length);
                fos.flush();
                i = fis.read(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        UserTable user= (UserTable) request.getSession().getAttribute("user");
        System.out.println("资源介绍："+introduction);
        ResourceTable rt=new ResourceTable();
        if(!introduction.equals("")){
            rt.setIntroduction(introduction);
        }
        rt.setUserID(user.getUserID());
        rt.setResourceLocation(resourcelocation);
        rt.setResourceType(1);
        Timestamp time=new Timestamp(System.currentTimeMillis());
        rt.setUploadTime(time);
        System.out.println("上传时间："+time.toString());
        rt.setResourceName(uploadFileName);
        System.out.println("文件名："+uploadFileName);

        int flag=userService.addResource(rt);
        System.out.println("resourceID:"+rt.getResourceID());


        if(territory!=null){
            for(String s:territory){
                System.out.println("territory[]："+s);
                Belong belong=new Belong();
                belong.setTerritoryID(Integer.parseInt(s));
                belong.setResourceID(rt.getResourceID());
                userService.addBelong(belong);
                System.out.println("插入Belong成功，belongID为："+belong.getBelongID());
            }
        }
        if(relation!=null){
            for (String r:relation){
                System.out.println("relation[]:"+r);
                Relation re=new Relation();
                re.setResourceIDOne(rt.getResourceID());
                re.setResourceIDTwo(Integer.parseInt(r));
                userService.addRelation(re);
                System.out.println("插入依赖资源成功！");
            }
        }

        if(flag!=0){
            return "/common/successupload";
        }else{
            return "/common/unsuccessupload";
        }


    }


    @RequestMapping(value = "/uploadCode", method = RequestMethod.POST)
    public String testUploadCode(HttpServletRequest request, MultipartHttpServletRequest multiReq) {

        String introduction=request.getParameter("introduction");
        String[] territory=request.getParameterValues("territory");
        String[] relation=request.getParameterValues("relation");
        String uploadPath ="D:\\IDEA_workspace\\mydemo\\src\\main\\uploadresource\\";
        // 获取上传文件的路径
        String uploadFilePath = multiReq.getFile("myfile").getOriginalFilename();
        if(uploadFilePath.trim().length()==0){
            return "/common/nofileerror";
        }
        System.out.println("上传文件的路径:" + uploadFilePath);
        // 截取上传文件的文件名
        String uploadFileName = uploadFilePath.substring(
                uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));
        System.out.println("上传文件的文件名" + uploadFileName);
        if(userService.searchResourceByName(uploadFileName)!=null){
            return "/common/samefileerror";
        }

        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(
                uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
        System.out.println("uploadFileSuffix:" + uploadFileSuffix);
        OutputStream fos = null;
        InputStream fis = null;
        String resourcelocation=uploadPath+ uploadFileName+"."+uploadFileSuffix;
        System.out.println("服务器存放地址："+resourcelocation);
        try {
            fis = (InputStream) multiReq.getFile("myfile").getInputStream();
            fos = new FileOutputStream(new File( uploadPath+ uploadFileName
                    + ".")
                    + uploadFileSuffix);
            byte[] temp = new byte[1024];
            int i = fis.read(temp);
            while (i != -1){
                fos.write(temp,0,temp.length);
                fos.flush();
                i = fis.read(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        UserTable user= (UserTable) request.getSession().getAttribute("user");
        System.out.println("资源介绍："+introduction);
        ResourceTable rt=new ResourceTable();
        if(!introduction.equals("")){
            rt.setIntroduction(introduction);
        }
        rt.setUserID(user.getUserID());
        rt.setResourceLocation(resourcelocation);
        rt.setResourceType(2);
        Timestamp time=new Timestamp(System.currentTimeMillis());
        rt.setUploadTime(time);
        System.out.println("上传时间："+time.toString());
        rt.setResourceName(uploadFileName);
        System.out.println("文件名："+uploadFileName);

        int flag=userService.addResource(rt);
        System.out.println("resourceID:"+rt.getResourceID());


        if(territory!=null){
            for(String s:territory){
                System.out.println("territory[]："+s);
                Belong belong=new Belong();
                belong.setTerritoryID(Integer.parseInt(s));
                belong.setResourceID(rt.getResourceID());
                userService.addBelong(belong);
                System.out.println("插入Belong成功，belongID为："+belong.getBelongID());
            }
        }
        if(relation!=null){
            for (String r:relation){
                System.out.println("relation[]:"+r);
                Relation re=new Relation();
                re.setResourceIDTwo(rt.getResourceID());
                re.setResourceIDOne(Integer.parseInt(r));
                userService.addRelation(re);
                System.out.println("插入依赖资源成功！");
            }
        }

        if(flag!=0){
            return "/common/successupload";
        }else{
            return "/common/unsuccessupload";
        }

    }


    @RequestMapping(value = "/uploadDataSet", method = RequestMethod.POST)
    public String testUploadDataSet(HttpServletRequest request, MultipartHttpServletRequest multiReq) {

        String introduction=request.getParameter("introduction");
        String[] territory=request.getParameterValues("territory");
        String[] relation=request.getParameterValues("relation");
        String uploadPath ="D:\\IDEA_workspace\\mydemo\\src\\main\\uploadresource\\";
        // 获取上传文件的路径
        String uploadFilePath = multiReq.getFile("myfile").getOriginalFilename();
        if(uploadFilePath.trim().length()==0){
            return "/common/nofileerror";
        }
        System.out.println("上传文件的路径:" + uploadFilePath);
        // 截取上传文件的文件名
        String uploadFileName = uploadFilePath.substring(
                uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));
        System.out.println("上传文件的文件名" + uploadFileName);
        if(userService.searchResourceByName(uploadFileName)!=null){
            return "/common/samefileerror";
        }

        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(
                uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
        System.out.println("uploadFileSuffix:" + uploadFileSuffix);
        OutputStream fos = null;
        InputStream fis = null;
        String resourcelocation=uploadPath+ uploadFileName+"."+uploadFileSuffix;
        System.out.println("服务器存放地址："+resourcelocation);
        try {
            fis = (InputStream) multiReq.getFile("myfile").getInputStream();
            fos = new FileOutputStream(new File( uploadPath+ uploadFileName
                    + ".")
                    + uploadFileSuffix);
            byte[] temp = new byte[1024];
            int i = fis.read(temp);
            while (i != -1){
                fos.write(temp,0,temp.length);
                fos.flush();
                i = fis.read(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        UserTable user= (UserTable) request.getSession().getAttribute("user");
        System.out.println("资源介绍："+introduction);
        ResourceTable rt=new ResourceTable();
        if(!introduction.equals("")){
            rt.setIntroduction(introduction);
        }
        rt.setUserID(user.getUserID());
        rt.setResourceLocation(resourcelocation);
        rt.setResourceType(3);
        Timestamp time=new Timestamp(System.currentTimeMillis());
        rt.setUploadTime(time);
        System.out.println("上传时间："+time.toString());
        rt.setResourceName(uploadFileName);
        System.out.println("文件名："+uploadFileName);

        int flag=userService.addResource(rt);
        System.out.println("resourceID:"+rt.getResourceID());


        if(territory!=null){
            for(String s:territory){
                System.out.println("territory[]："+s);
                Belong belong=new Belong();
                belong.setTerritoryID(Integer.parseInt(s));
                belong.setResourceID(rt.getResourceID());
                userService.addBelong(belong);
                System.out.println("插入Belong成功，belongID为："+belong.getBelongID());
            }
        }
        if(relation!=null){
            for (String r:relation){
                System.out.println("relation[]:"+r);
                Relation re=new Relation();
                re.setResourceIDTwo(rt.getResourceID());
                re.setResourceIDOne(Integer.parseInt(r));
                userService.addRelation(re);
                System.out.println("插入依赖资源成功！");
            }
        }

        if(flag!=0){
            return "/common/successupload";
        }else{
            return "/common/unsuccessupload";
        }


    }

    public void sortResource(List<ResourceTable> resourceList){

        Collections.sort(resourceList);
    }


    @RequestMapping(value = "/findResource")
    public String finResource(HttpServletRequest request){
        List<ResourceTable> list1=userService.searchResourceByType(2);
        List<ResourceTable> list2=userService.searchResourceByType(3);
        List<ResourceTable> list3=userService.searchResourceByType(1);
        list1.addAll(list2);
        list1.addAll(list3);
        sortResource(list1);
        request.setAttribute("resources",list1);
        System.out.println("list1:"+list1.toString());

        return "/registeruser/findResource";
    }
    @RequestMapping(value = "/overloadResourceTable")
    public String overloadResourceTable(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "resourceName") String resourceName,
                                        @RequestParam(value = "resourceType") String resourceType){
        System.out.println("resourceName:"+resourceName);
        System.out.println("resourceType:"+resourceType);
        List<ResourceTable> list1=userService.searchResourceByType(2);
        List<ResourceTable> list2=userService.searchResourceByType(3);
        List<ResourceTable> list3=userService.searchResourceByType(1);
        list1.addAll(list2);
        list1.addAll(list3);
        sortResource(list1);

        List<ResourceTable> mylist=new ArrayList<ResourceTable>();
        for(int i=0;i<list1.size();i++){
            if(list1.get(i).getResourceName().contains(resourceName)){
                mylist.add(list1.get(i));
            }
        }
        List<ResourceTable> mylist1=new ArrayList<ResourceTable>();
        if(!resourceType.equals("")){
            int type=Integer.parseInt(resourceType);
            for(int i=0;i<mylist.size();i++){
                if(mylist.get(i).getResourceType()==type){
                    mylist1.add(mylist.get(i));
                }
            }
            request.setAttribute("resources",mylist1);
        }else{
            request.setAttribute("resources",mylist);
        }
        return "/registeruser/findResource";
    }

    @RequestMapping(value = "/overloadPrivateResourceTable")
    public String overloadPrivateResourceTable(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "resourceName") String resourceName,
                                        @RequestParam(value = "resourceType") String resourceType){
        System.out.println("resourceName:"+resourceName);
        System.out.println("resourceType:"+resourceType);
        UserTable userTable= (UserTable) request.getSession().getAttribute("user");
        List<ResourceTable> list1= userService.searchResourceByUserID(userTable.getUserID());

        List<ResourceTable> mylist=new ArrayList<ResourceTable>();
        for(int i=0;i<list1.size();i++){
            if(list1.get(i).getResourceName().contains(resourceName)){
                mylist.add(list1.get(i));
            }
        }
        List<ResourceTable> mylist1=new ArrayList<ResourceTable>();
        if(!resourceType.equals("")){
            int type=Integer.parseInt(resourceType);
            for(int i=0;i<mylist.size();i++){
                if(mylist.get(i).getResourceType()==type){
                    mylist1.add(mylist.get(i));
                }
            }
            request.setAttribute("uploadedResourceList",mylist1);
        }else{
            request.setAttribute("uploadedResourceList",mylist);
        }
        return "/registeruser/seePrivateUploaded";
    }

    @RequestMapping(value = "/resourcesee/{resourceID}")
    public String resourceSee(@PathVariable String resourceID,HttpServletRequest request){
        HttpSession session=request.getSession();
        int resource_id=Integer.parseInt(resourceID);
        System.out.println("查看的resourceID:"+resource_id);
        UserTable user= (UserTable) session.getAttribute("user");
        System.out.println("userID:"+user.getUserID());
        ResourceTable re=userService.searchResourceByID(resource_id);

        List<Belong> belongList=userService.searchBelongByResourceID(resource_id);
        List<Discuss> discussList=userService.searchDiscussByResourceID(resource_id);
        List<Relation> relationList=null;
        if(re.getResourceType()==1){
            relationList=userService.searchRelationByOne(resource_id);
        }else{
            relationList=userService.searchRelationByTwo(resource_id);
        }
        List<ResourceTable> resourceTableList=new ArrayList<ResourceTable>();
        for(int i=0;i<relationList.size();i++){
            if(re.getResourceType()==1){
                resourceTableList.add(userService.searchResourceByID(relationList.get(i).getResourceIDTwo()));
            }else{
                resourceTableList.add(userService.searchResourceByID(relationList.get(i).getResourceIDOne()));
            }
        }

        String territoryList="";
        for(int i=0;i<belongList.size();i++){
            System.out.println("belongList大小："+belongList.size());
            TerritoryTable te=userService.searchTerritoryByID(belongList.get(i).getTerritoryID());
            territoryList =territoryList+" "+te.getTerritoryName();
        }
        UserTable uploadUser=userService.searchUserByID(re.getUserID());
        List<MyDiscuss> myDiscussList=new ArrayList<MyDiscuss>();
        for(int i=0;i<discussList.size();i++){
            UserTable userTable=userService.searchUserByID(discussList.get(i).getUserID());
            MyDiscuss myDiscuss=new MyDiscuss();
            myDiscuss.setDiscussID(discussList.get(i).getDiscussID());
            myDiscuss.setDiscussContent(discussList.get(i).getDiscussContent());
            myDiscuss.setResourceID(discussList.get(i).getResourceID());
            myDiscuss.setUserName(userTable.getUserName());
            myDiscussList.add(myDiscuss);
            System.out.println("DiscussUserName:"+userTable.getUserName());
        }
        request.setAttribute("resource",re);
        request.setAttribute("territoryList",territoryList);
        System.out.println("territoryList:"+territoryList);
        request.setAttribute("discussList",myDiscussList);

        request.setAttribute("resourceTableList",resourceTableList);
        request.setAttribute("uploadUser",uploadUser);
        return "/registeruser/seeResource";
    }

    @RequestMapping(value = "/uploadDiscuss")
    public String uploadDiscuss(HttpServletRequest request,String resourceID,String mydiscuss){
        int uploadDiscussUserID=((UserTable)request.getSession().getAttribute("user")).getUserID();
        System.out.println("讨论资源对象ID："+resourceID);
        System.out.println("讨论内容："+mydiscuss);
        System.out.println("讨论发布者ID："+uploadDiscussUserID);
        Discuss discuss=new Discuss();
        discuss.setDiscussContent(mydiscuss);
        discuss.setResourceID(Integer.parseInt(resourceID));
        discuss.setUserID(uploadDiscussUserID);
        userService.addDiscuss(discuss);
        String url="redirect:/resourcesee/"+resourceID;
        return url;
    }
    @ResponseBody
    @RequestMapping(value = "/downloadResource/{resourceID}")
    public void downloadResource(@PathVariable String resourceID, HttpServletRequest request, HttpServletResponse response){



        System.out.println("resourceID:"+resourceID);
        ResourceTable resourceTable=userService.searchResourceByID(Integer.parseInt(resourceID));

        UserTable userTable= (UserTable) request.getSession().getAttribute("user");
        DownLoad downLoad=new DownLoad();
        downLoad.setUserID(userTable.getUserID());
        downLoad.setResourceID(resourceTable.getResourceID());
        Timestamp time=new Timestamp(System.currentTimeMillis());
        downLoad.setDownLoadTime(time);
        userService.addDownLoad(downLoad);

        System.out.println("web_location:"+resourceTable.getResourceLocation());
        String location=resourceTable.getResourceLocation();
        String[] strs=location.split("\\\\");
        String fileName = strs[strs.length-1];
        System.out.println("fileName:"+fileName);

        String downloadFilePath ="D:\\IDEA_workspace\\mydemo\\src\\main\\uploadresource\\";

        File file = new File(downloadFilePath+fileName);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开            
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {

            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    @RequestMapping(value ="/seeUploaded")
    public String seeUserUploaded(HttpServletRequest request){
        UserTable userTable= (UserTable) request.getSession().getAttribute("user");
        System.out.println("userID:"+userTable.getUserID());
        List<ResourceTable> resourceTableList= userService.searchResourceByUserID(userTable.getUserID());
        request.setAttribute("uploadedResourceList",resourceTableList);

        return "/registeruser/seePrivateUploaded";
    }

    @RequestMapping(value = "/modifyResource/{resourceID}")
    public String modifyResource(@PathVariable String resourceID,HttpServletRequest request){
        System.out.println("待修改的ResourceID:"+resourceID);
        int resourceid=Integer.parseInt(resourceID);
        ResourceTable resourceTable=userService.searchResourceByID(resourceid);
        List<ResourceTable> list1=userService.searchResourceByType(2); //代码资源
        System.out.println("代码资源已经加载！");
        List<ResourceTable> list2=userService.searchResourceByType(3); //数据集资源
        System.out.println("数据集资源已经加载！");
        List<ResourceTable> list3=userService.searchResourceByType(1); //论文资源
        System.out.println("论文资源已经加载！");

        request.setAttribute("thisResource",resourceTable);
        TerritoryFlag territoryFlag=new TerritoryFlag();

        List<Belong> belongList=userService.searchBelongByResourceID(resourceid);
        territoryFlag.setOneFlag("false");
        territoryFlag.setTwoFlag("false");
        territoryFlag.setThreeFlag("false");
        territoryFlag.setFourFlag("false");
        territoryFlag.setFiveFlag("false");
        territoryFlag.setSixFlag("false");
        territoryFlag.setSevenFlag("false");
        territoryFlag.setEightFlag("false");
        for(Belong be:belongList){
            if(be.getTerritoryID()==1){
                territoryFlag.setOneFlag("true");
            }else if(be.getTerritoryID()==2){
                territoryFlag.setTwoFlag("true");
            }else if(be.getTerritoryID()==3){
                territoryFlag.setThreeFlag("true");
            }else if(be.getTerritoryID()==4){
                territoryFlag.setFourFlag("true");
            }else if(be.getTerritoryID()==5){
                territoryFlag.setFiveFlag("true");
            }else if(be.getTerritoryID()==6){
                territoryFlag.setSixFlag("true");
            }else if(be.getTerritoryID()==7){
                territoryFlag.setSevenFlag("true");
            }else if(be.getTerritoryID()==8){
                territoryFlag.setEightFlag("true");
            }
        }
        request.setAttribute("territoryFlag",territoryFlag);
        List<ResourceTable> resources=null;
        if(resourceTable.getResourceType()==1){
            System.out.println("待修改文件类型：论文");
            list1.addAll(list2);
            sortResource(list1);
            resources=list1;
          //  request.setAttribute("resources",resources);

        }else if(resourceTable.getResourceType()==2){
            System.out.println("文件类型：代码");
            sortResource(list3);
            resources=list3;
        //    request.setAttribute("resources",resources);

        }else if(resourceTable.getResourceType()==3){
            System.out.println("文件类型：数据集");
            sortResource(list3);
            resources=list3;
        //    request.setAttribute("resources",resources);
        }
        List<ResourceFull> resourceFullList=new ArrayList<ResourceFull>();
        assert resources != null;
        for(ResourceTable re:resources){
            Relation relation=new Relation();
            if(resourceTable.getResourceType()==1){
                relation.setResourceIDOne(resourceTable.getResourceID());
                relation.setResourceIDTwo(re.getResourceID());
            }else{
                relation.setResourceIDTwo(resourceTable.getResourceID());
                relation.setResourceIDOne(re.getResourceID());
            }
            ResourceFull resourceFull=new ResourceFull();
            resourceFull.setResourceID(re.getResourceID());
            resourceFull.setIntroduction(re.getIntroduction());
            resourceFull.setResourceLocation(re.getResourceLocation());
            resourceFull.setResourceName(re.getResourceName());
            resourceFull.setResourceType(re.getResourceType());
            resourceFull.setUploadTime(re.getUploadTime());
            resourceFull.setVisitVolume(re.getVisitVolume());
            resourceFull.setUserID(re.getUserID());
            if(userService.searchRelationByOneAndTwo(relation)==null){
                resourceFull.setRelationFlag("false");
            }else{
                resourceFull.setRelationFlag("true");
            }
            resourceFullList.add(resourceFull);
        }
        request.setAttribute("resources",resourceFullList);

        return "/registeruser/modifyPrivateResourceInfo";
    }

    @RequestMapping(value = "/doResourceInfoModify")
    public String doResourceInfoModify(HttpServletRequest request){
        String resourceName=request.getParameter("resourceName");
        String[] territory=request.getParameterValues("territory");
        String[] relation=request.getParameterValues("relation");
        String introduction=request.getParameter("introduction");
        System.out.println("resourceName:"+resourceName);
        ResourceTable resourceTable= userService.searchResourceByName(resourceName);
        if(territory==null){
            System.out.println("territory为空");
        }else{
            for(String s:territory){
                System.out.println("territory:"+s);
            }
        }
        if(relation==null){
            System.out.println("relation为空");
        }else{
            for(String s:relation){
                System.out.println("relation:"+s);
            }
        }

        System.out.println("introduction:"+introduction);


        UserTable user= (UserTable) request.getSession().getAttribute("user");
        resourceTable.setIntroduction(introduction);
        int flag=userService.updateResource(resourceTable);

        //删除数据库中存在的belong
        userService.deleteBelongByResourceID(resourceTable.getResourceID());
     //   System.out.println("territory："+territory);
 //       String[] str=territory.split(",");
        if(territory!=null){
            for(String s:territory){
                Belong belong=new Belong();
                belong.setTerritoryID(Integer.parseInt(s));
                belong.setResourceID(resourceTable.getResourceID());
                userService.addBelong(belong);
                System.out.println("修改Belong成功，belongID为："+belong.getBelongID());
            }
        }

        //删除数据库中存在的relation
        userService.deleteRelationByResourceID(resourceTable.getResourceID());
   //     System.out.println("relation:"+relation);
   //     String[] relations=relation.split(",");
        if(relation!=null){
            for (String r:relation){
                Relation re=new Relation();
                if(resourceTable.getResourceType()==1){
                    re.setResourceIDOne(resourceTable.getResourceID());
                    re.setResourceIDTwo(Integer.parseInt(r));
                }else{
                    re.setResourceIDTwo(resourceTable.getResourceID());
                    re.setResourceIDOne(Integer.parseInt(r));
                }
                userService.addRelation(re);
                System.out.println("修改依赖资源成功！");
            }
        }



        String url="redirect:/resourcesee/"+resourceTable.getResourceID();
        return url;
    }


    @RequestMapping(value = "/deleteResource/{resourceID}")
    public String deleteResource(@PathVariable String resourceID,HttpServletRequest request){
        System.out.println("正在执行删除操作！");
        int resourceid=Integer.parseInt(resourceID);
        System.out.println("resourceID:"+resourceID);
        ResourceTable resourceTable=userService.searchResourceByID(resourceid);
        System.out.println("resourceLocation:"+resourceTable.getResourceLocation());
        userService.deleteRelationByResourceID(resourceid);
        userService.deleteBelongByResourceID(resourceid);
        userService.deleteDiscussByResourceID(resourceid);
        userService.deleteDownLoadByResourceID(resourceid);
        userService.deleteResourceByID(resourceid);
        //删除物理文件
        File file=new File(resourceTable.getResourceLocation());
        boolean flag=file.delete();
        if(flag){
            System.out.println("删除物理文件成功！");
        }else{
            System.out.println("删除物理文件失败！");
        }
        System.out.println("删除操作执行完毕！");
        return "redirect:/seeUploaded";
    }

    @RequestMapping(value = "/territoryResourceSearch")
    public String territoryResourceSearch(HttpServletRequest request){
        List<ResourceTable> resourceTableList1=userService.searchResourceByType(1);
        List<ResourceTable> resourceTableList2=userService.searchResourceByType(2);
        List<ResourceTable> resourceTableList3=userService.searchResourceByType(3);
        resourceTableList1.addAll(resourceTableList2);
        resourceTableList1.addAll(resourceTableList3);
        String territoryInfo="未选择领域，请选择领域！";
        request.setAttribute("territoryInfo",territoryInfo);
        request.setAttribute("resourceList",resourceTableList1);
        return "/registeruser/territoryResourceSearch";
    }
    @RequestMapping(value = "/overloadTerritoryResource")
    public String overloadTerritoryResource(HttpServletRequest request,
                                            @RequestParam(value = "territoryID") String territoryID,
                                            @RequestParam(value = "resourceName") String resourceName,
                                            @RequestParam(value = "resourceType") String resourceType){

        List<ResourceTable> resourceTableList=null;
        String territoryInfo="未选择领域，请选择领域！";
        List<ResourceTable> resourceTableList1=userService.searchResourceByType(1);
        List<ResourceTable> resourceTableList2=userService.searchResourceByType(2);
        List<ResourceTable> resourceTableList3=userService.searchResourceByType(3);
        if(resourceType.equals("")){
            System.out.println("resourceType:#  #");
            resourceTableList1.addAll(resourceTableList2);
            resourceTableList1.addAll(resourceTableList3);
            resourceTableList=resourceTableList1;
        }else{
            System.out.println("resourceType不为空！");
            if(resourceType.equals("1")){
                resourceTableList=resourceTableList1;
            }else if(resourceType.equals("2")){
                resourceTableList=resourceTableList2;
            }else if(resourceType.equals("3")){
                resourceTableList=resourceTableList3;
            }
        }
        if(territoryID.equals("")){
            System.out.println("territoryID:#  #");

        }else{
            System.out.println("territoryID不为空！");
            int territoryid=Integer.parseInt(territoryID);
            System.out.println("territoryID:"+territoryid);
            TerritoryTable territoryTable=userService.searchTerritoryByID(territoryid);
            territoryInfo=territoryTable.getTerritoryIntroduction();
            assert resourceTableList != null;
            for(int i = 0; i<resourceTableList.size(); i++){
                if(userService.searchBelongByResourceIDAndTerritoryID(resourceTableList.get(i).getResourceID(),territoryid)==null){
                    resourceTableList.remove(i);
                    i--;
                }else{
                    System.out.println("存在belong: "+resourceTableList.get(i).getResourceID()+" <-> "+territoryid);
                }
            }
        }
        if(resourceName.equals("")){
            System.out.println("resourceName:#  #");
        }else{
            System.out.println("resourceName不为空！");
            System.out.println("resourceName:"+resourceName);
            assert resourceTableList != null;
            for(int i = 0; i<resourceTableList.size(); i++){
                if(!resourceTableList.get(i).getResourceName().contains(resourceName)){
                    resourceTableList.remove(i);
                    i--;
                }else{
                    System.out.println("存在resourceName的内容: "+resourceTableList.get(i).getResourceID()+" <-> "+resourceName);
                }
            }
        }
        request.setAttribute("territoryInfo",territoryInfo);
        request.setAttribute("resourceList",resourceTableList);

        return "/registeruser/territoryResourceSearch";
    }

    @RequestMapping(value = "/deleteDiscuss/{discussID}/{resourceID}")
    public String deleteDiscuss(HttpServletRequest request,@PathVariable String discussID,@PathVariable String resourceID){
        int discussid=Integer.parseInt(discussID);
        System.out.println("discussID:"+discussID);
        userService.deleteDiscussByID(discussid);
        return "redirect:/resourcesee/"+resourceID;
    }
}
