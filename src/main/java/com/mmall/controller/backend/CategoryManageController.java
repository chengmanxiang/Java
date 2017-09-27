package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by johncheng on 2017/9/8.
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    /**
     *添加商品接口
     *session 用户状态  categoryName 商品名称  parentId 父级商品的id
     * */
    @RequestMapping("add_category.do")
    @ResponseBody //序列化json格式
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        //检验一下是不是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //是管理员 增加处理分类的逻辑
            return iCategoryService.addCategory(categoryName,parentId);
        }else{
            return ServerResponse.createByErrorMessage("无权操作，需要管理员权限");
        }

    }

    /**
     *更新商品接口
     *session 用户状态  categoryName 商品名称  categoryId 商品的id
     * */
    @RequestMapping("set_category_name.do")
    @ResponseBody //序列化json格式
    public ServerResponse setCategoryName(HttpSession session, String categoryName,Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        //检验一下是不是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //更新categoryName
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return ServerResponse.createByErrorMessage("无权操作，需要管理员权限");
        }

    }
    /**
     *获取平级商品接口
     *session 用户状态  categoryId 商品的id
     * */
    @RequestMapping("get_category.do")
    @ResponseBody //序列化json格式
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        //检验一下是不是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //查询子节点的category信息。并且不递归，保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("无权操作，需要管理员权限");
        }
    }
    /**
     *获取当前categroyId，并且递归查询它的子节点的categoryId
     *session 用户状态  categoryId 商品的id
     * */
    @RequestMapping("get_deep_category.do")
    @ResponseBody //序列化json格式
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        //检验一下是不是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //查询当前节点的id。并且递归子节点的id
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("无权操作，需要管理员权限");
        }
    }



}
