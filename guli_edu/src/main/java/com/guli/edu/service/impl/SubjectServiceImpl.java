package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.entity.Subject;
import com.guli.edu.entity.vo.SubjectNestedVo;
import com.guli.edu.entity.vo.SubjectVo;
import com.guli.edu.mapper.SubjectMapper;
import com.guli.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public List<String> importCourse(MultipartFile file) throws IOException {
        String parentId = null;
        InputStream inputStream = file.getInputStream();
        List<String> message = new ArrayList<>();
        //创建WorkBook
        Workbook workbook = new HSSFWorkbook(inputStream);
        //获取sheet
        Sheet sheet = workbook.getSheetAt(0);
        //获取一共多少行
        int lastRowNum = sheet.getLastRowNum();
        //如果总值小于1,则返回信息,没有数据,否则,循环遍历
        if(lastRowNum < 1){
            message.add("Excel中没有任何数据");
            return message;
        }
        for (int i = 1; i <=lastRowNum ; i++) {
            //获取行
            Row row = sheet.getRow(i);
            //判断行是否为空
            if(row == null){
                message.add("第" + (i + 1) + "行，" + "第1列的值为空");
            }
            if(row != null){
                //如果不为空,获取一级分类的列
                Cell cell = row.getCell(0);
                //判断列是否为空,如果为空,返回信息,否则,获取列中的值
                if(cell == null){
                    message.add("第" + (i + 1) + "行，" + "第1列的值为空");
                }else{
                    //获取列中的值
                    String stringCellValue = cell.getStringCellValue();
                    //判断值是否为空,如果为空,返回信息,否则,调用方法,在数据库中进行查询
                    if(stringCellValue == null){
                        message.add("第" + (i + 1) + "行，" + "第1列的值为空");
                    }else{
                        //如果数据库中没有此值,返回null,并获取ID，在二级分类时当作parentId
                        Subject subject = this.selectSubjectByTitleAndId(stringCellValue,"0");
                        if(subject == null){
                            subject = new Subject();
                            subject.setTitle(stringCellValue);
                            subject.setParentId("0");
                            this.baseMapper.insert(subject);
                            parentId = subject.getId();
                        }else{
                            //将已有的id作为二级分类的parentId
                           parentId = subject.getId();
                        }
                    }
                }
                //获取二级分类的列
                Cell cell1 = row.getCell(1);
                //判断列是否为空,如果为空,返回信息,否则,获取列中的值
                if(cell1 == null){
                    message.add("第" + (i + 1) + "行，" + "第2列的值为空");
                }else{
                    String stringCellValue = cell1.getStringCellValue();
                    //判断值是否为空,如果为空,返回信息,否则,调用方法,在数据库中进行查询
                    if(stringCellValue == null){
                        message.add("第" + (i + 1) + "行，" + "第2列的值为空");
                    }else{
                        Subject subject = this.selectSubjectByTitleAndId(stringCellValue,parentId);
                        if(subject == null){
                            subject = new Subject();
                            subject.setTitle(stringCellValue);
                            subject.setParentId(parentId);
                            this.baseMapper.insert(subject);
                        }
                    }
                }
            }
        }

        return message;
    }

    @Override
    public List<SubjectVo> getSubjectList() {
        //将查询完的结果保存在这个集合中
        List<SubjectVo> list = new ArrayList<>();
        //查询你条件
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        //先查询出父节点
        wrapper.eq("parent_id","0");
        List<Subject> subjects = this.baseMapper.selectList(wrapper);
        for (Subject subject : subjects) {
            SubjectVo subjectVo = new SubjectVo();
            //将父节点复制到vo中,用vo和前端交互
            BeanUtils.copyProperties(subject,subjectVo);
            //将父节点结果添加到集合中
            list.add(subjectVo);
            //建立查询子节点的条件
            QueryWrapper<Subject> wrapper1 = new QueryWrapper<>();
            String subId =subject.getId();
            //subId:父节点的id,相当于子节点的parentId
            wrapper1.eq("parent_id",subId);
            List<Subject> subjectsBySon = this.baseMapper.selectList(wrapper1);
            //循环后将结果拷贝至vo中,在将子节点添加到父节点的children属性中
            for (Subject subject1 : subjectsBySon) {
                SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
                BeanUtils.copyProperties(subject1,subjectNestedVo);
                subjectVo.getChildren().add(subjectNestedVo);
            }
        }
        return list;
    }

    /**
     * 删除节点
     * @param id
     * @return
     */
    @Override
    public Boolean deleteById(String id) {
        List<String> ids = new ArrayList<>();
        ids.add(id);
        this.getIds(ids,id);
        int i = this.baseMapper.deleteBatchIds(ids);
        return i!=0;
    }

    @Override
    public List<Subject> getSubjectByParentId(String parentId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",parentId);
        List<Subject> subjects = this.baseMapper.selectList(wrapper);
        return subjects;
    }

    //递归删除
    private void getIds(List<String> ids, String id) {
        //找出此节点的子节点
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",wrapper);
        List<Subject> subjects = this.baseMapper.selectList(wrapper);
        //进行递归
        for (Subject subject : subjects) {
            ids.add(subject.getId());
            this.getIds(ids,subject.getId());
        }
    }

    //查询数据库
    private Subject selectSubjectByTitleAndId(String stringCellValue, String parentId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",stringCellValue);
        wrapper.eq("parent_id",parentId);
        Subject subject = this.baseMapper.selectOne(wrapper);
        return subject;
    }
}
