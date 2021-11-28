package com.bjpowernode.controller;

import com.bjpowernode.domain.Student;
import com.bjpowernode.exception.AgeException;
import com.bjpowernode.exception.MyUserException;
import com.bjpowernode.exception.NameException;
import com.bjpowernode.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService service; //疑问？用StudentServiceImpl会怎样

    //注册学生
    @RequestMapping("/addStudent.do")
    public ModelAndView addStudent(Student student) throws MyUserException {
        ModelAndView mv = new ModelAndView();


        //根据请求参数抛出异常
        if (!"zs".equals(student.getName())){
            throw new NameException("姓名不正确！！！");
        }
        if (student.getAge() == null || student.getAge()>80){
            throw new AgeException("年龄比较大！！！");
        }



        String tips = "注册失败";
        //调用service处理student
        int nums = service.addStudent(student);
        if (nums>0){
            //注册成功
            tips = "学生 【"+student.getName()+"】 注册成功";
        }
        //添加数据
        mv.addObject("tips",tips);
        //指定结果页面
        mv.setViewName("result");
        return mv;
    }

    //处理查询，响应ajax
    @RequestMapping("/queryStudent.do")
    @ResponseBody
    public List<Student> queryStudent(){
        //参数检查，简单的数据处理
        List<Student> students = service.findStudent();
        return students;
    }
}
