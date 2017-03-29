package com.chenx.service.front.impl;

import com.chenx.model.Comment;
import com.chenx.model.dto.ComList;
import com.chenx.service.front.IComService;
import com.chenx.utils.Page;
import com.fjhb.commons.exception.BasicRuntimeException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@Service("comService")
public class ComServiceImpl implements IComService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    @Transactional
    public int insertCom(Comment comment) {
        return sqlSessionTemplate.insert("comment.insertCom", comment);
    }

    @Override
    public Page selectComs(int pageNo, int pageSize, String tourId) {
        Map paramMap = new HashMap<>();
        paramMap.put("tourId", tourId);
        paramMap.put("limit", pageSize);
        paramMap.put("offset", (--pageNo)*pageSize);
        List<ComList> firComList = sqlSessionTemplate.selectList("comment.selectFirstComs", paramMap);
        if (firComList.size() > 0){
            List<String> comIdList = firComList.stream().map(i -> i.getComId()).collect(Collectors.toList());
            List<ComList> secComList = sqlSessionTemplate.selectList("comment.selectSecComs", comIdList);
            for (ComList firCom: firComList){
                List<ComList> secList = new ArrayList<>();
                for (ComList secCom : secComList){
                    if (firCom.getComId().equals(secCom.getComParent())){
                        secList.add(secCom);
                    }
                }
                firCom.setCommentList(secList);
            }
        }
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        long totalSize = sqlSessionTemplate.selectOne("comment.getComTotal", tourId);
        page.setTotalSize(totalSize);
        page.setCurrentPageData(firComList);
        return page;
    }

    @Override
    @Transactional
    public int deleteCom(String comId, String userId) {
        Map paramMap = new HashMap<>();
        paramMap.put("comId", comId);
        paramMap.put("userId", userId);
        int delNum = sqlSessionTemplate.delete("comment.deleteCom", paramMap);
        if (delNum == 1){
            return sqlSessionTemplate.delete("comment.deleteChildCom", comId);
        }else {
            throw new BasicRuntimeException("删除失败");
        }
    }
}
